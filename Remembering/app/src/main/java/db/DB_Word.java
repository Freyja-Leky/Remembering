package db;

import android.util.Log;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Word extends Thread {

    private String sql_word_create;
    private String sql_word_change;
    private String sql_word_record;
    private String emailID;
    private String RecordTitle;
    private boolean type;
    private Word word;

    //true for create;false for change
    public DB_Word(boolean type, Word w, VocabularyBook vocabularyBook,String email)
    {
        emailID="";
        for (int i=0;i<email.length();i++)
        {
            if (email.charAt(i)!='@'&&email.charAt(i)!='.')
                emailID+=email.charAt(i);
        }

        RecordTitle=vocabularyBook.getName()+"_"+emailID;
        this.type=type;
        this.word=w;

        sql_word_create="INSERT INTO "+vocabularyBook.getName()+" VALUES(?, ?, ?)";
        sql_word_record="INSERT INTO "+RecordTitle+" VALUES(?, ?, ?)";
    }

    public DB_Word(boolean type, Word w, VocabularyBook vocabularyBook)
    {
        this.type=type;
        this.word=w;
        sql_word_change="UPDATE "+vocabularyBook.getName()+" SET Chinese = ? , Hint = ? WHERE English = ?";
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            if (type)
            {
                PreparedStatement ps_word_create=cn.prepareStatement(sql_word_create);
                ps_word_create.setString(1,word.getEnglish());
                ps_word_create.setString(2,word.getChinese());
                ps_word_create.setString(3,word.getHint());
                ps_word_create.execute();
                ps_word_create.close();

                PreparedStatement ps_word_record=cn.prepareStatement(sql_word_record);
                ps_word_record.setString(1,word.getEnglish());
                ps_word_record.setBoolean(2,false);
                ps_word_record.setInt(3,0);
                ps_word_record.execute();
                ps_word_record.close();
            }
            else
            {
                PreparedStatement preparedStatement=cn.prepareStatement(sql_word_change);
                preparedStatement.setString(1,word.getChinese());
                preparedStatement.setString(2,word.getHint());
                preparedStatement.setString(3,word.getEnglish());
                preparedStatement.execute();
                preparedStatement.close();
            }
            cn.close();;
            Log.e("DB_Word","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Word","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Word","Fail for sql");
        }
    }

}

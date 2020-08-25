package db;

import android.util.Log;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Record extends Thread {

    private String sql;
    private String RecordTitle;
    private Word word;

    public DB_Record(Word word, VocabularyBook vocabularyBook,String email)
    {
        this.word=word;
        String emailID="";
        for (int i=0;i<email.length();i++)
        {
            if (email.charAt(i)!='@'&&email.charAt(i)!='.')
                emailID+=email.charAt(i);
        }
        if (vocabularyBook.getVersion()==0)
            RecordTitle=vocabularyBook.getName()+"_"+emailID;
        else
            RecordTitle=vocabularyBook.getName()+"_v"+vocabularyBook.getVersion()+"_"+emailID;
        sql="UPDATE "+RecordTitle+" SET ReviewTime = ? , Easy = ? WHERE English = ?";
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setInt(1,word.getReviewTime());
            preparedStatement.setBoolean(2,word.isEasy());
            preparedStatement.setString(3,word.getEnglish());
            Log.e("DB_Record",preparedStatement.toString());
            preparedStatement.execute();
            preparedStatement.close();
            cn.close();;
            Log.e("DB_Record","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Record","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Record","Fail for sql");
        }
    }

}

package db;

import android.util.Log;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_AddBook extends Thread {

    private String sql_book;
//    private String sql_record;
    private Word word;

    public DB_AddBook(Word word, VocabularyBook vocabularyBook,String email)
    {
        this.word=word;
        String emailID="";
        for (int i=0;i<email.length();i++)
        {
            if (email.charAt(i)!='@'&&email.charAt(i)!='.')
                emailID+=email.charAt(i);
        }
//        RecordTitle=vocabularyBook.getName()+"_"+emailID;
        sql_book = "INSERT INTO "+vocabularyBook.getName()+"  VALUES(?, ?, ?)";
//        sql_record = "INSERT INTO "+RecordTitle+" VALUES(?, ?, ?)";
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement ps_book=cn.prepareStatement(sql_book);
            ps_book.setString(1,word.getEnglish());
            ps_book.setString(2,word.getChinese());
            ps_book.setString(3,word.getHint());
            Log.e("DB_AddBook",ps_book.toString());
            ps_book.execute();
            ps_book.close();

//            PreparedStatement ps_record = cn.prepareStatement(sql_record);
//            ps_record.setString(1,word.getEnglish());
//            ps_record.setInt(2,0);
//            ps_record.setInt(3,0);
//            ps_record.execute();
//            ps_record.close();
            cn.close();;
            Log.e("DB_AddBook","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_AddBook","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_AddBook","Fail for sql");
        }
    }
}

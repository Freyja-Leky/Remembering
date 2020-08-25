package db;

import android.util.Log;
import logic_class.VocabularyBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Book extends Thread {

    private String sql_vocabulary_book;
    private String sql_create_book;
    private String sql_create_record;
    private String emailID;
    private String RecordTitle;
    private String intro;
    private String bookname;
    private String email;

    private boolean type;

    //true for modify;false for new
    public DB_Book(VocabularyBook book,boolean type,String email)
    {
        emailID="";
        for (int i=0;i<email.length();i++)
        {
            if (email.charAt(i)!='@'&&email.charAt(i)!='.')
                emailID+=email.charAt(i);
        }
        this.type=type;
        RecordTitle=book.getName()+"_"+emailID;
        intro=book.getIntroduction();
        bookname=book.getName();
        if (type)
        {
            sql_vocabulary_book="UPDATE vocabulary_book SET introducation = ? WHERE book_name = ?";
        }

        else
        {
            this.email=email;
            sql_vocabulary_book="INSERT INTO vocabulary_book  VALUES(?, ?, ?, ?, ?)";
            sql_create_book="Create TABLE IF NOT EXISTS "+book.getName()+" (English VARCHAR(255) PRIMARY KEY, Chinese VARCHAR(255) NOT NULL, Hint VARCHAR(255));";
            sql_create_record="Create TABLE IF NOT EXISTS "+RecordTitle+" (English VARCHAR(255) PRIMARY KEY, Easy BOOLEAN NOT NULL, ReviewTime INT NOT NULL)";
        }
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            if (type)
            {
                PreparedStatement preparedStatement=cn.prepareStatement(sql_vocabulary_book);
                preparedStatement.setString(1,intro);
                preparedStatement.setString(2,bookname);
                preparedStatement.execute();
                preparedStatement.close();
            }
            else
            {
                PreparedStatement ps_vocabulary_book=cn.prepareStatement(sql_vocabulary_book);
                ps_vocabulary_book.setString(1,bookname);
                ps_vocabulary_book.setString(2,intro);
                ps_vocabulary_book.setString(3,email);
                ps_vocabulary_book.setBoolean(4,false);
                ps_vocabulary_book.setInt(5,0);
                ps_vocabulary_book.execute();
                ps_vocabulary_book.close();

                PreparedStatement ps_create_book=cn.prepareStatement(sql_create_book);
                ps_create_book.execute();
                ps_create_book.close();

                PreparedStatement ps_create_record=cn.prepareStatement(sql_create_record);
                ps_create_record.execute();
                ps_create_record.close();
            }
            cn.close();
            Log.e("DB_Note","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Note","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Note","Fail for sql");
        }
    }
}

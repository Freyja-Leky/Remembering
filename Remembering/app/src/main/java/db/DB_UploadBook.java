package db;

import android.util.Log;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;

import java.sql.*;

public class DB_UploadBook extends Thread {

    private String sql_get_version;
    private String sql_upload_book;
    private String sql_insert_word;
    private VocabularyBook vocabularyBook;

    public DB_UploadBook(VocabularyBook vocabularyBook)
    {
        this.vocabularyBook=vocabularyBook;
        sql_get_version="SELECT visibility, version FROM vocabulary_book WHERE book_name = ?";
        sql_upload_book="Create TABLE IF NOT EXISTS "+vocabularyBook.getName()+"_v";
        sql_insert_word="INSERT INTO "+vocabularyBook.getName()+"_v";

    }

    public void run()
    {
        Connection cn=null;
        ResultSet rs=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk", "testuser", "44678ghost");

            PreparedStatement ps_get_version=cn.prepareStatement(sql_get_version);
            ps_get_version.setString(1,vocabularyBook.getName());
            rs=ps_get_version.executeQuery();
            if (rs.next())
            {
                Boolean visibility=rs.getBoolean(1);
                int version=rs.getInt(2);
                if (!visibility)
                {
                    String sql="UPDATE vocabulary_book SET visibility = ? , version = ? WHERE book_name = ?";
                    PreparedStatement ps_sql=cn.prepareStatement(sql);
                    ps_sql.setBoolean(1,true);
                    ps_sql.setInt(2,1);
                    ps_sql.setString(3,vocabularyBook.getName());
                    ps_sql.execute();
                    ps_sql.close();
                    version++;
                }
                else
                {

                    String sql_drop="DROP TABLE "+vocabularyBook.getName()+"_v"+version;
                    PreparedStatement ps_drop=cn.prepareStatement(sql_drop);
                    ps_drop.execute();
                    ps_drop.close();

                    String sql="UPDATE vocabulary_book SET version = ? WHERE book_name = ?";
                    version++;
                    PreparedStatement ps_sql=cn.prepareStatement(sql);
                    ps_sql.setInt(1,version);
                    ps_sql.setString(2,vocabularyBook.getName());
                    ps_sql.execute();
                    ps_sql.close();
                }
                sql_upload_book+=version;
                sql_upload_book+=" (English VARCHAR(255) PRIMARY KEY, Chinese VARCHAR(255) NOT NULL, Hint VARCHAR(255));";
                sql_insert_word+=version;
                sql_insert_word+="  VALUES(?, ?, ?)";
            }
            Log.e("DB_UploadBook",sql_upload_book);
            Log.e("DB_UploadBook",sql_insert_word);

            rs.close();
            ps_get_version.close();

            PreparedStatement ps_upload_book=cn.prepareStatement(sql_upload_book);
            ps_upload_book.execute();
            ps_upload_book.close();

            PreparedStatement ps_insert_word=cn.prepareStatement(sql_insert_word);
            for (Word word:vocabularyBook.getWordSet())
            {
                ps_insert_word.setString(1,word.getEnglish());
                ps_insert_word.setString(2,word.getChinese());
                ps_insert_word.setString(3,word.getHint());
                ps_insert_word.execute();
            }
            ps_insert_word.close();
            cn.close();
            Log.e("DB_UploadBook","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_UploadBook","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_UploadBook","Fail for sql");
        }
    }
}

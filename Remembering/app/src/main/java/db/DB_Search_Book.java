package db;

import android.util.Log;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;

import java.sql.*;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

public class DB_Search_Book extends Thread{

    private String sql;

    private String sql_Get_Version;
    private String sql_Create_Record;
    private String sql_Init_Record;
    private String sql_Read_Record;

    private String search_str;
    private String creator;
    private String RecordTitle;
    private String emailID;
    //0 for init;1 for one book;2 for search
    private int flag;

    private VocabularyBook vocabularyBook;
    private List<VocabularyBook> bookList;

    public DB_Search_Book(String email,Boolean tag)
    {
        this.creator=email;
        sql="SELECT book_name, introducation FROM vocabulary_book WHERE visibility = 1 AND creater <> ?";
        bookList=new ArrayList<>();
        this.flag=0;
    }

    public DB_Search_Book(VocabularyBook vocabularyBook,String email)
    {
        emailID="";
        for (int i=0;i<email.length();i++)
        {
            if (email.charAt(i)!='@'&&email.charAt(i)!='.')
                emailID+=email.charAt(i);
        }
        RecordTitle=vocabularyBook.getName()+"_v";
        sql_Get_Version="SELECT version FROM vocabulary_book WHERE book_name = ?";
        this.vocabularyBook=vocabularyBook;
        this.flag=1;
    }

    public DB_Search_Book(String str)
    {
        sql="SELECT book_name, introducation FROM vocabulary_book WHERE visibility = 1 AND book_name LIKE ?";
        search_str="%"+str+"%";
        bookList=new ArrayList<>();
        this.flag=2;
    }

    public void run()
    {
        Connection cn=null;
        ResultSet rs=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            if (flag==0)
            {
                PreparedStatement preparedStatement=cn.prepareStatement(sql);
                preparedStatement.setString(1,creator);
                rs=preparedStatement.executeQuery();
                while (rs.next())
                {
                    String bookname=rs.getString("book_name");
                    String introduction=rs.getString("introducation");
                    VocabularyBook vocabularyBook=new VocabularyBook(bookname,introduction);
                    bookList.add(vocabularyBook);
                }
                rs.close();
                preparedStatement.close();
            }
            else if (flag==1)
            {
                PreparedStatement ps_get_version=cn.prepareStatement(sql_Get_Version);
                ps_get_version.setString(1,vocabularyBook.getName());
                rs=ps_get_version.executeQuery();
                rs.next();
                int version=rs.getInt(1);
                vocabularyBook.setVersion(version);
                RecordTitle+=version;
                RecordTitle+="_";
                RecordTitle+=emailID;

                String bookName=vocabularyBook.getName()+"_v"+version;

                sql="SELECT * FROM "+bookName;
                sql_Create_Record="Create TABLE IF NOT EXISTS "+RecordTitle+" (English VARCHAR(255) PRIMARY KEY, Easy BOOLEAN NOT NULL, ReviewTime INT NOT NULL)";
                sql_Init_Record="INSERT INTO "+RecordTitle+" VALUES(?, ?, ?)";
                sql_Read_Record="SELECT "+bookName+".English, "+bookName+".Chinese, "+bookName+".Hint, "+RecordTitle+".Easy, "+RecordTitle+".ReviewTime" +
                        " FROM "+bookName+" INNER JOIN "+RecordTitle+" ON "+RecordTitle+".English = "+bookName+".English";

                Log.e("DB_Search_Book",sql_Create_Record);
                Log.e("DB_Search_Book",sql_Read_Record);

                DatabaseMetaData metaData=null;
                ResultSet rsTable=null;
                metaData=cn.getMetaData();
                rsTable=metaData.getTables(null,null,RecordTitle,null);
                if (rsTable.next())
                {
                    PreparedStatement ps_read=cn.prepareStatement(sql_Read_Record);
                    rs=ps_read.executeQuery();
                    while (rs.next())
                    {
                        String wordEnglish=rs.getString(1);
                        String wordChinese=rs.getString(2);
                        String wordhint=rs.getString(3);
                        boolean wordEasy=rs.getBoolean(4);
                        int wordReviewTime=rs.getInt(5);
                        Word word=new Word(wordEnglish,wordChinese,vocabularyBook.getName());
                        if (wordhint!=null)
                            word.setHint(wordhint);
                        if (wordEasy)
                            word.setEasy();
                        word.setReviewTime(wordReviewTime);
                        Log.e("DB_Search_Book",word.getEnglish()+""+String.valueOf(word.getReviewTime()));
                        vocabularyBook.add2Book(word);
                    }
                    Log.e("DB_Search_Book","read");
                }
                else
                {
                    PreparedStatement ps_Create_Record=cn.prepareStatement(sql_Create_Record);
                    ps_Create_Record.execute();
                    ps_Create_Record.close();
                    PreparedStatement ps_sql = cn.prepareStatement(sql);
                    PreparedStatement ps_sql_init = cn.prepareStatement(sql_Init_Record);
                    rs = ps_sql.executeQuery();
                    while (rs.next()) {
                        String english = rs.getString(1);
                        String chinese = rs.getString(2);
                        String hint = rs.getString(3);
                        Word word = new Word(english, chinese, vocabularyBook.getName());
                        if (hint != null)
                            word.setHint(hint);
                        vocabularyBook.add2Book(word);
                        ps_sql_init.setString(1, english);
                        ps_sql_init.setInt(3, 0);
                        ps_sql_init.setBoolean(2, false);
                        Log.e("DB_Search_Book:word",ps_sql_init.toString());
                        ps_sql_init.execute();
                    }
                    Log.e("DB_Search_Book","create");
                    ps_sql_init.close();
                    ps_sql.close();
                    rs.close();
                }

            }
            else
            {
                PreparedStatement preparedStatement=cn.prepareStatement(sql);
                preparedStatement.setString(1,search_str);
                rs=preparedStatement.executeQuery();
                while (rs.next())
                {
                    String bookname=rs.getString("book_name");
                    String introduction=rs.getString("introducation");
                    VocabularyBook vocabularyBook=new VocabularyBook(bookname,introduction);
                    bookList.add(vocabularyBook);
                }
                Log.e("DB_Search_Book",String.valueOf(bookList.size()));
                rs.close();
                preparedStatement.close();
            }
            cn.close();
            Log.e("DB_Search_Book","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Search_Book","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Search_Book","Fail for sql");
        }
    }

    public List<VocabularyBook> getBookList() {
        return bookList;
    }

    public VocabularyBook getVocabularyBook()
    {
        return vocabularyBook;
    }

}

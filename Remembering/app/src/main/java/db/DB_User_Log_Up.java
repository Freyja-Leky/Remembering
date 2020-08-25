package db;

import android.util.Log;
import logic_class.User;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;

import java.sql.*;

public class DB_User_Log_Up extends Thread {

    private String sql_user_info;
    private String sql_user_learning_setting;
    private String sql_note;
    private String sql_vocabulary_book;

    private String email;
    private String emailID;
    private String note;
    private User user;


    public DB_User_Log_Up(String email) {
        user=new User(email);
        sql_user_info="SELECT * FROM user_info WHERE ID = ?";
        sql_user_learning_setting="SELECT * FROM user_learning_setting WHERE ID = ?";
        emailID="";
        for (int i=0;i<email.length();i++)
        {
            if (email.charAt(i)!='@'&&email.charAt(i)!='.')
                emailID+=email.charAt(i);
        }
        this.note="note_"+emailID;
        sql_note="SELECT * FROM "+note;
        sql_vocabulary_book="SELECT book_name, introducation FROM vocabulary_book WHERE creater = ?";
        this.email=email;
    }

    public void run()
    {
        Connection cn=null;
        ResultSet rs=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            //user_info
            PreparedStatement ps_user_info = cn.prepareStatement(sql_user_info);
            ps_user_info.setString(1,email);
            rs=ps_user_info.executeQuery();
            if (rs.next())
            {
                user.setName(rs.getString("user_name"));
                user.setSign(rs.getString("sign"));
                user.setDays(rs.getInt("days"));
                java.util.Date date=new java.util.Date(rs.getDate("last_date").getTime());
                user.setLastDate(date);
            }
            ps_user_info.close();

            //user_learning_setting
            PreparedStatement ps_user_learning_setting = cn.prepareStatement(sql_user_learning_setting);
            ps_user_learning_setting.setString(1,email);
            rs=ps_user_learning_setting.executeQuery();
            //to be created after searching books
            String reviewing_book="";
            Boolean isPrivateBook=false;
            if (rs.next())
            {
                user.setMode(rs.getInt("mode"));
                user.setAmount(rs.getInt("amount"));
                reviewing_book=rs.getString("reviewing_book");
//                Log.e("DB_User_Log_Up：Review：",reviewing_book);
            }
            ps_user_learning_setting.close();

            //note
            PreparedStatement ps_note=cn.prepareStatement(sql_note);
            rs= ps_note.executeQuery();
            while (rs.next())
            {
                String English=rs.getString("English");
                String Note=rs.getString("Note");
                Word word=new Word(English,Note);
                user.addNote(word);
            }
            ps_note.close();


            //books
            PreparedStatement ps_vocabulary_book=cn.prepareStatement(sql_vocabulary_book);
            ps_vocabulary_book.setString(1,email);
            rs=ps_vocabulary_book.executeQuery();
            while (rs.next())
            {
                String bookname=rs.getString("book_name");
                Log.e("DB_User_Log_Up：bookname：",bookname);
                String introducation=rs.getString("introducation");
                VocabularyBook vocabularyBook=new VocabularyBook(bookname,introducation);
                String bookrecord=bookname+"_"+emailID;
                String sql;
                sql="SELECT "+bookname+".English, "+bookname+".Chinese, "+bookname+".Hint, "+bookrecord+".Easy, "+bookrecord+".ReviewTime" +
                        " FROM "+bookname+" INNER JOIN "+bookrecord+" ON "+bookrecord+".English = "+bookname+".English";
                Log.e("DB_User_Log_UP",sql);
                PreparedStatement preparedStatement=cn.prepareStatement(sql);
                ResultSet rs4book;
                rs4book=preparedStatement.executeQuery();
                //words in book with review record
                while (rs4book.next())
                {
                    String wordEnglish=rs4book.getString(1);
                    String wordChinese=rs4book.getString(2);
                    String wordhint=rs4book.getString(3);
                    boolean wordEasy=rs4book.getBoolean(4);
                    int wordReviewTime=rs4book.getInt(5);
                    Log.e("DB_User_Log_Up",wordEnglish+" "+String.valueOf(wordReviewTime));
                    Word word=new Word(wordEnglish,wordChinese,bookname);
                    if (wordhint!=null)
                        word.setHint(wordhint);
                    if (wordEasy)
                        word.setEasy();
                    Log.e("DB_User_Log_Up",wordEnglish+" "+String.valueOf(word.getReviewTime()));
                    word.setReviewTime(wordReviewTime);
                    vocabularyBook.add2Book(word);
                }
                user.addBook(vocabularyBook);
                if (bookname.equals(reviewing_book))
                {
                    user.setLearningBook(vocabularyBook);
                    isPrivateBook=true;
                    Log.e("DB_User_Log_Up：getReview",bookname+"1");
                }
                preparedStatement.close();
            }
            ps_vocabulary_book.close();
            ps_user_learning_setting.close();

            //reviewing book
            if (!isPrivateBook)
            {
                String sql_get_Book="SELECT introducation FROM vocabulary_book WHERE book_name = ?";
                PreparedStatement ps_get_Book=cn.prepareStatement(sql_get_Book);
                ps_get_Book.setString(1,reviewing_book);
                ResultSet rs_book=ps_get_Book.executeQuery();
                rs_book.next();
                String intro=rs_book.getString(1);
                VocabularyBook vb=new VocabularyBook(reviewing_book,intro);
                DB_Search_Book db_search_book=new DB_Search_Book(vb,email);
                db_search_book.start();
                try {
                    db_search_book.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user.setLearningBook(db_search_book.getVocabularyBook());
                ps_get_Book.close();
                rs_book.close();
            }



            cn.close();;
            Log.e("DB_User_Log_Up","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_User_Log_Up","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_User_Log_Up","Fail for sql");
        }
    }

    public User getUser() {
        return user;
    }
}

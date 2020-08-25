package db;

import android.util.Log;
import logic_class.word_class.Word;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Note extends Thread{

    private String sql;
    private String English;
    private String Note;
    private String emailID;
    private String db_note;
    private int type;

    //1 for add;2 for delete;3 for update
    public DB_Note(int type, Word W,String email)
    {
        emailID="";
        for (int i=0;i<email.length();i++)
        {
            if (email.charAt(i)!='@'&&email.charAt(i)!='.')
                emailID+=email.charAt(i);
        }
        this.db_note="note_"+emailID;
        this.English=W.getEnglish();
        this.Note=W.getNote();
        this.type=type;
        switch (type)
        {
            case 1:
                sql = "INSERT INTO "+db_note+" VALUES(?, ?)";
                break;
            case 2:
                sql = "DELETE FROM "+db_note+" WHERE English = ?";
                break;
            case 3:
                sql = "UPDATE "+db_note+" SET Note = ? WHERE English = ?";
                break;
            default:
                sql="";
        }
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            switch (type)
            {
                case 1:
                    preparedStatement.setString(1,English);
                    preparedStatement.setString(2,Note);
                    break;
                case 2:
                    preparedStatement.setString(1,English);
                    break;
                case 3:
                    preparedStatement.setString(1,Note);
                    preparedStatement.setString(2,English);
                    break;
            }
            preparedStatement.execute();
            preparedStatement.close();
            cn.close();;
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

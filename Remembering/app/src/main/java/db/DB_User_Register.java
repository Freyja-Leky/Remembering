package db;

import android.util.Log;
import logic_class.VocabularyBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_User_Register extends Thread {

    private String sql_user_list;
    private String sql_user_info;
    private String sql_user_learning_setting;
    private String sql_note;

    private String email;
    private String name;
    private String password;
    private String note;

    public DB_User_Register(String email,String name,String password)
    {
        String emailID="";
        for (int i=0;i<email.length();i++)
        {
            if (email.charAt(i)!='@'&&email.charAt(i)!='.')
                emailID+=email.charAt(i);
        }
        this.note="note_"+emailID;
        this.email=email;
        this.name=name;
        this.password=password;
        sql_user_list="INSERT INTO user_list VALUES(?, ?)";
        sql_user_info="INSERT INTO user_info (ID, user_name, days, last_date) VALUES(?, ?, ?, ?)";
        sql_user_learning_setting="INSERT INTO user_learning_setting  VALUES(?, ?, ?, ?)";
        sql_note="Create TABLE IF NOT EXISTS "+note+" (English VARCHAR(255) PRIMARY KEY, Note VARCHAR(255));";
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            //user list
            PreparedStatement ps_user_list=cn.prepareStatement(sql_user_list);
            ps_user_list.setString(1,email);
            ps_user_list.setString(2,password);
            if (ps_user_list.execute())
                Log.e("DB_User_Register","user_list true");
            ps_user_list.close();
            //user info
            PreparedStatement ps_user_info=cn.prepareStatement(sql_user_info);
            ps_user_info.setString(1,email);
            ps_user_info.setString(2,name);
            ps_user_info.setInt(3,0);
            java.sql.Date sqlDate=java.sql.Date.valueOf("2016-12-01");
            ps_user_info.setDate(4,sqlDate);
            if (ps_user_info.execute())
            {
                Log.e("DB_User_Register","user_info true");
            }
            ps_user_info.close();
            //user learning setting
            PreparedStatement ps_user_learning_setting=cn.prepareStatement(sql_user_learning_setting);
            ps_user_learning_setting.setString(1,email);
            ps_user_learning_setting.setInt(2,1);
            ps_user_learning_setting.setInt(3,1);
            ps_user_learning_setting.setString(4,"testbook2");
            if (ps_user_learning_setting.execute())
                Log.e("DB_User_Register","user_learning_setting true");
            ps_user_learning_setting.close();
            //user note
            PreparedStatement ps_note=cn.prepareStatement(sql_note);
            if (ps_note.execute())
                Log.e("DB_User_Register","note true");
            ps_note.close();

            cn.close();
            Log.e("DB_User_Register","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_User_Register","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_User_Register","Fail for sql");
        }
    }
}

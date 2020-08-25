package db;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Change_Password extends Thread {

    private String sql;
    private String email;
    private String pwd;

    public DB_Change_Password(String email,String pwd)
    {
        sql="UPDATE user_list SET password = ? WHERE ID = ?";
        this.email=email;
        this.pwd=pwd;
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setString(1,pwd);
            preparedStatement.setString(2,email);
            preparedStatement.execute();
            preparedStatement.close();
            cn.close();;
            Log.e("DB_Change_Password","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Change_Password","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Change_Password","Fail for sql");
        }
    }
}

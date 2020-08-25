package db;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Change_Info extends Thread {

    private String sql;
    private String parameter;
    private String email;

    //true for username;false for sign
    public DB_Change_Info(String info,boolean type,String email)
    {
        this.parameter=info;
        this.email=email;
        if (type)
            sql = "UPDATE user_info SET user_name = ? WHERE ID = ?";
        else
            sql = "UPDATE user_info SET sign = ? WHERE ID = ?";
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setString(1,parameter);
            preparedStatement.setString(2,email);
            preparedStatement.execute();
            preparedStatement.close();
            cn.close();;
            Log.e("DB_Change_Info","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Change_Info","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Change_Info","Fail for sql");
        }
    }
}

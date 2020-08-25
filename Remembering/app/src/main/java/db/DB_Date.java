package db;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Date extends Thread {

    private java.util.Date today;
    private String sql;
    private String email;
    private int days;

    public DB_Date(java.util.Date today,int days,String email)
    {
        this.today=today;
        this.days=days;
        this.email=email;
        sql="UPDATE user_info SET last_date = ? , days = ? WHERE ID = ?";
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setDate(1,new java.sql.Date(today.getTime()));
            preparedStatement.setInt(2,days);
            preparedStatement.setString(3,email);
            preparedStatement.execute();
            preparedStatement.close();
            cn.close();;
            Log.e("DB_Change_Date","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Change_Date","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Change_Date","Fail for sql");
        }
    }
}

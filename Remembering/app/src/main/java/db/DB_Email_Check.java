package db;

import android.util.Log;

import java.sql.*;

public class DB_Email_Check extends Thread {

    private String sql;
    private String email;
    private boolean result;


    public DB_Email_Check(String parameter)
    {
        sql="select * from user_list where ID = ?";
        email=parameter;
    }

    public void run()
    {
        Connection cn=null;
        ResultSet resultSet=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement preparedStatement= cn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            resultSet=preparedStatement.executeQuery();
            if (resultSet.next())
            {
//                Log.e("DB_Check_Info","true");
                result=false;
            }
            else
                result=true;
            preparedStatement.close();
            cn.close();;
            Log.e("DB_Email_Check","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Email_Check","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Email_Check","Fail for sql");
        }
    }

    public boolean getResult(){
        return result;
    }
}

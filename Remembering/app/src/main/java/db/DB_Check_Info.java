package db;

import android.util.Log;

import java.sql.*;

public class DB_Check_Info extends Thread {
    private String sql_email;
    private String sql;
    private String email,password;
    private boolean result;


    public DB_Check_Info(String parameter,String parameter2)
    {
        sql="select * from user_list where ID = ? and password = ?";
        email=parameter;
        password=parameter2;
    }

    public void run()
    {
        Connection cn=null;
        ResultSet resultSet=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
//            Log.e("DB_Check_Info",preparedStatement.toString());
            resultSet=preparedStatement.executeQuery();
            if (resultSet.next())
//                Log.e("DB_Check_Info","true");
                result=true;
            else
                result=false;
            preparedStatement.close();
            resultSet.close();
            cn.close();;
            Log.e("DB_Check_Info","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Check_Info","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Check_Info","Fail for sql");
        }
    }

    public boolean getResult(){
        return result;
    }
}

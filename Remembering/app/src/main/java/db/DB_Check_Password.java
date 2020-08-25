package db;

import android.util.Log;

import java.sql.*;

public class DB_Check_Password extends Thread {
    private String sql;
    private String email;
    private String pwd;
    private ResultSet resultSet;
    private boolean result;

    public DB_Check_Password(String email,String pwd)
    {
        sql="SELECT * FROM user_list WHERE ID = ? AND password = ?";
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
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,pwd);
            resultSet=preparedStatement.executeQuery();
            if (!resultSet.next())
                result=false;
            else
                result=true;
            preparedStatement.close();
            cn.close();;
            Log.e("DB_Check_Password","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Check_Password","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Check_Password","Fail for sql");
        }
    }

    public boolean getResult(){
        return result;
    }
}

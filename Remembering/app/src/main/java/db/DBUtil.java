package db;

import android.util.Log;
import java.sql.*;

public class DBUtil extends Thread {

    private String cmd;

    private String sql;
    //1 for select;2 for update;3 for insert;4 for delete;
    private int cmdType;


    private ResultSet resultSet;


    public DBUtil(String cmd,String parameter)
    {
        switch (cmd)
        {
            case "EmailCheck":
                sql="select * from user_list where ID = "+parameter+";";
                break;

        }
    }

    public DBUtil(String cmd,String parameter1,String parameter2)
    {
        switch (cmd)
        {
            case "CheckInfo":
                sql="select * from user_list where ID = "+parameter1+" and password = "+parameter2+";";
                break;

        }
    }

    public DBUtil(String cmd,String parameter1,String parameter2,String parameter3)
    {
        switch (cmd)
        {
            case "Register":

        }
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2","testuser","44678ghost");
            cn.close();;
            Log.e("MainActivity","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("MainActivity","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("MainActivity","Fail for sql");
        }
    }

}

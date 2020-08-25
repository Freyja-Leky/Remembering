package db;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Change_Mode extends Thread {

    private String sql;
    private int parameter;
    private String book;
    private String email;
    private boolean type;

    //true for mode or amount;false for book

    public DB_Change_Mode(int parameter,boolean type,String email)
    {
        this.parameter=parameter;
        this.email=email;
        if (type)
            sql = "UPDATE user_learning_setting SET mode = ? WHERE ID = ?";
        else
            sql = "UPDATE user_learning_setting SET amount = ? WHERE ID = ?";
        this.type=true;
    }

    public DB_Change_Mode(String book,String email)
    {
        this.book=book;
        this.email=email;
        sql = "UPDATE user_learning_setting SET reviewing_book = ? WHERE ID = ?";
        this.type=false;
    }

    public void run()
    {
        Connection cn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/test_db2?characterEncoding=gbk","testuser","44678ghost");
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            if (type)
                preparedStatement.setInt(1,parameter);
            else
                preparedStatement.setString(1,book);
            preparedStatement.setString(2,email);
            preparedStatement.execute();
            preparedStatement.close();
            cn.close();;
            Log.e("DB_Change_Mode","Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DB_Change_Mode","Fail for class");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_Change_Mode","Fail for sql");
        }
    }

}

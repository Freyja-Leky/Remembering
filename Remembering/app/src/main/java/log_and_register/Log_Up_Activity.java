package log_and_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.freyja.testthree.R;
import db.DB_Check_Info;
import db.DB_Email_Check;
import db.DB_User_Log_Up;
import global_data.Data;
import logic_class.User;
import menu_page.User_Info_Page_Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log_Up_Activity extends Activity {

    private Button btnRegister;
    private Button btnSignUp;
    private EditText inputEmail;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_up);

        btnSignUp=(Button)findViewById(R.id.SignUp);
        btnRegister=(Button)findViewById(R.id.Register);
        inputEmail=(EditText)findViewById(R.id.Email);
        inputPassword=(EditText)findViewById(R.id.Password);

        inputPassword.setInputType(129);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Log_Up_Activity.this,Register_Activity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=inputEmail.getText().toString();
                String password=inputPassword.getText().toString();
                //check User Info

                //format Email
                String mailRegex,mailName,mailDomain;
                mailName="^[0-9a-z]+\\w*";       //^表明一行以什么开头；^[0-9a-z]表明要以数字或小写字母开头；\\w*表明匹配任意个大写小写字母或数字或下划线
                mailDomain="([0-9a-z]+\\.)+[0-9a-z]+$";       //***.***.***格式的域名，其中*为小写字母或数字;第一个括号代表有至少一个***.匹配单元，而[0-9a-z]$表明以小写字母或数字结尾
                mailRegex=mailName+"@"+mailDomain;          //邮箱正则表达式      ^[0-9a-z]+\w*@([0-9a-z]+\.)+[0-9a-z]+$
                Pattern pattern = Pattern.compile(mailRegex);
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches())
                {
                    Toast.makeText(Log_Up_Activity.this,"邮箱格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!CheckEmail(email))
                {
                    Toast.makeText(Log_Up_Activity.this,"邮箱未注册",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check Info from db
                if (!CheckPwd(email,password))
                {
                    Toast.makeText(Log_Up_Activity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    return;
                }

                //transfor data to center
                DB_User_Log_Up db_user_log_up=new DB_User_Log_Up(email);
                db_user_log_up.start();
                try {
                    db_user_log_up.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final Data data=(Data)getApplication();
                User user;
                user=db_user_log_up.getUser();
                data.setUser(user);

//                //skip to menu
                Intent intent=new Intent(Log_Up_Activity.this, User_Info_Page_Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean CheckPwd(String email, String password){
        DB_Check_Info db_check_info=new DB_Check_Info(email,password);
        db_check_info.start();
        try {
            db_check_info.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return db_check_info.getResult();
    }

    private boolean CheckEmail(String email)
    {
        DB_Email_Check db_email_check=new DB_Email_Check(email);
        db_email_check.start();
        try {
            db_email_check.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (!db_email_check.getResult());
    }

}

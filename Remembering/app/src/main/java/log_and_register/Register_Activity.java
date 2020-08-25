package log_and_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.freyja.testthree.R;
import db.DBUtil;
import db.DB_Email_Check;
import db.DB_User_Register;
import global_data.Data;
import logic_class.User;
import menu_page.User_Info_Page_Activity;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_Activity extends Activity {

    private Button btnRegister;
    private EditText inputUsername;
    private EditText inputEmail;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btnRegister=(Button)findViewById(R.id.SignUp);
        inputEmail=(EditText)findViewById(R.id.Email);
        inputUsername=(EditText)findViewById(R.id.Username);
        inputPassword=(EditText)findViewById(R.id.Password);

        inputPassword.setInputType(129);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String name = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                //format Email
                String mailRegex, mailName, mailDomain;
                mailName = "^[0-9a-z]+\\w*";
                mailDomain = "([0-9a-z]+\\.)+[0-9a-z]+$";
                mailRegex = mailName + "@" + mailDomain;
                Pattern pattern = Pattern.compile(mailRegex);
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches()) {
                    Toast.makeText(Register_Activity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                //email check from database
                try {
                    if (!EmailCheck(email)) {
                        Toast.makeText(Register_Activity.this, "该邮箱已被注册", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //User Name Length

                if (name.length()>8||name.length()<2)
                {
                    Toast.makeText(Register_Activity.this, "用户名长度应为2-8", Toast.LENGTH_SHORT).show();
                    return;
                }

//                Email send
//                Save user to database and create user
                try {
                    register(email, name, password);
                } catch (ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
                //Skip to menu page
                Intent intent = new Intent(Register_Activity.this, User_Info_Page_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean EmailCheck(String email) throws InterruptedException {
        DB_Email_Check db_email_check=new DB_Email_Check(email);
        db_email_check.start();
        db_email_check.join();
        return db_email_check.getResult();
    }

    private void register(String email,String name,String password) throws ParseException, InterruptedException {
        DB_User_Register db_user_register=new DB_User_Register(email,name,password);
        db_user_register.start();
        db_user_register.join();
        User user=new User(email,name);
        final Data data=(Data)getApplication();
        data.setUser(user);
    }
}

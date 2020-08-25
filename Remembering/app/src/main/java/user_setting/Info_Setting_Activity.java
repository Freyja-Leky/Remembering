package user_setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import global_data.Data;
import info_setting_part.Change_Info_Activity;
import menu_page.User_Info_Page_Activity;

public class Info_Setting_Activity extends Activity {
    private Button btnBack;
    private Button btnModifyUsername;
    private Button btnModifySign;
    private Button btnPassword;

    private TextView name;
    private TextView sign;

    private String name_str;
    private String sign_str;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_setting);

        getLayout();
        getDataFromGlobal();
        setButton();
        setView();
    }

    //get Button and TextView
    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        btnModifyUsername=(Button)findViewById(R.id.Name_Setting);
        btnPassword=(Button)findViewById(R.id.Password_Setting);
        btnModifySign=(Button)findViewById(R.id.Sign_Setting);
        name=(TextView)findViewById(R.id.Name_data);
        sign=(TextView)findViewById(R.id.Sign_data);
    }

    private void getDataFromGlobal()
    {
        final Data data=(Data)getApplication();
        name_str=data.getName();
        sign_str=data.getSign();
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(4);
            }
        });

        btnModifyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(1);
            }
        });

        //need check twice
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(3);
            }
        });

        btnModifySign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(2);
            }
        });
    }

    private void setView(){
        name.setText(name_str);
        sign.setText(sign_str);
    }

    //=1 for name; =2 for sign;=3 for password;=4 for back
    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(Info_Setting_Activity.this, Change_Info_Activity.class);
                intent.putExtra("title","用户名修改");
                intent.putExtra("flag",1);
                break;
            case 2:
                intent=new Intent(Info_Setting_Activity.this, Change_Info_Activity.class);
                intent.putExtra("title","个性签名修改");
                intent.putExtra("flag",2);
                break;
            case 3:
                intent=new Intent(Info_Setting_Activity.this, Change_Info_Activity.class);
                intent.putExtra("title","请输入原密码");
                intent.putExtra("flag",3);
                break;
            case 4:
                intent=new Intent(Info_Setting_Activity.this, User_Info_Page_Activity.class);
                break;
            default:
                intent=new Intent(Info_Setting_Activity.this, Info_Setting_Activity.class);
        }
        startActivity(intent);
    }
}

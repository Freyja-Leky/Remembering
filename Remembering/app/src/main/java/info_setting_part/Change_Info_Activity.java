package info_setting_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.*;
import com.example.freyja.testthree.R;
import global_data.Data;
import user_setting.Info_Setting_Activity;

import java.sql.SQLException;

public class Change_Info_Activity extends Activity {
    private Button btnBack;
    private Button btnAdmit;
    private TextView Title;
    private EditText Modify;
    private TextView RuleHint;

    private String title_str;
    private String to_be_modified;
    private String rule_str;
    private String pwd;
    private int flag;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_info);

        getLayout();
        getDataFromIntent();
        setButton();
        setView();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        Title=(TextView)findViewById(R.id.change_title);
        Modify=(EditText)findViewById(R.id.info);
        btnAdmit=(Button)findViewById(R.id.change);
        RuleHint=(TextView)findViewById(R.id.RuleHint);
    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        title_str=intent.getStringExtra("title");
        flag=intent.getIntExtra("flag",0);
        final Data data=(Data)getApplication();
        switch (flag)
        {
            case 1:
                to_be_modified=data.getName();
                rule_str="用户名长度应控制在2-8个字符";
                Modify.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                break;
            case 2:
                to_be_modified=data.getSign();
                rule_str="个性签名最长为30个字符";
                Modify.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                break;
            default:
                rule_str="两次密码不得重复";
                to_be_modified="";

        }
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(1);

            }
        });

        btnAdmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(2);
            }
        });
    }

    private void setView()
    {
        Title.setText(title_str);
        Modify.setText(to_be_modified);
        RuleHint.setText(rule_str);
        if (flag==3)
        {
            //can not see the password
            Modify.setInputType(129);
            btnAdmit.setText("确认");
        }
    }

    //Set rules for sign or name
    private boolean checkModify(String str) {
        if (str.equals("")) {
            Toast.makeText(Change_Info_Activity.this, "用户名不可为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (str.length() < 2)
        {
            Toast.makeText(Change_Info_Activity.this, "用户名过短", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkPassword(String str)
    {
        if (pwd.equals(str))
            return false;
        return true;
    }

    //page=1 for back;page=2 for admit;
    private void skipPage(int page){
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(Change_Info_Activity.this,Info_Setting_Activity.class);
                break;
            case 2:
                String str=Modify.getText().toString();
                final Data data=(Data)getApplication();
                switch (flag)
                {
                    case 1:
                        if (!checkModify(str))
                            return;
                        data.setName(str);
                        intent=new Intent(Change_Info_Activity.this,Info_Setting_Activity.class);
                        break;
                    case 2:
                        data.setSign(str);
                        intent=new Intent(Change_Info_Activity.this,Info_Setting_Activity.class);
                        break;
                    case 3:
                        try {
                            if (!data.checkPassword(str))
                            {
                                Toast.makeText(Change_Info_Activity.this,"原密码错误",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pwd=str;
                        Title.setText("修改密码");
                        btnAdmit.setText("完成");
                        Modify.setText("");
                        Modify.setInputType(128);
                        flag=4;
                        return;
                    case 4:
                        if (!checkPassword(str))
                        {
                            Toast.makeText(Change_Info_Activity.this,"密码重复",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        data.setPassword(str);
                        intent=new Intent(Change_Info_Activity.this,Info_Setting_Activity.class);
                        break;
                    default:
                        intent=new Intent(Change_Info_Activity.this,Change_Info_Activity.class);
                }
                Toast.makeText(Change_Info_Activity.this,"修改成功",Toast.LENGTH_SHORT).show();
                break;
            default:
                intent=new Intent(Change_Info_Activity.this,Change_Info_Activity.class);
        }
        startActivity(intent);
        finish();
    }
}

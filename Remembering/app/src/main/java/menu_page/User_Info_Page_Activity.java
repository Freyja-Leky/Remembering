package menu_page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import user_setting.Book_List_Activity;
import user_setting.Info_Setting_Activity;
import user_setting.Learning_Manage_Activity;
import user_setting.Note_List_Activity;
import global_data.Data;

public class User_Info_Page_Activity extends Activity {

    private Button btnFriendList;
    private Button btnStartPage;
    private Button btnLearningSetting;
    private Button btnBook;
    private Button btnNote;
    private Button btnUserSetting;
    private TextView name;
    private TextView sign;
    private ImageView Avatar;

    private String name_str,sign_str;
    private int avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_page);

        getLayout();
        getDataFromGlobal();
        setButton();
        setView();
    }

    //get Button ImageView and TextView
    private void getLayout(){
        btnFriendList=(Button)findViewById(R.id.Friend_list);
        btnStartPage=(Button)findViewById(R.id.Start_Page);
        btnLearningSetting=(Button)findViewById(R.id.LearningManage);
        btnBook=(Button)findViewById(R.id.Vacbulary);
        btnNote=(Button)findViewById(R.id.Note);
        btnUserSetting=(Button)findViewById(R.id.Settings);
        name=(TextView)findViewById(R.id.Username);
        sign=(TextView)findViewById(R.id.PersonalSign);
        Avatar=(ImageView) findViewById(R.id.Avatar);
    }

    //get User Data
    private void getDataFromGlobal(){
        final Data data=(Data)getApplication();
        name_str=data.getName();
        sign_str=data.getSign();
        avatar=data.getAvatar();
        //from settings
    }

    //set Button
        private void setButton(){
            btnFriendList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skipPage(1);
                }
            });
//
            btnStartPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skipPage(2);
                }
            });
//
            btnLearningSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skipPage(3);
                }
            });
//
            btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skipPage(4);
                }
            });
//
            btnNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skipPage(5);
                }
            });
//
            btnUserSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skipPage(6);
                }
            });

        }

    //set TextView and ImageView
    private void setView(){
        name.setText(name_str);
        sign.setText(sign_str);
        Avatar.setImageResource(avatar);
    }

    //page=1 for friend list;page=2 for start;page=3 for learning manage;page=4 for book;page=5 for note;page=6 for system
    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(User_Info_Page_Activity.this,Friend_List_Activity.class);
                break;
            case 2:
                intent=new Intent(User_Info_Page_Activity.this,Start_Page_Activity.class);
                break;
            case 3:
                intent=new Intent(User_Info_Page_Activity.this, Learning_Manage_Activity.class);
                break;
            case 4:
                intent=new Intent(User_Info_Page_Activity.this, Book_List_Activity.class);
                break;
            case 5:
                intent=new Intent(User_Info_Page_Activity.this, Note_List_Activity.class);
                break;
            case 6:
                intent=new Intent(User_Info_Page_Activity.this, Info_Setting_Activity.class);
                break;
            default:
                intent=new Intent(User_Info_Page_Activity.this, User_Info_Page_Activity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

}

package menu_page;

import Review.Review_Chinese_Page_Activity;
import Review.Review_English_Page_Activity;
import Spell.Spell_English_Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import global_data.Data;

public class Start_Page_Activity extends Activity {

    private Button btnFriendList;
    private Button btnUserInfo;
    private Button btnStart;

    private TextView bookTitle;
    private TextView days;
    private TextView newWord;
    private TextView oldWord;
    private TextView allWord;

    private int day_num,new_num,old_num,all_num;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);
        View view=findViewById(R.id.start_page);
        view.getBackground().setAlpha(120);

        getLayout();
        getDataFromGlobal();
        setButton();
        setTextView();
    }

    //get Button and TextView
    private void getLayout()
    {
        btnFriendList=(Button)findViewById(R.id.Friend_list);
        btnUserInfo=(Button)findViewById(R.id.User_Info);
        btnStart=(Button)findViewById(R.id.start);

        bookTitle=(TextView)findViewById(R.id.WordBook) ;
        days=(TextView)findViewById(R.id.CountingDay);
        newWord=(TextView)findViewById(R.id.NewWord);
        oldWord=(TextView)findViewById(R.id.ReviewWord);
        allWord=(TextView)findViewById(R.id.WholeWord);
    }

    //get Learning Data
    private void getDataFromGlobal(){
        final Data data=(Data)getApplication();
        data.BuildWordList();
        all_num=data.getRemainAll();
        old_num=data.getRemainOld();
        new_num=data.getRemainNew();
        day_num=data.getDays();
        title=data.getTitle();
    }

    //set Button Action
    private void setButton() {
        //Skip to Friend List
        btnFriendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(1);
            }
        });

        //Skip to UserInfo
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(2);
            }
        });

        //End
//        if (isReviewEnd)
//        {
//            btnStart.setClickable(false);
//            btnStart.setText("学习完成");
//            return;
//        }
        //Skip to Learning View
        final Data data=(Data)getApplication();
        if (data.isReviewEnd()&&data.isBookOver())
        {
            btnStart.setClickable(false);
            btnStart.setText("请切换词库");
        }
        else if (data.isReviewEnd())
        {
            btnStart.setClickable(true);
            btnStart.setText("开启下一轮");
        }
        else
        {
            btnStart.setClickable(true);
            btnStart.setText("开始学习");
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.isReviewEnd())
                    data.setWordList();
                final Data data=(Data)getApplication();
                if (data.getMode()==1)
                    skipPage(3);
                else
                    skipPage(4);
            }
        });
    }

    //set TextView
    private void setTextView(){
        bookTitle.setText(title);

        String str="";
        str=String.valueOf(day_num);
        str+=" Days";
        days.setText(str);

        str=String.valueOf(new_num);
//        Log.e("StartPage:","new: "+new_num);
        newWord.setText(str);
        str=String.valueOf(old_num);
//        Log.e("StartPage:","old: "+old_num);
        oldWord.setText(str);
        str=String.valueOf(all_num);
//        Log.e("StartPage:","all: "+all_num);
        allWord.setText(str);
    }

    //page=1 for friend list;page=2 for user info;page=3 for start
    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(Start_Page_Activity.this,Friend_List_Activity.class);
                break;
            case 2:
                intent=new Intent(Start_Page_Activity.this,User_Info_Page_Activity.class);
                break;
            case 3:
                intent=new Intent(Start_Page_Activity.this, Review_English_Page_Activity.class);
                break;
            case 4:
                intent=new Intent(Start_Page_Activity.this, Spell_English_Activity.class);
                break;
            default:
                intent=new Intent(Start_Page_Activity.this, Start_Page_Activity.class);
        }
        startActivity(intent);
        finish();
    }

}

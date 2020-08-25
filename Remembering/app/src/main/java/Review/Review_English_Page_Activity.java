package Review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import logic_class.word_class.Word2Review;
import menu_page.Start_Page_Activity;

public class Review_English_Page_Activity extends Activity {

    private Button btnBack;
    private Button btnSetEasy;
    private Button btnYes;
    private Button btnNo;

    private ProgressBar pb;

    private TextView word;
    private TextView hint;
    private TextView note;
    private TextView hint_title;
    private TextView note_title;

    private LinearLayout linearLayout;

    private int new_num;
    private int old_num;
    private int all_num;
    private Word2Review W;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_english);
        //Layout
        getLayout();
        //Data
        getDataFromGlobal();
        //Function
        setView();
        setPb();
        setButton();
    }

    private void getLayout(){
        btnBack=(Button)findViewById(R.id.Return);
        btnSetEasy=(Button)findViewById(R.id.TobeEasy);
        btnYes=(Button)findViewById(R.id.GotIt);
        btnNo=(Button)findViewById(R.id.DontKnow);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        word=(TextView)findViewById(R.id.Word);
        hint=(TextView)findViewById(R.id.SystemHint);
        note=(TextView)findViewById(R.id.NoteHint);
        linearLayout=(LinearLayout)findViewById(R.id.Line3);
        hint_title=(TextView)findViewById(R.id.Hint_Title);
        note_title=(TextView)findViewById(R.id.Note_Title);
    }

    private void getDataFromGlobal()
    {

        final Data wl=(Data)getApplication();
        W=wl.sendWord();
        all_num=wl.getListAmount();
        new_num=wl.getRemainOld();
        old_num=wl.getRemainNew();
        Log.e("Review_English_Page:new",String.valueOf(new_num));
        Log.e("Review_English_Page:old",String.valueOf(old_num));
        Log.e("Review_English_Page:all",String.valueOf(all_num));
    }

    private void setButton(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(1);
            }
        });

        btnSetEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set Easy
                W.setEasy();
                final Data wl=(Data)getApplication();
                wl.updateRecord(W.getWord());
                wl.deleteFromWordList(W);
                Toast.makeText(Review_English_Page_Activity.this,"太简单了！",Toast.LENGTH_SHORT).show();
                skipPage(2);
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Data wl=(Data)getApplication();
                if (W.getIsFirstTime())
                {
                    W.setKnown();
                    wl.updateRecord(W.getWord());
                    wl.deleteFromWordList(W);
                }
                else
                {
                    W.initialFirstTime();
                }
                skipPage(2);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (W.getIsFirstTime())
                {
                    //first time
                    hint.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    note.setVisibility(View.VISIBLE);
                    hint_title.setVisibility(View.VISIBLE);
                    note_title.setVisibility(View.VISIBLE);
                    W.Fail4FirstTime();
                    return;
                }
                else
                {
                    //second time
                    W.initialFirstTime();
                    skipPage(2);
                }
            }
        });
    }

    private void setView(){
        word.setText(W.getWordEnglish());
        if (W.getWordHint().equals(""))
            hint.setText("无例句");
        else
            hint.setText(W.getWordHint());
        if (W.getWordNote().equals(""))
            note.setText("无笔记，现在去添加！");
        else
            note.setText(W.getWordNote());
    }

    private void setPb()
    {
        pb.setMax(all_num);
        pb.setProgress(all_num-new_num-old_num);
    }

    //page=1 for back;page=2 for chinese;
    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(Review_English_Page_Activity.this, Start_Page_Activity.class);
                break;
            case 2:
                intent=new Intent(Review_English_Page_Activity.this, Review_Chinese_Page_Activity.class);
                intent.putExtra("word",new Gson().toJson(W));
                break;
            default:
                intent=new Intent(Review_English_Page_Activity.this, Start_Page_Activity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}

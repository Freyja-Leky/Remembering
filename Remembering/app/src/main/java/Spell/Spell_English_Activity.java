package Spell;

import Review.Review_Chinese_Page_Activity;
import Review.Review_English_Page_Activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import logic_class.word_class.Word2Review;
import menu_page.Start_Page_Activity;
import org.w3c.dom.Text;

public class Spell_English_Activity extends Activity {

    private Button btnBack;
    private Button btnSetEasy;
    private Button btnYes;
    private Button btnNo;

    private ProgressBar pb;

    private TextView hint;
    private TextView note;
    private TextView spell_title;
    private EditText word;

    private int new_num;
    private int old_num;
    private int all_num;
    private Word2Review W;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spell_english);
        getLayout();
        getDataFromGlobal();
        setPb();
        setView();
        setButton();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        btnSetEasy=(Button)findViewById(R.id.TobeEasy);
        btnYes=(Button)findViewById(R.id.GotIt);
        btnNo=(Button)findViewById(R.id.DontKnow);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        hint=(TextView)findViewById(R.id.ChineseMeaning);
        note=(TextView)findViewById(R.id.NoteHint);
        spell_title=(TextView)findViewById(R.id.spell_title);
        word=(EditText)findViewById(R.id.Word);
    }

    private void getDataFromGlobal()
    {

        final Data wl=(Data)getApplication();
        W=wl.sendWord();
        all_num=wl.getListAmount();
        new_num=wl.getRemainOld();
        old_num=wl.getRemainNew();
    }

    private void setView()
    {
        hint.setText(W.getWordChinese());
        if (W.getWordNote().equals(""))
            note.setText("当前无笔记");
        else
            note.setText(W.getWordNote());
        spell_title.setText("该单词长度为："+W.getWordEnglish().length());
        word.setFilters(new InputFilter[]{new InputFilter.LengthFilter(W.getWordEnglish().length())});
    }

    private void setPb()
    {
        pb.setMax(all_num);
        pb.setProgress(all_num-new_num-old_num);
    }

    private void setButton()
    {
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
                Toast.makeText(Spell_English_Activity.this,"太简单了！",Toast.LENGTH_SHORT).show();
                skipPage(2);
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans=word.getText().toString();
                final Data wl=(Data)getApplication();
                if (ans.equals(W.getWordEnglish()))
                {
                    W.setKnown();
                    wl.updateRecord(W.getWord());
                    wl.deleteFromWordList(W);
                    skipPage(2);
                }
                else
                {
                    Drawable drawable= getResources().getDrawable(R.drawable.error);
                    drawable.setBounds(0,0,60,60);
                    word.setError("错误",drawable);
//                    Toast.makeText(Spell_English_Activity.this,"拼写错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(2);
            }
        });

    }

    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(Spell_English_Activity.this, Start_Page_Activity.class);
                break;
            case 2:
                intent=new Intent(Spell_English_Activity.this, Review_Chinese_Page_Activity.class);
                intent.putExtra("word",new Gson().toJson(W));
                break;
            default:
                intent=new Intent(Spell_English_Activity.this, Start_Page_Activity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}

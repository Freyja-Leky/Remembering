package Review;

import Spell.Spell_English_Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import review_add_book.Book_Select_Activity;
import logic_class.word_class.Word2Review;
import menu_page.Start_Page_Activity;
import note_part.Note_Change_Activity;
import note_part.Note_Create_Activity;


public class Review_Chinese_Page_Activity extends Activity {
    private Button btnBack;
    private Button btnSetEasy;
    private Button btnNext;
    private Button btnAdd2Book;

    private Button btnModify;
    private Button btnCreate;

    private ProgressBar pb;

    private TextView word;
    private TextView chinese;
    private TextView note;
    private TextView note_title;
    private LinearLayout linearLayout;

    private int new_num;
    private int old_num;
    private int all_num;
    private Word2Review W;
    private boolean isEnd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_chinese);
        //Layout
        getLayout();
        //Data
        getDataFromGlobal();
        getDataFromIntent();
        //Function
        setView();
        setPb();
        setButton();
    }

    private void getLayout(){
        btnBack=(Button)findViewById(R.id.Return);
        btnSetEasy=(Button)findViewById(R.id.TobeEasy);
        btnNext=(Button)findViewById(R.id.Next);
        btnAdd2Book=(Button)findViewById(R.id.AddtoBook);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        word=(TextView)findViewById(R.id.Word);
        chinese=(TextView)findViewById(R.id.ChineseMeaning);
        note=(TextView)findViewById(R.id.NoteHint);
        btnModify=(Button)findViewById(R.id.Modify);
        btnCreate=(Button)findViewById(R.id.Create);
        note_title=(TextView)findViewById(R.id.Note_Title);
        linearLayout=(LinearLayout) findViewById(R.id.Line3);
    }

    private void getDataFromGlobal()
    {
        final Data wl=(Data)getApplication();
        all_num=wl.getListAmount();
        new_num=wl.getRemainNew();
        old_num=wl.getRemainOld();
        isEnd=wl.isReviewEnd();
    }

    private void getDataFromIntent()
    {
        String WJson=getIntent().getStringExtra("word");
        Log.e("Chinese w2r:",WJson);
        W=new Gson().fromJson(WJson,Word2Review.class);
    }

    private void setView()
    {
        word.setText(W.getWordEnglish());
        chinese.setText(W.getWordChinese());
        if (W.getWordNote().equals(""))
        {
            btnCreate.setVisibility(View.VISIBLE);
            note_title.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            note.setVisibility(View.INVISIBLE);
            btnModify.setVisibility(View.INVISIBLE);
        }
        else
        {
            btnCreate.setVisibility(View.INVISIBLE);
            note.setVisibility(View.VISIBLE);
            note.setText(W.getWordNote());
            btnModify.setVisibility(View.VISIBLE);
            note_title.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        if (W.isEasy())
        {
            btnSetEasy.setBackgroundResource(R.drawable.cancel);
        }
//        Log.e("WordList:new",String.valueOf(new_num));
//        Log.e("WordList:new",String.valueOf(old_num));
//        Log.e("WordList:new",String.valueOf(all_num));

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
                if (!W.isEasy())
                {
                    W.setEasy();
                    final Data data=(Data)getApplication();
                    data.updateRecord(W.getWord());
                    data.deleteFromWordList(W);
                    Toast.makeText(Review_Chinese_Page_Activity.this,"Easy!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    W.cancelEasy();
                    final Data data=(Data)getApplication();
                    data.updateRecord(W.getWord());
                    data.cancelDelete(W);
                    Toast.makeText(Review_Chinese_Page_Activity.this,"Difficult?",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the last one then skip to end
                if (isEnd)
                {
                    skipPage(5);
                    return;
                }
                final Data data=(Data)getApplication();
                int mode=data.getMode();
                if (mode==2)
                {
                    skipPage(7);
                    return;
                }
                else
                {
                    skipPage(2);
                    return;
                }
            }
        });
        //to be written
        btnAdd2Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(4);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(6);
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(3);
            }
        });
    }

    private void setPb()
    {
        //to be written
        pb.setMax(all_num);
        pb.setProgress(all_num-new_num-old_num);
    }

    //page=1 for back;page=2 for english;page=3 for note modify;page=4 for book;page=5 for end;page=6 for note create
    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(Review_Chinese_Page_Activity.this, Start_Page_Activity.class);
                break;
            case 2:
                Log.e("RCA:Continue","end2");
                intent=new Intent(Review_Chinese_Page_Activity.this, Review_English_Page_Activity.class);
                break;
            case 3:
                intent=new Intent(Review_Chinese_Page_Activity.this, Note_Change_Activity.class);
                intent.putExtra("word", new Gson().toJson(W));
                intent.putExtra("flag",0);
                break;
                //to be add
            case 4:
                intent=new Intent(Review_Chinese_Page_Activity.this, Book_Select_Activity.class);
                intent.putExtra("word",new Gson().toJson(W));
                break;
            case 5:
                Log.e("RCA:End","end5");
                intent=new Intent(Review_Chinese_Page_Activity.this, Review_End_Activity.class);
                break;
            case 6:
                intent=new Intent(Review_Chinese_Page_Activity.this, Note_Create_Activity.class);
                intent.putExtra("word", new Gson().toJson(W));
//                intent.putExtra("flag",0);
                break;
            case 7:
                intent=new Intent(Review_Chinese_Page_Activity.this, Spell_English_Activity.class);
                break;
            default:
                intent=new Intent(Review_Chinese_Page_Activity.this, Start_Page_Activity.class);
        }
        //transfer data
        startActivity(intent);
        finish();
    }

}

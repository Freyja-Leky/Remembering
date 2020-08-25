package word_part;

import Review.Review_Chinese_Page_Activity;
import Review.Review_End_Activity;
import Review.Review_English_Page_Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import book_part.Book_Show_Activity;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import logic_class.word_class.Word;
import logic_class.word_class.Word2Review;
import menu_page.Start_Page_Activity;
import note_part.Note_Change_Activity;
import note_part.Note_Create_Activity;

public class Word_Show_Activity extends Activity {
    private Button btnBack;
    private Button btnModifyWord;

    private Button btnCreate;

    private TextView word;
    private TextView chinese;
    private TextView note;
    private TextView hint;

    private Word W;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_show);
        //Layout
        getLayout();
        //Data
        getDataFromIntent();
        //Function
        setView();
        setButton();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        btnModifyWord=(Button)findViewById(R.id.ModifyWord);
        btnCreate=(Button)findViewById(R.id.Create);

        word=(TextView)findViewById(R.id.Word);
        chinese=(TextView)findViewById(R.id.ChineseMeaning);
        note=(TextView)findViewById(R.id.NoteHint);
        hint=(TextView)findViewById(R.id.SystemHint);
    }

    private void getDataFromIntent()
    {
        String WJson=getIntent().getStringExtra("word");
        W=new Gson().fromJson(WJson, Word.class);
    }

    private void setView()
    {
        word.setText(W.getEnglish());
        chinese.setText(W.getChinese());
        if (W.getHint().equals(""))
        {
            hint.setText("点击修改添加例句");
            hint.setTextColor(getResources().getColor(R.color.LightSlateGray));
        }
        else
            hint.setText(W.getHint());
        if (W.getNote().equals(""))
        {
            note.setText("点击修改添加笔记");
            note.setTextColor(getResources().getColor(R.color.LightSlateGray));
        }
        else
            note.setText(W.getNote());
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(1);
            }
        });

        btnModifyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(2);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(4);
            }
        });
    }

    //1 for back;2 for Modify;3 for note modify;4 for note create;
    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                finish();
                break;
            case 2:
                intent=new Intent(Word_Show_Activity.this, Word_Change_Activity.class);
                intent.putExtra("word", new Gson().toJson(W));
                startActivity(intent);
                break;
//            case 3:
//                intent=new Intent(Word_Show_Activity.this, Note_Change_Activity.class);
//                intent.putExtra("word", new Gson().toJson(W));
//                intent.putExtra("flag",2);
//                break;
            case 4:
                intent=new Intent(Word_Show_Activity.this, Note_Create_Activity.class);
                intent.putExtra("word", new Gson().toJson(W));
                intent.putExtra("flag",2);
                startActivity(intent);
                break;
            default:
                intent=new Intent(Word_Show_Activity.this, Word_Show_Activity.class);
        }
        finish();
    }

}

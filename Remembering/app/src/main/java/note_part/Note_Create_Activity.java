package note_part;

import Review.Review_Chinese_Page_Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import log_and_register.Log_Up_Activity;
import logic_class.word_class.Word;
import logic_class.word_class.Word2Review;
import word_part.Word_Show_Activity;

public class Note_Create_Activity extends Activity {
    private Button btnBack;
    private Button btnSave;

    private TextView word;
    private EditText note;

    private Word W;
    private Word2Review W2R;

//    private int flag;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_change);
        //Layout
        getLayout();
        //Data
        getDataFromIntent();
        setView();
        setButton();

    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        btnSave=(Button)findViewById(R.id.save);

        word=(TextView)findViewById(R.id.Word);
        note=(EditText)findViewById(R.id.Note_to_Change);
    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
//        flag=intent.getIntExtra("flag",0);
        String WJson=getIntent().getStringExtra("word");
//        if (flag==0)
////        {
            W2R=new Gson().fromJson(WJson, Word2Review.class);
            W=W2R.getWord();
//        }
//        else
//        {
//            W=new Gson().fromJson(WJson, Word.class);
//        }
    }

    private void setView()
    {
            word.setText(W.getEnglish());
            note.setHint("请输入笔记");
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteModified=note.getText().toString();
                if (noteModified.equals(""))
                {
                    Toast.makeText(Note_Create_Activity.this,"创建笔记不可为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                W.setNote(noteModified);
                final Data data=(Data)getApplication();
//                data.updateRecord(W);
                if (!data.add2Note(W))
                {
                    Toast.makeText(Note_Create_Activity.this,"该单词已添加过笔记，可从笔记列表中进行修改！",Toast.LENGTH_SHORT).show();
                }
                skipPage();
            }
        });
    }

    private void skipPage()
    {
        Intent intent;
//        if (flag==0)
//        {
            intent=new Intent(Note_Create_Activity.this, Review_Chinese_Page_Activity.class);
            W2R.setWordNote(W.getNote());
            intent.putExtra("word",new Gson().toJson(W2R));
//        }
//        else
//        {
//            intent=new Intent(Note_Create_Activity.this, Word_Show_Activity.class);
//            intent.putExtra("word",new Gson().toJson(W));
//        }
        startActivity(intent);
        finish();
    }


}

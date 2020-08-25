package note_part;

import Review.Review_Chinese_Page_Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import logic_class.word_class.Word;
import logic_class.word_class.Word2Review;
import word_part.Word_Show_Activity;

public class Note_Change_Activity extends Activity {
    private Button btnBack;
    private Button btnSave;

    private TextView word;
    private EditText note;

    private Word W;
    private Word2Review W2R;

    private int flag;

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

    //flag=0 for reviewing
    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);
        if (flag==0)
        {
            String WJson=getIntent().getStringExtra("word");
            W2R=new Gson().fromJson(WJson, Word2Review.class);
            W=W2R.getWord();
        }
        else {
            String WJson = getIntent().getStringExtra("word");
            W = new Gson().fromJson(WJson, Word.class);
        }
    }

    private void setView()
    {
        word.setText(W.getEnglish());
        note.setText(W.getNote());
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
                Log.e("Note Change",noteModified);
                W.setNote(noteModified);
                final Data data=(Data)getApplication();
                if (noteModified.equals(""))
                    data.deletefromNote(W);
                else
                    data.updateNote(W);
                Log.e("Note Change",W.getNote());
                skipPage();
            }
        });
    }


    private void skipPage()
    {
        Intent intent;
        if (flag==0)
        {
            intent=new Intent(Note_Change_Activity.this, Review_Chinese_Page_Activity.class);
            W2R.setWordNote(W.getNote());
            intent.putExtra("word",new Gson().toJson(W2R));
        }
        else if (flag==1)
        {
            intent=new Intent(Note_Change_Activity.this, Note_Show_Activity.class);
            intent.putExtra("word",new Gson().toJson(W));
        }
        else
        {
            intent=new Intent(Note_Change_Activity.this, Word_Show_Activity.class);
            intent.putExtra("word",new Gson().toJson(W));
        }
        startActivity(intent);
        finish();
    }
}

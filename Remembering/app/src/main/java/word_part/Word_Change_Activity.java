package word_part;

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
import logic_class.word_class.Word;


public class Word_Change_Activity extends Activity {
    private Button btnBack;
    private Button btnSave;
    private TextView English;
    private EditText Chinese;
    private EditText Note;
    private EditText Hint;

    private Word W;
    private boolean withNote;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_change);

        getLayout();
        getDataFromIntent();
        setView();
        setButton();
    }

    private void getDataFromIntent() {

        Intent intent=getIntent();
        String WJson=getIntent().getStringExtra("word");
        W=new Gson().fromJson(WJson, Word.class);
        if (W.getNote().equals(""))
            withNote=false;
        else
            withNote=true;
    }

    private void getLayout()
    {
        btnBack = (Button) findViewById(R.id.Return);
        btnSave = (Button) findViewById(R.id.save);
        English = (TextView) findViewById(R.id.English);
        Chinese = (EditText) findViewById(R.id.chinese_to_add);
        Hint = (EditText) findViewById(R.id.example_to_add);
        Note = (EditText) findViewById(R.id.note_to_add);
    }

    private void setView()
    {
        English.setText(W.getEnglish());
        Chinese.setText(W.getChinese());
        if (W.getHint().equals(""))
        {
            Hint.setHint("请添加例句（例句将作为记忆时的提示出现）");
        }
        else {
            Hint.setText(W.getHint());
        }
        if (W.getNote().equals(""))
        {
            Note.setHint("请添加笔记");
        }
        else {
            Note.setText(W.getNote());
        }
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
                String chinese=Chinese.getText().toString();
                String note=Note.getText().toString();
                String hint=Hint.getText().toString();
                if (chinese.equals(""))
                {
                    Toast.makeText(Word_Change_Activity.this,"中文释义不可为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                W.setChinese(chinese);
                W.setNote(note);
                W.setHint(hint);
                final Data data = (Data) getApplication();
                if ((withNote)&&note.equals(""))
                    data.deletefromNote(W);
                else if ((!withNote)&&(!note.equals("")))
                    data.add2Note(W);
                else if ((withNote)&&(!note.equals("")))
                    data.updateNote(W);
//                if (note.equals("")&&withNote)
//                {
//                    data.deletefromNote(W);
//                }
//                else if ((!note.equals(""))&&(!withNote))
//                {
//                    data.add2Note(W);
//                }
                data.updateWordinBook(W);
                skipPage();
            }
        });
    }

    private void skipPage()
    {
        Intent intent=new Intent(Word_Change_Activity.this, Word_Show_Activity.class);
        intent.putExtra("word",new Gson().toJson(W));
        startActivity(intent);
        finish();
    }

}

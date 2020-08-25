package note_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import logic_class.word_class.Word;
import user_setting.Note_List_Activity;

public class Note_Show_Activity extends Activity {
    private Button btnBack;
    private Button btnModify;

    private TextView word;
    private TextView note;

    private Word W;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_show);
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
        btnModify=(Button)findViewById(R.id.Add);
        word=(TextView)findViewById(R.id.Word);
        note=(TextView)findViewById(R.id.Note);
    }

    private void getDataFromIntent()
    {
        String WJson=getIntent().getStringExtra("word");
        W=new Gson().fromJson(WJson, Word.class);
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
                skipPage(1);
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
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
                intent=new Intent(Note_Show_Activity.this, Note_List_Activity.class);
                break;
            case 2:
                intent=new Intent(Note_Show_Activity.this, Note_Change_Activity.class);
                intent.putExtra("word", new Gson().toJson(W));
                intent.putExtra("flag",1);
                break;
            default:
                intent=new Intent(Note_Show_Activity.this, Note_Show_Activity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

}

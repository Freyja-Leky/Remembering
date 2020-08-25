package word_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import book_part.Book_Show_Activity;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;

public class Word_Create_Activity extends Activity {
    private Button btnBack;
    private Button btnSave;
    private EditText English;
    private EditText Chinese;
    private EditText Note;
    private EditText Hint;

    private VocabularyBook book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_create);

        getLayout();
        getDataFromIntent();
        setButton();
    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        String BJson=intent.getStringExtra("book");
        book=new Gson().fromJson(BJson,VocabularyBook.class);
    }

    private void getLayout() {
        btnBack = (Button) findViewById(R.id.Return);
        btnSave = (Button) findViewById(R.id.save);
        English = (EditText) findViewById(R.id.English);
        Chinese = (EditText) findViewById(R.id.chinese_to_add);
        Hint = (EditText) findViewById(R.id.example_to_add);
        Note = (EditText) findViewById(R.id.note_to_add);
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
                String english=English.getText().toString();
                String chinese=Chinese.getText().toString();
                String note=Note.getText().toString();
                String hint=Hint.getText().toString();
                if (english.equals("")||chinese.equals(""))
                {
                    Toast.makeText(Word_Create_Activity.this,"英文与中文释义不可为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                Word W=new Word(english,chinese,book.getName());
                W.setNote(note);
                W.setHint(hint);
                if (!book.add2Book(W))
                {
                    Toast.makeText(Word_Create_Activity.this,"该本书中已存在该单词！",Toast.LENGTH_SHORT).show();
                    return;
                }
                final Data data=(Data)getApplication();
                if (!note.equals(""))
                {

                    if (!data.add2Note(W))
                    {
                        Toast.makeText(Word_Create_Activity.this,"该单词已添加过笔记，可从笔记列表中进行修改！",Toast.LENGTH_SHORT).show();
                    }
                }
                data.createWordinBook(W);
                skipPage();
            }
        });
    }

    private void skipPage()
    {
        Intent intent=new Intent(Word_Create_Activity.this, Book_Show_Activity.class);
        intent.putExtra("book",new Gson().toJson(book));
        startActivity(intent);
        finish();
    }
}

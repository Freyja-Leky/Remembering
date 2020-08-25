package book_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import db.DB_UploadBook;
import global_data.Data;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;
import user_setting.Book_List_Activity;
import word_part.Word_Adapter;
import word_part.Word_Create_Activity;

import java.util.Iterator;
import java.util.List;

public class Book_Show_Activity extends Activity {
    private Button btnBack;
    private Button btnAdd;
    private Button btnChange;
    private Button BtnUpload;
    private TextView bookName;
    private RecyclerView recyclerView;

    private VocabularyBook book;
    private List<Word> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_show);

        getLayout();
        getDataFromIntent();
        setButton();
        setView();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        btnChange=(Button)findViewById(R.id.Change);
        btnAdd=(Button)findViewById(R.id.Add_New_Word);
        BtnUpload=(Button)findViewById(R.id.Upload);
        bookName=(TextView)findViewById(R.id.Name);
        recyclerView=(RecyclerView)findViewById(R.id.rc);
    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        String BJson=intent.getStringExtra("book");
        book=new Gson().fromJson(BJson,VocabularyBook.class);
        wordList=compare2Notes(book.getWordList());
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(1);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(2);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(3);
            }
        });

        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_UploadBook db_uploadBook=new DB_UploadBook(book);
                db_uploadBook.start();
                try {
                    db_uploadBook.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Book_Show_Activity.this,"上传成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setView()
    {
        bookName.setText(book.getName());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Word_Adapter word_adapter=new Word_Adapter(wordList);
        recyclerView.setAdapter(word_adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    //1 for back;2 for change;3 for add
    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(Book_Show_Activity.this, Book_List_Activity.class);
                break;
            case 2:
                intent=new Intent(Book_Show_Activity.this, Book_Change_Activity.class);
                intent.putExtra("book",new Gson().toJson(book));
                break;
            case 3:
                intent=new Intent(Book_Show_Activity.this, Word_Create_Activity.class);
                intent.putExtra("book",new Gson().toJson(book));
                break;
            default:
                intent=new Intent(Book_Show_Activity.this, Book_List_Activity.class);
        }
        startActivity(intent);
        finish();
    }

    private List<Word> compare2Notes(List<Word> words)
    {
        final Data data=(Data)getApplication();
        List<Word> notes=data.getNoteData();
        Iterator<Word> notesIt=notes.iterator();
        while (notesIt.hasNext())
        {
            Word note=notesIt.next();
            for (int i=0;i<words.size();i++)
            {
                if (note.getEnglish().equals(words.get(i).getEnglish()))
                {
                    words.get(i).setNote(note.getNote());
                    break;
                }
            }
        }
        return words;
    }
}

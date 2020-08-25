package book_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import logic_class.VocabularyBook;
import user_setting.Book_List_Activity;

public class Book_Create_Activity extends Activity {

    private Button btnBack;
    private Button btnSave;
    private TextView Name;
    private EditText bookName;
    private EditText introduction;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_change);

        getLayout();
        setButton();
        setView();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        btnSave=(Button)findViewById(R.id.save);
        Name=(TextView)findViewById(R.id.Name) ;
        bookName=(EditText)findViewById(R.id.Name_to_Add);
        introduction=(EditText)findViewById(R.id.introduction_to_add);
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
                String name=bookName.getText().toString();
                String intro=introduction.getText().toString();
                if (name.equals(""))
                {
                    Toast.makeText(Book_Create_Activity.this,"词书名不可为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                VocabularyBook book=new VocabularyBook(name,intro);
                final Data data=(Data)getApplication();
                data.addBook2DataBase(book);
                skipPage();
            }
        });
    }

    private void setView()
    {
        Name.setVisibility(View.INVISIBLE);
        bookName.setVisibility(View.VISIBLE);
    }

    private void skipPage()
    {
        Intent intent=new Intent(Book_Create_Activity.this, Book_List_Activity.class);
        startActivity(intent);
        finish();
    }
}

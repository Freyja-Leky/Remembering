package book_part;

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
import logic_class.VocabularyBook;
import org.w3c.dom.Text;

public class Book_Change_Activity extends Activity {
    private Button btnBack;
    private Button btnSave;
    private TextView Name;
    private EditText bookName;
    private EditText introduction;
    private VocabularyBook book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_change);

        getLayout();
        getDataFromIntent();
        setButton();
        setView();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        btnSave=(Button)findViewById(R.id.save);
        Name=(TextView)findViewById(R.id.Name);
        bookName=(EditText)findViewById(R.id.Name_to_Add);
        introduction=(EditText)findViewById(R.id.introduction_to_add);
    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        String BJson=intent.getStringExtra("book");
        book=new Gson().fromJson(BJson,VocabularyBook.class);
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
//                if (name.equals(""))
//                {
//                    Toast.makeText(Book_Change_Activity.this,"词书名不可为空！",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                book.setName(name);
                book.setIntroduction(intro);
                final Data data=(Data)getApplication();
                data.updateBook(book);
                //save to database
                skipPage();
            }
        });
    }

    private void setView()
    {
        Name.setText(book.getName());
        introduction.setText(book.getIntroduction());
    }

    private void skipPage()
    {
        Intent intent=new Intent(Book_Change_Activity.this,Book_Show_Activity.class);
        intent.putExtra("book",new Gson().toJson(book));
        startActivity(intent);
        finish();
    }
}

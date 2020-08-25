package user_setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import book_part.Book_Adapter;
import book_part.Book_Create_Activity;
import com.example.freyja.testthree.R;
import global_data.Data;
import logic_class.VocabularyBook;
import menu_page.User_Info_Page_Activity;

import java.util.List;

public class Book_List_Activity  extends Activity {

    private Button btnBack;
    private Button btnAdd;
    private RecyclerView recyclerView;

    private List<VocabularyBook> bookList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        View view=findViewById(R.id.book_list);
        view.getBackground().setAlpha(120);

        getLayout();
        getDataFromGlobal();
        setButton();
        setView();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        btnAdd=(Button)findViewById(R.id.Add);
        recyclerView=(RecyclerView)findViewById(R.id.rc);
    }

    private void getDataFromGlobal()
    {
        final Data data=(Data)getApplication();
        bookList=data.getBookList();
    }

    private void setView()
    {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Book_Adapter book_adapter=new Book_Adapter(bookList);
        recyclerView.setAdapter(book_adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(1);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
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
                intent=new Intent(Book_List_Activity.this, User_Info_Page_Activity.class);
                break;
            case 2:
                intent=new Intent(Book_List_Activity.this, Book_Create_Activity.class);
                break;
            default:
                intent=new Intent(Book_List_Activity.this,Book_List_Activity.class);
        }
        startActivity(intent);
        finish();
    }
}

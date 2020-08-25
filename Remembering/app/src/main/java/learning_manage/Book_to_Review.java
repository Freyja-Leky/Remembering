package learning_manage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import book_part.Book_Adapter;
import com.example.freyja.testthree.R;
import global_data.Data;
import logic_class.VocabularyBook;


import java.util.List;

public class Book_to_Review extends Activity {
    private Button btnBack;
    private RecyclerView recyclerView;
    private Button btnAdd;

    private List<VocabularyBook> bookList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_to_review);

        getLayout();
        getDataFromGlobal();
        setButton();
        setView();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        recyclerView=(RecyclerView)findViewById(R.id.rc);
        btnAdd=(Button)findViewById(R.id.find_new_book);
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
        Book_Choose_Adapter book_adapter=new Book_Choose_Adapter(bookList);
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

    private void skipPage(int page) {
        if (page==1)
            finish();
        else
        {
            Intent intent=new Intent(Book_to_Review.this,Book_Review_Share_Activity.class);
            startActivity(intent);
        }
    }

}

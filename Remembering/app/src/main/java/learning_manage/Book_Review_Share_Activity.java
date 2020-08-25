package learning_manage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import db.DB_Search_Book;
import global_data.Data;
import logic_class.VocabularyBook;

import java.util.ArrayList;
import java.util.List;

public class Book_Review_Share_Activity extends Activity {

    private Button btnBack;
    private RecyclerView recyclerView;
    private Button btnSearch;
    private EditText search_title;

    Book_Choose_Adapter book_adapter;
    private String email;
    private List<VocabularyBook> bookList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_review_share);

        getLayout();
        getDataFromGlobal();
        getDataFromDB();
        setView();
        setButton();
    }

    private void getDataFromGlobal()
    {
        Data data=(Data)getApplication();
        email=data.getEmail();
    }

    private void getDataFromDB()
    {
        DB_Search_Book db_search_book=new DB_Search_Book(email,true);
        db_search_book.start();
        try {
            db_search_book.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bookList=db_search_book.getBookList();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        search_title=(EditText) findViewById(R.id.search_title);
        btnSearch=(Button)findViewById(R.id.find);
        recyclerView=(RecyclerView)findViewById(R.id.rc);
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<VocabularyBook> oldTemp=new ArrayList<>(bookList);
                String search_str=search_title.getText().toString();
                DB_Search_Book db_search_book=new DB_Search_Book(search_str);
                db_search_book.start();
                try {
                    db_search_book.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bookList.clear();
                bookList.addAll(db_search_book.getBookList());
                Log.e("BRSA",String.valueOf(bookList.size()));
                DiffUtil.Callback callback=new DiffUtil.Callback() {
                    @Override
                    public int getOldListSize() {
                        return oldTemp.size();
                    }

                    @Override
                    public int getNewListSize() {
                        return bookList.size();
                    }

                    @Override
                    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return oldTemp.get(oldItemPosition).equals(bookList.get(newItemPosition));
                    }

                    @Override
                    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        return oldTemp.get(oldItemPosition).equals(bookList.get(newItemPosition));
                    }
                };
                DiffUtil.DiffResult diffResult=DiffUtil.calculateDiff(callback,true);
                diffResult.dispatchUpdatesTo(book_adapter);
//                book_adapter.notifyDataSetChanged();
            }
        });
    }

    private void setView()
    {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        book_adapter=new Book_Choose_Adapter(bookList,email);
        recyclerView.setAdapter(book_adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void skipPage()
    {
        finish();
    }
}

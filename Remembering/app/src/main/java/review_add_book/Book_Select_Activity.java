package review_add_book;

import Review.Review_Chinese_Page_Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;
import logic_class.word_class.Word2Review;

import java.util.List;

public class Book_Select_Activity extends Activity {
    private Button btnBack;
    private RecyclerView recyclerView;

    private List<VocabularyBook> bookList;
    private Word word;
    private Word2Review word2Review;
    //to be replace

    private String email;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_select);

        getLayout();
        getDataFromGlobal();
        getDataFromIntent();
        setButton();
        setView();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        recyclerView=(RecyclerView)findViewById(R.id.rc);
    }

    private void getDataFromGlobal()
    {
        final Data data=(Data)getApplication();
        bookList=data.getBookList();
        email=data.getEmail();
    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        String WJson=intent.getStringExtra("word");
        word2Review=new Gson().fromJson(WJson,Word2Review.class);
        word=word2Review.getWord();
    }

    private void setView()
    {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Select_Adapter select_adapter=new Select_Adapter(bookList,word,email);
        recyclerView.setAdapter(select_adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Book_Select_Activity.this, Review_Chinese_Page_Activity.class);
                intent.putExtra("word",new Gson().toJson(word2Review));
                Log.e("Book_Select:",new Gson().toJson(word2Review));
                startActivity(intent);
                finish();
            }
        });
    }
}

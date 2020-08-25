package user_setting;

import Review.Review_Chinese_Page_Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.example.freyja.testthree.R;
import global_data.Data;
import logic_class.word_class.Word;
import menu_page.User_Info_Page_Activity;
import note_part.Note_Adapter;
import note_part.Note_Create_Activity;

import java.util.ArrayList;
import java.util.List;

public class Note_List_Activity extends Activity {
    private Button btnBack;
    private RecyclerView recyclerView;

    private List<Word>note_data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list);
        View view=findViewById(R.id.note_list);
        view.getBackground().setAlpha(120);
        getLayout();
        getDataFromGlobal();
        setView();
        setButton();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        recyclerView=(RecyclerView)findViewById(R.id.Note_View);
    }

    private void getDataFromGlobal()
    {
        final Data data=(Data)getApplication();
        note_data=data.getNoteData();

//        note_data=new ArrayList<Word>();
//        Word w1=new Word("aaa","bbb","ccc","ddd");
//        note_data.add(w1);
//        Word w2=new Word("eee","bbb","ccc","fff");
//        note_data.add(w2);
    }

    private void setView()
    {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Note_Adapter adapter=new Note_Adapter(note_data);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(Note_List_Activity.this, User_Info_Page_Activity.class);
                startActivity(intent);
            }
        });
    }

}

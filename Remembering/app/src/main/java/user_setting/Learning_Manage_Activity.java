package user_setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import global_data.Data;
import info_setting_part.Change_Info_Activity;
import learning_manage.Book_to_Review;
import logic_class.VocabularyBook;
import menu_page.User_Info_Page_Activity;

public class Learning_Manage_Activity extends Activity {
    private Button btnBack;
    private RadioGroup learningMode;
    private RadioGroup learningAmount;

    private Button btnBook;

    private TextView bookName;
    private ImageView bookCover;

    private int mode;
    private int amount;
    private VocabularyBook reviewingBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learnning_manage);

        getLayout();
        getDataFromGlobal();
        getDataFromIntent();
        setButton();
        setView();
    }

    private void getLayout(){
        btnBack=(Button)findViewById(R.id.Return);
        btnBook=(Button)findViewById(R.id.book_setting) ;
        learningMode=(RadioGroup)findViewById(R.id.Mode_Setting);
        learningAmount=(RadioGroup)findViewById(R.id.Amount_Setting);
        bookName=(TextView)findViewById(R.id.Book_Name);
        bookCover=(ImageView) findViewById(R.id.Book_Cover);
    }

    private void getDataFromGlobal(){
        final Data data=(Data)getApplication();
        mode=data.getMode();
        amount=data.getAmount();
        reviewingBook=data.getLearningBook();
    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        int flag=intent.getIntExtra("flag",0);
        if (flag==1)
        {
            String BSon=intent.getStringExtra("book");
            VocabularyBook book=new Gson().fromJson(BSon,VocabularyBook.class);
            reviewingBook=book;
            final Data data=(Data)getApplication();
            data.setVocabularyBook(book);
        }
    }

    private void setButton(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(1);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage(2);
            }
        });
    }

    private void setView(){
        if (mode==1)
        {
            learningMode.check(R.id.remember);
        }
        else
        {
            learningMode.check(R.id.spell);
        }
        switch (amount){
            case 1:
                learningAmount.check(R.id.A50);
                break;
            case 2:
                learningAmount.check(R.id.A100);
                break;
            case 3:
                learningAmount.check(R.id.A150);
                break;
            case 4:
                learningAmount.check(R.id.A200);
                break;

        }

        bookName.setText(reviewingBook.getName());
        bookCover.setImageResource(reviewingBook.getCover());
    }

    private void getModifiedData(){
        switch (learningMode.getCheckedRadioButtonId())
        {
            case R.id.remember:
                mode=1;
                break;
            case R.id.spell:
                mode=2;
                break;
        }
        switch (learningAmount.getCheckedRadioButtonId())
        {
            case R.id.A50:
                amount=1;
                break;
            case R.id.A100:
                amount=2;
                break;
            case R.id.A150:
                amount=3;
                break;
            case R.id.A200:
                amount=4;
                break;
        }
    }

    //page=1 for back;page=2 for book select
    private void skipPage(int page)
    {
        Intent intent;
        switch (page)
        {
            case 1:
                intent=new Intent(Learning_Manage_Activity.this, User_Info_Page_Activity.class);
                getModifiedData();
                final Data data=(Data)getApplication();
                data.setMode(mode);
                data.setAmount(amount);
                break;
            case 2:
                intent=new Intent(Learning_Manage_Activity.this, Book_to_Review.class);
                break;
            default:
                intent=new Intent(Learning_Manage_Activity.this,Learning_Manage_Activity.class);

        }
        startActivity(intent);
    }

}

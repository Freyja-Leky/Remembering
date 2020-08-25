package Review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import global_data.Data;
import menu_page.Start_Page_Activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Review_End_Activity extends Activity {
    private Button btnBack;
    private TextView days;
    private TextView show_amount;

    private int day_num;
    private int amount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_end);
        View view=findViewById(R.id.review_end);
        view.getBackground().setAlpha(100);

        //Layout
        getLayout();
        //Data
        try {
            getDataFromGlobal();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Function
        setView();
        setButton();
    }

    private void getLayout()
    {
        btnBack=(Button)findViewById(R.id.Return);
        days=(TextView)findViewById(R.id.CountingDay);
        show_amount=(TextView)findViewById(R.id.amount);
    }

    private void getDataFromGlobal() throws ParseException {
        final Data data=(Data)getApplication();
        amount=data.getListAmount();
        day_num=data.getDays();
        Date lastDate=data.getLastDate();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String nowDate=simpleDateFormat.format(new Date());
        String last=simpleDateFormat.format(lastDate);
        Date dDate=simpleDateFormat.parse(nowDate);
        if (!nowDate.equals(last))
        {
            day_num++;
            data.updateDate(day_num,dDate);
        }
    }

    private void setView()
    {
        days.setText(String.valueOf(day_num));
        show_amount.setText(String.valueOf(amount));
    }

    private void setButton()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipPage();
            }
        });
    }

    private void skipPage()
    {
        Intent intent=new Intent(Review_End_Activity.this, Start_Page_Activity.class);
        startActivity(intent);
        finish();
    }
}

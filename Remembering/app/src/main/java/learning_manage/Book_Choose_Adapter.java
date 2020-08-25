package learning_manage;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import book_part.Book_Adapter;
import book_part.Book_Show_Activity;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import db.DB_Search_Book;
import global_data.Data;
import logic_class.VocabularyBook;
import user_setting.Learning_Manage_Activity;

import java.util.List;

public class Book_Choose_Adapter extends RecyclerView.Adapter<Book_Choose_Adapter.VH> {

    private List<VocabularyBook> book_data;
    private String email;
    private Boolean flag;

    //true for private book;false for share book
    public Book_Choose_Adapter(List<VocabularyBook> vocabularyBooks)
    {
        this.book_data=vocabularyBooks;
        this.flag=true;
    }

    public Book_Choose_Adapter(List<VocabularyBook> vocabularyBooks,String email)
    {
        this.book_data=vocabularyBooks;
        this.email=email;
        Log.e("BCA:",email);
        this.flag=false;
    }

    class VH extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView intro;
        public ImageView cover;

        public VH(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.book_name);
            intro = (TextView) v.findViewById(R.id.book_intro);
            cover = (ImageView) v.findViewById(R.id.cover);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.name.setText(book_data.get(position).getName());
        holder.intro.setText(book_data.get(position).getIntroduction());
        holder.cover.setImageResource(book_data.get(position).getCover());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if not then create a function with context
                if (flag)
                {
                    Intent intent=new Intent(v.getContext(), Learning_Manage_Activity.class);
                    intent.putExtra("book",new Gson().toJson(book_data.get(position)));
                    intent.putExtra("flag",1);
                    v.getContext().startActivity(intent);
                }
                else
                {
                    DB_Search_Book db_search_book=new DB_Search_Book(book_data.get(position),email);
                    db_search_book.start();
                    try {
                        db_search_book.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(v.getContext(), Learning_Manage_Activity.class);
                    intent.putExtra("book",new Gson().toJson(db_search_book.getVocabularyBook()));
                    intent.putExtra("flag",1);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
        VH myViewHolder = new VH(v);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return book_data.size();
    }
}

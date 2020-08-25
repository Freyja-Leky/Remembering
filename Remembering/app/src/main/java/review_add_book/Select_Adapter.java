package review_add_book;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.freyja.testthree.R;
import db.DB_AddBook;
import logic_class.VocabularyBook;
import logic_class.word_class.Word;

import java.util.List;

public class Select_Adapter extends RecyclerView.Adapter<Select_Adapter.VH> {

    private List<VocabularyBook> book_data;
    private Word word;
    //to be replace
    private String email;

    public Select_Adapter(List<VocabularyBook> vocabularyBooks, Word word,String email)
    {
        this.book_data=vocabularyBooks;
        this.word=word;
        this.email=email;
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
                //to be replaced
                DB_AddBook db_addBook=new DB_AddBook(word,book_data.get(position),email);
                db_addBook.start();
                if (book_data.get(position).add2Book(word))
                    Toast.makeText(v.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
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

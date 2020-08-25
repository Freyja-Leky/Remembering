package book_part;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import logic_class.VocabularyBook;

import java.util.List;

public class Book_Adapter extends RecyclerView.Adapter<Book_Adapter.VH>  {

    private List<VocabularyBook> book_data;

    public Book_Adapter(List<VocabularyBook> vocabularyBooks)
    {
        this.book_data=vocabularyBooks;
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
                Intent intent=new Intent(v.getContext(), Book_Show_Activity.class);
                intent.putExtra("book", new Gson().toJson(book_data.get(position)));
                v.getContext().startActivity(intent);
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

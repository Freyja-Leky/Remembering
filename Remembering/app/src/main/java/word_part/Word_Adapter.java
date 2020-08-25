package word_part;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.freyja.testthree.R;
import com.google.gson.Gson;
import logic_class.word_class.Word;


import java.util.List;

public class Word_Adapter extends RecyclerView.Adapter<Word_Adapter.VH> {
    private List<Word> word_data;

    public Word_Adapter(List<Word> word_data)
    {
        this.word_data=word_data;
    }

    class VH extends RecyclerView.ViewHolder{
        public TextView english;
        public TextView chinese;

        public VH(View v) {
            super(v);
            english = (TextView) v.findViewById(R.id.English);
            chinese = (TextView) v.findViewById(R.id.Chinese);
        }
    }


    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.english.setText(word_data.get(position).getEnglish());
        holder.chinese.setText(word_data.get(position).getChinese());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if not then create a function with context
                Intent intent=new Intent(v.getContext(), Word_Show_Activity.class);
                intent.putExtra("word", new Gson().toJson(word_data.get(position)));
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        VH myViewHolder = new VH(v);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return word_data.size();
    }
}

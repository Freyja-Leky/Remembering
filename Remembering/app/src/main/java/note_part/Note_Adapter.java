package note_part;

import android.content.Context;
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

public class Note_Adapter extends RecyclerView.Adapter<Note_Adapter.VH> {

    private List<Word> note_data;

    public Note_Adapter(List<Word> data) {
        this.note_data = data;
    }

    //inner class to find item view
     class VH extends RecyclerView.ViewHolder{
        public TextView word;
        public TextView note;

        public VH(View v) {
            super(v);
            word = (TextView) v.findViewById(R.id.word);
            note = (TextView) v.findViewById(R.id.note);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.word.setText(note_data.get(position).getEnglish());
        holder.note.setText(note_data.get(position).getNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if not then create a function with context
                Intent intent=new Intent(v.getContext(),Note_Show_Activity.class);
                intent.putExtra("word", new Gson().toJson(note_data.get(position)));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        VH myViewHolder = new VH(v);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return note_data.size();
    }

}

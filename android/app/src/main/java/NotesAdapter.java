import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.myapplication.R;

import java.util.List;

import org.w3c.dom.Text;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder>
{

    private List <NoteBuilder> notesList;

    public  class  MyViewHolder extends  RecyclerView.ViewHolder {

        public TextView title , content ;

        public MyViewHolder(View view){

            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);

        }
    }

    public NotesAdapter (List <NoteBuilder> notesList){
        this.notesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent , int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row , parent , false);
        return new MyViewHolder(itemView);
    }
    @Override
    public  void onBindViewHolder(MyViewHolder holder , int position){
        NoteBuilder note =  notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
    }
    @Override
    public int getItemCount(){
        return notesList.size();
    }
}


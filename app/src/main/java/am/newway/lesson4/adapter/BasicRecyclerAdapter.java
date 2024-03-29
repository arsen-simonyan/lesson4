package am.newway.lesson4.adapter;

import am.newway.lesson4.R;
import am.newway.lesson4.data.Tasks;
import am.newway.lesson4.enums.TaskType;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class BasicRecyclerAdapter extends RecyclerView.Adapter<BasicRecyclerAdapter.MyViewHolder>
{
    private List<Tasks> list;

    public BasicRecyclerAdapter( List<Tasks> list )
    {
        this.list = list;
    }

    public void addTasks( Tasks task )
    {
        list.add( task );
        notifyItemInserted( list.size() - 1 );
    }

    public void removeTasks( int position )
    {
        list.remove( position );
        notifyItemRemoved( position );
    }

    @NonNull @Override public MyViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup , int i )
    {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.item_layout , viewGroup , false );
        MyViewHolder holder = new MyViewHolder( view );
        holder.textName.setText( "1" );
        return holder;
    }

    @Override public void onBindViewHolder( @NonNull MyViewHolder myViewHolder , int i )
    {
        myViewHolder.bind( list.get( i ) );
    }

    @Override public int getItemCount()
    {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textName;
        TextView textDescription;
        ImageView imageView;
        ConstraintLayout rl;
        Context context;

        MyViewHolder( View viewItem )
        {
            super( viewItem );
            textName = viewItem.findViewById( R.id.textName );
            textDescription = viewItem.findViewById( R.id.textDescription );
            imageView = viewItem.findViewById( R.id.image );
            rl = viewItem.findViewById( R.id.root );
            context = viewItem.getContext();
        }

        void bind( Tasks task )
        {
            textName.setText( task.getStrName() );
            textDescription.setText( task.getStrDescription() );
            Uri uri = task.getUriImage();

            Glide.with(context).load(uri).into(imageView);
            //if(uri  != null)
             //   imageView.setImageURI( uri );
//            Glide.with( context )
//                    .load( task.getUriImage() )
//                    .into( imageView );

            switch ( TaskType.valueOf( task.getStrType() ) )
            {
                case IMP:
                    rl.setBackgroundColor( ContextCompat.getColor( context , R.color.myRed ) );
                    break;
                case ORD:
                    rl.setBackgroundColor( ContextCompat.getColor( context , R.color.myGreen ) );
                    break;
                case UNI:
                    rl.setBackgroundColor( ContextCompat.getColor( context , R.color.myBlue ) );
                    break;
            }
        }
    }
}

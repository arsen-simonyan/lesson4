package am.newway.lesson4.fragments;

import am.newway.lesson4.R;
import am.newway.lesson4.adapter.BasicRecyclerAdapter;
import am.newway.lesson4.data.RecyclerViewMargin;
import am.newway.lesson4.data.Settings;
import am.newway.lesson4.data.Tasks;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends BaseFragment
{
    private BasicRecyclerAdapter mAdapter;

    public FirstFragment()
    {
    }

    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState )
    {
        return inflater.inflate( R.layout.fragment_first , container , false );
    }

    @Override public void onViewCreated( @NonNull View view , @Nullable Bundle savedInstanceState )
    {
        super.onViewCreated( view , savedInstanceState );

        RecyclerView recyclerView = view.findViewById( R.id.recycler );
        RecyclerView.LayoutManager mManager = new LinearLayoutManager( getActivity() , RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager( mManager );

        mAdapter = new BasicRecyclerAdapter( new ArrayList<Tasks>() );
        recyclerView.setAdapter( mAdapter );

        RecyclerViewMargin decoration = new RecyclerViewMargin( (int) getResources().getDimension( R.dimen.recycler_item_margin ) );
        recyclerView.addItemDecoration( decoration );

        if( getActivity() != null)
        {
            List<Tasks> tasks = Settings.getInstance( getActivity() ).SQL().getTasks( "SELECT * FROM Tasks" );
            for (Tasks task : tasks)
            {
                mAdapter.addTasks( task );
            }
        }
    }

    @Override public void setTask( Tasks task )
    {
        mAdapter.addTasks( task );
    }
}

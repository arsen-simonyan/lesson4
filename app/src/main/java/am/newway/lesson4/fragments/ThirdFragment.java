package am.newway.lesson4.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import am.newway.lesson4.R;
import am.newway.lesson4.data.Tasks;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ThirdFragment extends BaseFragment
{
    public ThirdFragment()
    {
        // Required empty public constructor
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


        if( getActivity() != null)
        {

        }
    }

    @Override public void setTask( Tasks task )
    {
    }
}

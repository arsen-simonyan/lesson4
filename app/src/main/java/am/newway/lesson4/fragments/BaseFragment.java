package am.newway.lesson4.fragments;

import am.newway.lesson4.data.Tasks;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment
{
    public abstract void setTask( Tasks task );

}

package am.newway.lesson4.adapter;

import am.newway.lesson4.fragments.FirstFragment;
import am.newway.lesson4.fragments.SecondFragment;
import am.newway.lesson4.fragments.ThirdFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter
{

	public MyPagerAdapter( FragmentManager fm) {
		super(fm);
	}

	@NonNull @Override
	public Fragment getItem( int position)
	{
		switch ( position )
		{
			case 0:
				return new FirstFragment();
			case 1:
				return new SecondFragment();
			case 2:
				return new ThirdFragment();
			default:
				return new ThirdFragment();
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0){
			return "First";
		} else if (position == 1){
			return "Second";
		} else {
			return "Third";
		}
	}
}

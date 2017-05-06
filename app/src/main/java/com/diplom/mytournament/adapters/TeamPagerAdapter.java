package com.diplom.mytournament.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.diplom.mytournament.fragments.TestObjectFragment;

/**
 * Created by Kovrizhkin V.A. on 06.05.2017.
 */

public class TeamPagerAdapter extends FragmentStatePagerAdapter {

    public TeamPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new TestObjectFragment();
        Bundle args = new Bundle();
        args.putInt(TestObjectFragment.ARG_OBJECT, position+1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }


    @Override
    public int getCount() {
        return 3;
    }
}

package com.diplom.mytournament.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.diplom.mytournament.fragments.team_view_pager.TeamInfoFragment;

import java.util.List;

/**
 * Created by Kovrizhkin V.A. on 06.05.2017.
 */

public class TeamPagerAdapter extends FragmentStatePagerAdapter {

    private static List<Fragment> fragmentList;

    public TeamPagerAdapter(FragmentManager fm, int teamId) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new TeamInfoFragment();

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String s = "";
        switch (position) {
            case 0:
                s = "МАТЧИ";
                break;
            case 1:
                s =  "СОСТАВ";
            break;
            case 2: s = "ИНФОРМАЦИЯ";
                break;
        }
        return s;
    }


    @Override
    public int getCount() {
        return 3;
    }
}

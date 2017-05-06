package com.diplom.mytournament.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.diplom.mytournament.fragments.team_view_pager.TeamInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kovrizhkin V.A. on 06.05.2017.
 */

public class MyTournamentPagerAdapter extends FragmentStatePagerAdapter {

    private static List<Fragment> fragmentList;

    private static List<String> titles;

    private int tabCount;

    public MyTournamentPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentList.get(position);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        /*String s = "";
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
        return s;*/
        return titles.get(position);
    }


    @Override
    public int getCount() {
        return tabCount;
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titles.add(title);
    }
}

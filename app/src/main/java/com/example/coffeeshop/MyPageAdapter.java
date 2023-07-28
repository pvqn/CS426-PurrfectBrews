package com.example.coffeeshop;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private String[] tabTitles;

    public MyPageAdapter(@NonNull FragmentManager fragmentManager, List<Fragment>fragments, String[] tabTitles ) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.tabTitles = tabTitles;

    }





    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */

    @Override
    public int getCount() {
        return this.fragments.size();
    }


    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Return the title for the tab at the given position
        return tabTitles[position];
    }
}

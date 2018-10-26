package com.ycc.simplemvp.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用的viewpager 内置fragment
 * <p>
 * Created by issuser on 2017/11/29.
 */

public class FrgViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private FragmentManager fragmentManager;

    public FrgViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.fragmentManager = fm;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragments.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

}

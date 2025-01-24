package com.example.booksapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProfilePagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"Okuduklarım", "Şu anda Okuduğum", "Okumak İstediklerim"};

    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReadBooksFragment();
            case 1:
                return new CurrentlyReadingFragment();
            case 2:
                return new WantToReadFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

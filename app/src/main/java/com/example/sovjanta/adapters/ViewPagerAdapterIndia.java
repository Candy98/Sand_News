package com.example.sovjanta.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sovjanta.indiafrag.BusinessFragment;
import com.example.sovjanta.indiafrag.EntertainmentFragment;
import com.example.sovjanta.indiafrag.HealthFragment;
import com.example.sovjanta.indiafrag.ScienceFragment;
import com.example.sovjanta.indiafrag.SportsFragment;
import com.example.sovjanta.indiafrag.TechnologyFragment;
import com.example.sovjanta.indiafrag.TopHeadlinesFragment;

public class ViewPagerAdapterIndia extends FragmentPagerAdapter {
    public ViewPagerAdapterIndia(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new TopHeadlinesFragment();
        }
        else if (position == 1)
        {
            fragment = new TechnologyFragment();
        }
        else if (position == 2)
        {
            fragment = new SportsFragment();
        }
        else if (position == 3)
        {
            fragment = new ScienceFragment();
        }
        else if (position == 4)
        {
            fragment = new HealthFragment();
        }
        else if (position == 5)
        {
            fragment = new EntertainmentFragment();
        }
        else if (position == 6)
        {
            fragment = new BusinessFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Headlines";
        }
        else if (position == 1)
        {
            title = "Tech";
        }
        else if (position == 2)
        {
            title = "Sports";
        }
        else if (position == 3)
        {
            title = "Science";
        }
        else if (position == 4)
        {
            title = "Health";
        }
        else if (position == 5)
        {
            title = "Entertainment";
        }
        else if (position == 6)
        {
            title = "Business";
        }
        return title;
    }
}

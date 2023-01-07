package com.wavesignal.mmtest.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wavesignal.mmtest.audiotest.Nav1Tab3;
import com.wavesignal.mmtest.audiotest.Nav1Tab4;
import com.wavesignal.mmtest.audiotest.R;
import com.wavesignal.mmtest.audiotest.Nav1Tab1;
import com.wavesignal.mmtest.audiotest.Nav1Tab2;
import com.wavesignal.mmtest.audiotest.Nav1Tab5;
import com.wavesignal.mmtest.audiotest.Nav1Tab6;
import com.wavesignal.mmtest.audiotest.Nav1Tab7;

public class NavFragment1 extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static NavFragment1 newInstance() {
        NavFragment1 fragment = new NavFragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_fragment_1, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Nav1Tab1(), "Tab1");
        adapter.addFragment(new Nav1Tab2(), "Tab2");
        adapter.addFragment(new Nav1Tab3(), "Tab3");
        adapter.addFragment(new Nav1Tab4(), "Tab4");
        adapter.addFragment(new Nav1Tab5(), "Tab5");
        adapter.addFragment(new Nav1Tab6(), "Tab6");
        adapter.addFragment(new Nav1Tab7(), "Tab7");
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabOne = view1.findViewById(R.id.text);
        tabOne.setText("One");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_black_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabTwo = view2.findViewById(R.id.text);
        tabTwo.setText("Two");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_border_black_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View view3 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabThree = view3.findViewById(R.id.text);
        tabThree.setText("Three");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings_black_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
//
        View view4 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabFour = view4.findViewById(R.id.text);
        tabFour.setText("Four");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings_black_24dp, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        View view5 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabFive = view5.findViewById(R.id.text);
        tabFive.setText("Five");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings_black_24dp, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);

        View view6 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabSix = view6.findViewById(R.id.text);
        tabSix.setText("Six");
        tabSix.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings_black_24dp, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tabSix);

        View view7 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabSeven = view7.findViewById(R.id.text);
        tabSeven.setText("Seven");
        tabSeven.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings_black_24dp, 0, 0);
        tabLayout.getTabAt(6).setCustomView(tabSeven);
    }
}

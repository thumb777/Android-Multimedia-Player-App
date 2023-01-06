package com.example.audiotest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

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
        adapter.addFragment(new TabFragment1(), "Tab1");
        adapter.addFragment(new TabFragment2(), "Tab2");
        adapter.addFragment(new TabFragment3(), "Tab3");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabOne = view.findViewById(R.id.text);
        tabOne.setText("One");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_black_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabTwo = view1.findViewById(R.id.text);
        tabTwo.setText("Two");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_border_black_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabThree = view2.findViewById(R.id.text);
        tabThree.setText("Three");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings_black_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
//
//        TextView tabThree = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
//        tabThree.setText("THREE");
//        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings_black_24dp, 0, 0);
//        tabLayout.getTabAt(2).setCustomView(tabThree);
    }
}

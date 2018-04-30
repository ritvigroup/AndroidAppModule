package com.ritvi.kaajneeti.fragment.homeactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.ViewPagerWithTitleAdapter;
import com.ritvi.kaajneeti.fragment.GroupListFragment;
import com.ritvi.kaajneeti.fragment.myconnection.ContactFragment;
import com.ritvi.kaajneeti.fragment.myconnection.FriendsFragment;
import com.ritvi.kaajneeti.fragment.myconnection.RequestFragment;
import com.ritvi.kaajneeti.fragment.myconnection.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 27-02-2018.
 */

public class MyConnectionFragment extends Fragment {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_connection, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    boolean is_initialize;

    public void initialize() {
        if (!is_initialize) {
            setUpTabswithViewPager();
            is_initialize = true;
        }
    }

    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());
//        adapter.addFrag(new SuggestionFragment(), "Suggestion");
        final SearchFragment searchFragment = new SearchFragment();
        final RequestFragment requestFragment = new RequestFragment();
        final ContactFragment contactFragment = new ContactFragment();
        final FriendsFragment friendsFragment = new FriendsFragment();
        final GroupListFragment groupListFragment = new GroupListFragment();

        adapter.addFrag(searchFragment, "Search");
        adapter.addFrag(requestFragment, "Request");
        adapter.addFrag(contactFragment, "Contact");
        adapter.addFrag(friendsFragment, "Connect");
        adapter.addFrag(groupListFragment, "Group");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

//        searchFragment.initialize();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
//                    case 0:searchFragment.initialize();
//                        break;
                    case 0:
                        requestFragment.initialize();
                        break;
                    case 1:
                        contactFragment.initialize();
                        break;
                    case 2:
                        friendsFragment.initialize();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

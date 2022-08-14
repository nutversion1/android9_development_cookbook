package com.example.slideshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private final int PAGE_COUNT = 3;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewPager);
        mPagerAdapter = new SlideAdapter((getSupportFragmentManager()));
        mViewPager.setAdapter(mPagerAdapter);
    }

    private class SlideAdapter extends FragmentStatePagerAdapter {

        public SlideAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            SlideFragment slideFragment = new SlideFragment();

            switch (position){
                case 0:
                    slideFragment.setImage(R.drawable.slide_1);
                    break;
                case 1:
                    slideFragment.setImage(R.drawable.slide_2);
                    break;
                case 2:
                    slideFragment.setImage(R.drawable.slide_3);
                    break;
            }

            return slideFragment;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() == 0){
            super.onBackPressed();
        }else{
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
        }

    }
}
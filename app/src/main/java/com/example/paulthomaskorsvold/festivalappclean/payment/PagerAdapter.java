package com.example.paulthomaskorsvold.festivalappclean.payment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by paulthomaskorsvold on 10/17/17.
 *
 */

public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PaymentFragment tab1 = new PaymentFragment();
                return tab1;
            case 1:
                AddFragment tab2 = new AddFragment();
                return tab2;
            case 2:
                SplitFragment tab3 = new SplitFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

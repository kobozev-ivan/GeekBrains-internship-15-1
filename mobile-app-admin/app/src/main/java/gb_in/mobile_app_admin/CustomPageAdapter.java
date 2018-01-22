package gb_in.mobile_app_admin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 13.01.2018.
 */

public class CustomPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public CustomPageAdapter(FragmentManager fm, List<Fragment> listFragments) {
        super(fm);
        fragments = listFragments;
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
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Справочники";
            case 1: return "Общая статистика";
            default: return "";
        }
    }
}

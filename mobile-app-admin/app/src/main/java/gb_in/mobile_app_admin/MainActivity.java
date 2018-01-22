package gb_in.mobile_app_admin;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gb_in.mobile_app_admin.presenter.Presenter;
import gb_in.mobile_app_admin.view.AbstractReferenceClass;


public class MainActivity extends AppCompatActivity   implements AbstractReferenceClass {
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ReferenceFragment referenceFragment = new ReferenceFragment();
        StatisticFragment statisticFragment = new StatisticFragment();
        presenter = new Presenter(this,referenceFragment,statisticFragment);
        referenceFragment.setPresenter(presenter);
        statisticFragment.setPresenter(presenter);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(referenceFragment);
        fragments.add(statisticFragment);

        CustomPageAdapter cpa = new CustomPageAdapter(getSupportFragmentManager(),fragments);
        ViewPager vp = findViewById(R.id.viewPager);
        vp.setAdapter(cpa);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: presenter.loadPersonData(); break;
                    case 1: presenter.loadPersonStatistics(); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void displayError(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}

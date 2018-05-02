package ages181.policiafederal_android;

/**
 * Created by 15104313 on 27/04/18.
 */

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);

        if(getIntent().getExtras() != null) {
            int intentFragment = getIntent().getExtras().getInt("frgToLoad");
            pager.setCurrentItem(intentFragment);
        }

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private String tabTitles[] = new String[] { "Endereço", "Sobre o Local", "Dados Gerais", "Responsável", "Vestigios" };

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return TelaEndereco.newInstance();
                case 1:
                    return SobreOLocal.newInstance();
                case 2:
                    return DadosGerais.newInstance();
                case 3:
                    return Responsavel.newInstance();
                case 4:
                    return TelaListaVestigios.newInstance();
                default:
                    return TelaEndereco.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
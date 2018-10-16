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
import android.text.Editable;
import android.view.View;

public class MainActivity extends FragmentActivity {
    private MyPagerAdapter adapter;

    private DadosGerais dadosGerais;
    private TelaEndereco telaEndereco;
    private Responsavel responsavel;
    private SobreOLocal sobreOLocal;
    private SobreOFato sobreOFato;
    private TelaListaVestigios telaListaVestigios;
    private TelaTestemunhas telaTestemunhas;

    private int telaAnterior = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager pager = (ViewPager) findViewById(R.id.viewpager);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (telaAnterior){
                    case 0:
                        dadosGerais.salvarDadosGerais();
                        break;
                    case 1:
                        telaEndereco.salvaTelaEndereco();
                        break;
                    case 2:
                        responsavel.salvaResponsavel();
                        break;
                    case 3:
                        sobreOLocal.salvaSobreOLocal();
                        break;
                    case 4:
                        sobreOFato.salvaSobreOFato();
                        break;
                }
                telaAnterior = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);

        if (getIntent().getExtras() != null) {
            int intentFragment = getIntent().getExtras().getInt("frgToLoad");
            pager.setCurrentItem(intentFragment);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private String tabTitles[] = new String[]{"Dados Gerais", "Endereço", "Responsável do Local", "Sobre o Local", "Sobre o Fato", "Vestigios", "Testemunhas"};

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    dadosGerais = DadosGerais.newInstance();
                    return dadosGerais;
                case 1:
                    telaEndereco = TelaEndereco.newInstance();
                    return telaEndereco;
                case 2:
                    responsavel = Responsavel.newInstance();
                    return responsavel;
                case 3:
                    sobreOLocal = SobreOLocal.newInstance();
                    return sobreOLocal;
                case 4:
                    sobreOFato = SobreOFato.newInstance();
                    return sobreOFato;
                case 5:
                    telaListaVestigios = TelaListaVestigios.newInstance();
                    return telaListaVestigios;
                case 6:
                    telaTestemunhas = TelaTestemunhas.newInstance();
                    return telaTestemunhas;
                default:
                    telaEndereco = TelaEndereco.newInstance();
                    return telaEndereco;
            }
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

    }
}


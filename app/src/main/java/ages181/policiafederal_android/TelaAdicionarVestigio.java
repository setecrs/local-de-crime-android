package ages181.policiafederal_android;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TelaAdicionarVestigio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_vestigio);
        configuraBotaoVoltar();
        configuraBotaoAdiciona();
        configuraBotaoGravar();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void configuraBotaoGravar() {
        Button botaoGravar = (Button) findViewById(R.id.buttonGravar);
        botaoGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TelaAdicionarVestigio.this,TelaListaVestigios.class));
            }
        });

    }

    private void configuraBotaoVoltar() {
        ImageButton botaoVoltar = (ImageButton) findViewById(R.id.imageButtonVoltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(TelaAdicionarVestigio.this, MainActivity.class));
                i.putExtra("frgToLoad", 4);

                // Now start your activity
                startActivity(i);
            }
        });

    }

    private void configuraBotaoAdiciona() {
        FloatingActionButton fab = findViewById(R.id.floatingActionButtonAdicionarVestigio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TelaAdicionarVestigio.this,TelaAdicionarVestigio.class));
            }
        });
    }
}

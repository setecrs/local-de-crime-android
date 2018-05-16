package ages181.policiafederal_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TelaTestemunhas extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.testemunhas, container, false);
        return v;
    }

    public static TelaTestemunhas newInstance() {
        TelaTestemunhas f = new TelaTestemunhas();
        return f;
    }
}

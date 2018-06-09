package ages181.policiafederal_android;

import java.util.List;

public class ListaOcorrencia {
    private static List<Ocorrencia> lista;

    public ListaOcorrencia(List<Ocorrencia> list) {
        lista = list;
    }

    public static List<Ocorrencia> getLista() {
        return lista;
    }

    public static void setLista(List<Ocorrencia> lista) {
        ListaOcorrencia.lista = lista;
    }
}


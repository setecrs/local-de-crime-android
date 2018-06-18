package ages181.policiafederal_android;

public class Ocorrencia {

    private String id, numeroOcorrencia, dataHoraAcionamento, tipoLocal;

    public Ocorrencia(String id, String numeroOcorrencia, String dataHoraAcionamento, String tipoLocal) {
        this.id = id;
        this.numeroOcorrencia = numeroOcorrencia;
        this.dataHoraAcionamento = dataHoraAcionamento;
        this.tipoLocal = tipoLocal;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public String getNumeroOcorrencia() {
        return numeroOcorrencia;
    }

    public void setNumeroOcorrencia(String numeroOcorrencia) {
        this.numeroOcorrencia = numeroOcorrencia;
    }

    public String getDataHoraAcionamento() {
        return dataHoraAcionamento;
    }

    public void setDataHoraAcionamento(String dataHoraAcionamento) {
        this.dataHoraAcionamento = dataHoraAcionamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String formatarDataHora() {
        String ano = getDataHoraAcionamento().substring(0, 4);
        String mes = getDataHoraAcionamento().substring(5, 7);
        String dia = getDataHoraAcionamento().substring(8, 10);

        String data = dia + "/" + mes + "/" + ano;

        String hora = getDataHoraAcionamento().substring(11, 16);

        String datahora = data + " | " + hora;
        return datahora;
    }


}


package ages181.policiafederal_android;

public class Vestigio {

    private String informacoesAdicionais, tipoVestigio, nomeVestigio, etiqueta, idBanco;
    private int numId;

    public Vestigio(int numId, String informacoesAdicionais, String etiqueta, String nomeVest,String idBancoN) {
        this.numId = numId;
        this.informacoesAdicionais = informacoesAdicionais;
        this.etiqueta = etiqueta;
        this.nomeVestigio = nomeVest;
        this.idBanco = idBancoN;
    }


    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getNomeVestigio() {
        return nomeVestigio;
    }

    public void setNomeVestigio(String nomeVestigio) {
        this.nomeVestigio = nomeVestigio;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public String getInformacoesAdicionais() {
        return informacoesAdicionais;
    }

    public void setInformacoesAdicionais(String informacoesAdicionais) {
        this.informacoesAdicionais = informacoesAdicionais;
    }

    public String getTipoVestigio() {
        return tipoVestigio;
    }

    public void setTipoVestigio(String tipoVestigio) {
        this.tipoVestigio = tipoVestigio;
    }
}

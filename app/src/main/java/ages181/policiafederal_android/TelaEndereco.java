package ages181.policiafederal_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

public class TelaEndereco extends Fragment{

    private Spinner spinnerLocal, spinnerEstado;
    private EditText editTextRua, editTextNumero, editTextComplemento,editTextOutro;
    private AutoCompleteTextView autoCompleteTextViewCidade;
    private String[] cidadesRS = {"Aceguá","Água Santa","Agudo","Ajuricaba","Alecrim","Alegrete","Alegria", "Almirante Tamandaré do Sul", "Alpestre", "Alto Alegre", "Alto Feliz", "Alvorada", "Amaral Ferrador", "Ametista do Sul", "André da Rocha", "Anta Gorda", "Antônio Prado", "Arambaré", "Araricá", "Aratiba", "Arroio do Meio", "Arroio do Padre", "Arroio do Sal", "Arroio do Tigre", "Arroio dos Ratos", "Arroio Grande", "Arvorezinha", "Augusto Pestana", "Áurea", "Bagé", "Balneário Pinhal", "Barão", "Barão de Cotegipe", "Barão do Triunfo", "Barra do Guarita", "Barra do Quaraí", "Barra do Ribeiro", "Barra do Rio Azul", "Barra Funda", "Barracão", "Barros Cassal", "Benjamin Constan do Sul", "Bento Gonçalves", "Boa Vista das Missões", "Boa Vista do Buricá", "Boa Vista do Cadeado", "Boa Vista do Incra", "Boa Vista do Sul", "Bom Jesus", "Bom Princípio", "Bom Progresso", "Bom Retiro do Sul", "Boqueirão do Leão", "Bossoroca", "Bozano", "Braga", "Brochier", "Butiá", "Caçapava do Sul", "Cacequi", "Cachoeira do Sul", "Cachoeirinha", "Cacique Doble", "Caibaté", "Caiçara", "Camaquã", "Camargo", "Cambará do Sul", "Campestre da Serra", "Campina das Missões", "Campinas do Sul", "Campo Bom", "Campo Novo", "Campos Borges", "Candelária", "Cândido Godói", "Candiota", "Canela", "Canguçu", "Canoas", "Canudos do Vale", "Capão Bonito do Sul", "Capão da Canoa", "Capão do Cipó", "Capão do Leão", "Capela de Santana", "Capitão", "Capivari do Sul", "Caraá", "Carazinho", "Carlos Barbosa", "Carlos Gomes", "Casca", "Caseiros", "Catuípe", "Caxias do Sul", "Centenário", "Cerrito", "Cerro Branco", "Cerro Grande", "Cerro Grande do Sul", "Cerro Largo", "Chapada", "Charqueadas", "Charrua", "Chiapeta", "Chuí", "Chuvisca", "Cidreira", "Ciríaco", "Colinas", "Colorado", "Condor", "Constantina", "Coqueiro Baixo", "Coqueiros do Sul", "Coronel Barros", "Coronel Bicaco", "Coronel Pilar", "Cotiporã", "Coxilha", "Crissiumal", "Cristal", "Cristal do Sul", "Cruz Alta", "Cruzaltense", "Cruzeiro do Sul", "David Canabarro", "Derrubadas", "Dezesseis de Novembro", "Dilermando de Aguiar", "Dois Irmãos", "Dois Irmãos das Missões", "Dois Lajeados", "Dom Feliciano", "Dom Pedrito", "Dom Pedro de Alcântara", "Dona Francisca", "Doutor Maurício Cardoso", "Doutor Ricardo", "Eldorado do Sul", "Encantado", "Encruzilhada do Sul", "Engenho Velho", "Entre Rios do Sul", "Entre-Ijuís", "Erebango", "Erechim", "Ernestina", "Erval Grande", "Erval Seco", "Esmeralda", "Esperança do Sul", "Espumoso", "Estação", "Estância Velha", "Esteio", "Estrela", "Estrela Velha", "Eugênio de Castro", "Fagundes Varela", "Farroupilha", "Faxinal do Soturno", "Faxinalzinho", "Fazenda Vilanova", "Feliz", "Flores da Cunha", "Floriano Peixoto", "Fontoura Xavier", "Formigueiro", "Forquetinha", "Fortaleza dos Valos", "Frederico Westphalen", "Garibaldi", "Garruchos", "Gaurama", "General Câmara", "Gentil", "Getúlio Vargas", "Giruá", "Glorinha", "Gramado", "Gramado dos Loureiros", "Gramado Xavier", "Gravataí", "Guabiju", "Guaíba", "Guaporé", "Guarani das Missões", "Harmonia", "Herval", "Herveiras", "Horizontina", "Hulha Negra", "Humaitá", "Ibarama", "Ibiaçá", "Ibiraiaras", "Ibirapuitã", "Ibirubá", "Igrejinha", "Ijuí", "Ilópolis", "Imbé", "Imigrante", "Independência", "Inhacorá", "Ipê", "Ipiranga do Sul", "Iraí", "Itaara", "Itacurubi", "Itapuca", "Itaqui", "Itati", "Itatiba do Sul", "Ivorá", "Ivoti", "Jaboticaba", "Jacuizinho", "Jacutinga", "Jaguarão", "Jaguari", "Jaquirana", "Jari", "Jóia", "Júlio de Castilhos", "Lagoa Bonita do Sul", "Lagoa dos Três Cantos", "Lagoa Vermelha", "Lagoão", "Lajeado", "Lajeado do Bugre", "Lavras do Sul", "Liberato Salzano", "Lindolfo Collor", "Linha Nova", "Maçambara", "Machadinho", "Mampituba", "Manoel Viana", "Maquiné", "Maratá", "Marau", "Marcelino Ramos", "Mariana Pimentel", "Mariano Moro", "Marques de Souza", "Mata", "Mato Castelhano", "Mato Leitão", "Mato Queimado", "Maximiliano de Almeida", "Minas do Leão", "Miraguaí", "Montauri", "Monte Alegre dos Campos", "Monte Belo do Sul", "Montenegro", "Mormaço", "Morrinhos do Sul", "Morro Redondo", "Morro Reuter", "Mostardas", "Muçum", "Muitos Capões", "Muliterno", "Não-Me-Toque", "Nicolau Vergueiro", "Nonoai", "Nova Alvorada", "Nova Araçá", "Nova Bassano", "Nova Boa Vista", "Nova Bréscia", "Nova Candelária", "Nova Esperança do Sul", "Nova Hartz", "Nova Pádua", "Nova Palma", "Nova Petrópolis", "Nova Prata", "Nova Ramada", "Nova Roma do Sul", "Nova Santa Rita", "Novo Barreiro", "Novo Cabrais", "Novo Hamburgo", "Novo Machado", "Novo Tiradentes", "Novo Xingu", "Osório", "Paim Filho", "Palmares do Sul", "Palmeira das Missões", "Palmitinho", "Panambi", "Pântano Grande", "Paraí", "Paraíso do Sul", "Pareci Novo", "Parobé", "Passa Sete", "Passo do Sobrado", "Passo Fundo", "Paulo Bento", "Paverama", "Pedras Altas", "Pedro Osório", "Pejuçara", "Pelotas", "Picada Café", "Pinhal", "Pinhal da Serra", "Pinhal Grande", "Pinheirinho do Vale", "Pinheiro Machado", "Pirapó", "Piratini", "Planalto", "Poço das Antas", "Pontão", "Ponte Preta", "Portão", "Porto Alegre", "Porto Lucena", "Porto Mauá", "Porto Vera Cruz", "Porto Xavier", "Pouso Novo", "Presidente Lucena", "Progresso", "Protásio Alves", "Putinga", "Quaraí", "Quatro Irmãos", "Quevedos", "Quinze de Novembro", "Redentora", "Relvado", "Restinga Seca", "Rio dos Índios", "Rio Grande", "Rio Pardo", "Riozinho", "Roca Sales", "Rodeio Bonito", "Rolador", "Rolante", "Ronda Alta", "Rondinha", "Roque Gonzales", "Rosário do Sul", "Sagrada Família", "Saldanha Marinho", "Salto do Jacuí", "Salvador das Missões", "Salvador do Sul", "Sananduva", "Santa Bárbara do Sul", "Santa Cecília do Sul", "Santa Clara do Sul", "Santa Cruz do Sul", "Santa Margarida do Sul", "Santa Maria", "Santa Maria do Herval", "Santa Rosa", "Santa Tereza", "Santa Vitória do Palmar", "Santana da Boa Vista", "Santana do Livramento", "Santiago", "Santo Ângelo", "Santo Antônio da Patrulha", "Santo Antônio das Missões", "Santo Antônio do Palma", "Santo Antônio do Planalto", "Santo Augusto", "Santo Cristo", "Santo Expedito do Sul", "São Borja", "São Domingos do Sul", "São Francisco de Assis", "São Francisco de Paula", "São Gabriel", "São Jerônimo", "São João da Urtiga", "São João do Polêsine", "São Jorge", "São José das Missões", "São José do Herval", "São José do Hortêncio", "São José do Inhacorá", "São José do Norte", "São José do Ouro", "São José do Sul", "São José dos Ausentes", "São Leopoldo", "São Lourenço do Sul", "São Luiz Gonzaga", "São Marcos", "São Martinho", "São Martinho da Serra", "São Miguel das Missões", "São Nicolau", "São Paulo das Missões", "São Pedro da Serra", "São Pedro das Missões", "São Pedro do Butiá", "São Pedro do Sul", "São Sebastião do Caí", "São Sepé", "São Valentim", "São Valentim do Sul", "São Valério do Sul", "São Vendelino", "São Vicente do Sul", "Sapiranga", "Sapucaia do Sul", "Sarandi", "Seberi", "Sede Nova", "Segredo", "Selbach", "Senador Salgado Filho", "Sentinela do Sul", "Serafina Corrêa", "Sério", "Sertão", "Sertão Santana", "Sete de Setembro", "Severiano de Almeida", "Silveira Martins", "Sinimbu", "Sobradinho", "Soledade", "Tabaí", "Tapejara", "Tapera", "Tapes", "Taquara", "Taquari", "Taquaruçu do Sul", "Tavares", "Tenente Portela", "Terra de Areia", "Teutônia", "Tio Hugo", "Tiradentes do Sul", "Toropi", "Torres", "Tramandaí", "Travesseiro", "Três Arroios", "Três Cachoeiras", "Três Coroas", "Três de Maio", "Três Forquilhas", "Três Palmeiras", "Três Passos", "Trindade do Sul", "Triunfo", "Tucunduva", "Tunas", "Tupanci do Sul", "Tupanciretã", "Tupandi", "Tuparendi", "Turuçu", "Ubiretama", "União da Serra", "Unistalda", "Uruguaiana", "Vacaria", "Vale do Sol", "Vale Real", "Vale Verde", "Vanini", "Venâncio Aires", "Vera Cruz", "Veranópolis", "Vespasiano Correa", "Viadutos", "Viamão", "Vicente Dutra", "Victor Graeff", "Vila Flores", "Vila Lângaro", "Vila Maria", "Vila Nova do Sul", "Vista Alegre", "Vista Alegre do Prata", "Vista Gaúcha", "Vitória das Missões", "Westfália", "Xangri-lá"};
    private String[] cidadesSC = {"Abdon Batista", "Abelardo Luz", "Agrolândia", "Agronômica", "Água Doce", "Águas de Chapecó", "Águas Frias", "Águas Mornas", "Alfredo Wagner", "Alto Bela Vista", "Anchieta", "Angelina", "Anita Garibaldi", "Anitápolis", "Antônio Carlos", "Apiúna", "Arabutã", "Araquari", "Araranguá", "Armazém", "Arroio Trinta", "Arvoredo", "Ascurra", "Atalanta", "Aurora", "Balneário Arroio do Silva", "Balneário Barra do Sul", "Balneário Camboriú", "Balneário Gaivota", "Bandeirante", "Barra Bonita", "Barra Velha", "Bela Vista do Toldo", "Belmonte", "Benedito Novo", "Biguaçu", "Blumenau", "Bocaina do Sul", "Bom Jardim da Serra", "Bom Jesus", "Bom Jesus do Oeste", "Bom Retiro", "Bombinhas", "Botuverá", "Braço do Norte", "Braço do Trombudo", "Brunópolis", "Brusque", "Caçador", "Caibi", "Calmon", "Camboriú", "Campo Alegre", "Campo Belo do Sul", "Campo Erê", "Campos Novos", "Canelinha", "Canoinhas", "Capão Alto", "Capinzal", "Capivari de Baixo", "Catanduvas", "Caxambu do Sul", "Celso Ramos", "Cerro Negro", "Chapadão do Lageado", "Chapecó", "Cocal do Sul", "Concórdia", "Cordilheira Alta", "Coronel Freitas", "Coronel Martins", "Correia Pinto", "Corupá", "Criciúma", "Cunha Porã", "Cunhataí", "Curitibanos", "Descanso", "Dionísio Cerqueira", "Dona Emma", "Doutor Pedrinho", "Entre Rios", "Ermo", "Erval Velho", "Faxinal dos Guedes", "Flor do Sertão", "Florianópolis", "Formosa do Sul", "Forquilhinha", "Fraiburgo", "Frei Rogério", "Galvão", "Garopaba", "Garuva", "Gaspar", "Governador Celso Ramos", "Grão Pará", "Gravatal", "Guabiruba", "Guaraciaba", "Guaramirim", "Guarujá do Sul", "Guatambú", "Herval d'Oeste", "Ibiam", "Ibicaré", "Ibirama", "Içara", "Ilhota", "Imaruí", "Imbituba", "Imbuia", "Indaial", "Iomerê", "Ipira", "Iporã do Oeste", "Ipuaçu", "Ipumirim", "Iraceminha", "Irani", "Irati", "Irineópolis", "Itá", "Itaiópolis", "Itajaí", "Itapema", "Itapiranga", "Itapoá", "Ituporanga", "Jaborá", "Jacinto Machado", "Jaguaruna", "Jaraguá do Sul", "Jardinópolis", "Joaçaba", "Joinville", "José Boiteux", "Jupiá", "Lacerdópolis", "Lages", "Laguna", "Lajeado Grande", "Laurentino", "Lauro Muller", "Lebon Régis", "Leoberto Leal", "Lindóia do Sul", "Lontras", "Luiz Alves", "Luzerna", "Macieira", "Mafra", "Major Gercino", "Major Vieira", "Maracajá", "Maravilha", "Marema", "Massaranduba", "Matos Costa", "Meleiro", "Mirim Doce", "Modelo", "Mondaí", "Monte Carlo", "Monte Castelo", "Morro da Fumaça", "Morro Grande", "Navegantes", "Nova Erechim", "Nova Itaberaba", "Nova Trento", "Nova Veneza", "Novo Horizonte", "Orleans", "Otacílio Costa", "Ouro", "Ouro Verde", "Paial", "Painel", "Palhoça", "Palma Sola", "Palmeira", "Palmitos", "Papanduva", "Paraíso", "Passo de Torres", "Passos Maia", "Paulo Lopes", "Pedras Grandes", "Penha", "Peritiba", "Petrolândia", "Piçarras", "Pinhalzinho", "Pinheiro Preto", "Piratuba", "Planalto Alegre", "Pomerode", "Ponte Alta", "Ponte Alta do Norte", "Ponte Serrada", "Porto Belo", "Porto União", "Pouso Redondo", "Praia Grande", "Presidente Castelo Branco", "Presidente Getúlio", "Presidente Nereu", "Princesa", "Quilombo", "Rancho Queimado", "Rio das Antas", "Rio do Campo", "Rio do Oeste", "Rio do Sul", "Rio dos Cedros", "Rio Fortuna", "Rio Negrinho", "Rio Rufino", "Riqueza", "Rodeio", "Romelândia", "Salete", "Saltinho", "Salto Veloso", "Sangão", "Santa Cecília", "Santa Helena", "Santa Rosa de Lima", "Santa Rosa do Sul", "Santa Terezinha", "Santa Terezinha do Progresso", "Santiago do Sul", "Santo Amaro da Imperatriz", "São Bento do Sul", "São Bernardino", "São Bonifácio", "São Carlos", "São Cristovão do Sul", "São Domingos", "São Francisco do Sul", "São João Batista", "São João do Itaperiú", "São João do Oeste", "São João do Sul", "São Joaquim", "São José", "São José do Cedro", "São José do Cerrito", "São Lourenço do Oeste", "São Ludgero", "São Martinho", "São Miguel da Boa Vista", "São Miguel do Oeste", "São Pedro de Alcântara", "Saudades", "Schroeder", "Seara", "Serra Alta", "Siderópolis", "Sombrio", "Sul Brasil", "Taió", "Tangará", "Tigrinhos", "Tijucas", "Timbé do Sul", "Timbó", "Timbó Grande", "Três Barras", "Treviso", "Treze de Maio", "Treze Tílias", "Trombudo Central", "Tubarão", "Tunápolis", "Turvo", "União do Oeste", "Urubici", "Urupema", "Urussanga", "Vargeão", "Vargem", "Vargem Bonita", "Vidal Ramos", "Videira", "Vitor Meireles", "Witmarsum", "Xanxerê", "Xavantina", "Xaxim", "Zortéa"};

    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.endereco, container, false);


        spinnerLocal = v.findViewById(R.id.spinnerLocal);
        spinnerEstado = v.findViewById(R.id.spinnerEstado);
        editTextOutro = v.findViewById(R.id.editTextOutro);
        editTextRua = v.findViewById(R.id.editTextRua);
        editTextNumero = v.findViewById(R.id.editTextNumero);
        editTextComplemento = v.findViewById(R.id.editTextComplemento);
        autoCompleteTextViewCidade = v.findViewById(R.id.autoCompleteTextViewCidade);

        onClickSpinner(spinnerEstado);
        onClickSpinner(spinnerLocal);
        editTextOutro.setVisibility(View.GONE);
        return v;
    }


    public void onClickSpinner(View v) {

        if (v.getId() == R.id.spinnerEstado) {
            if (((Spinner) v).getSelectedItem().toString().equals("RS")) {
                adapter = new ArrayAdapter<>(this.getContext(),
                        android.R.layout.simple_list_item_1, cidadesRS);
                autoCompleteTextViewCidade.setAdapter(adapter);
            } else {
                adapter = new ArrayAdapter<>(this.getContext(),
                        android.R.layout.simple_list_item_1, cidadesSC);
                autoCompleteTextViewCidade.setAdapter(adapter);
            }
        } else {
            if (((Spinner) v).getSelectedItem().toString().equals("Outro")) {
                editTextOutro.setVisibility(View.VISIBLE);
            } else {
                editTextOutro.setVisibility(View.GONE);
                editTextOutro.setText("");
            }
        }
    }

    public String localSelecionado(){
        if(spinnerLocal.getSelectedItem().toString().equals("Outro")){
            return editTextOutro.toString();
        } else {
            return spinnerLocal.getSelectedItem().toString();
        }
    }

    public void sendMassage(View view){
        String local = localSelecionado();
        try{
            HttpEndereco t  = new HttpEndereco(local, spinnerEstado.getSelectedItem().toString(), autoCompleteTextViewCidade.getText().toString(),
                                        editTextRua.getText().toString(), editTextNumero.getText().toString(), editTextComplemento.getText().toString(),
                                        StaticProperties.getId());
            t.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static TelaEndereco newInstance() {
        TelaEndereco f = new TelaEndereco();
        return f;
    }
}

//route: /endereco/{idOcorrencia} | method: PATCH | params:
//        {
//        "tipoLocal": String,
//        "estado": String,
//        "municipio": String,
//        "logradouro": String,
//        "complemento": String,
//        "_id": Ocorrencia._id
//        }
//        | Headers: {"x-access-token": [JWT TOKEN]) } [DEVE ESTAR AUTENTICADO]
package ages181.policiafederal_android;

/**
 * Created by 15104313 on 27/04/18.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class MainActivity extends FragmentActivity {
    private MyPagerAdapter adapter;

    private DadosGerais dadosGerais;
    private TelaEndereco telaEndereco;
    private Responsavel responsavel;
    private SobreOLocal sobreOLocal;
    private SobreOFato sobreOFato;
    private TelaListaVestigios telaListaVestigios;
    private TelaTestemunhas telaTestemunhas;
    private Button button;

    private int telaAnterior = 0;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager pager = findViewById(R.id.viewpager);

        button = findViewById(R.id.botaoEncPdf);

        if (CarregarOcorrencia.isEncerrada) {
            button.setText("Gerar PDF");
        } else {
            button.setText("Encerrar ocorrencia");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CarregarOcorrencia.isEncerrada) {
                    gerarRelatorioPDF();
                } else {
                    //encerra ocorrencia
                    try {
                        HttpFinalizaOcorrencia htp = new HttpFinalizaOcorrencia(StaticProperties.getIdOcorrencia());
                        htp.execute().get();

                        //VERIFICA RETORNO
                        if (htp.getStatusCode() == 200) {
                            Toast toast = Toast.makeText(pager.getContext(), "Ocorrência encerrada!", Toast.LENGTH_SHORT);
                            toast.show();
                            button.setText("Gerar PDF");
                            CarregarOcorrencia.isEncerrada = true;
                            if (dadosGerais != null) {
                                dadosGerais.verificaEncerrada();
                            }
                            if (telaEndereco != null) {
                                telaEndereco.verificaEncerrada();
                            }
                            if (responsavel != null) {
                                responsavel.verificaEncerrada();
                            }
                            if (sobreOLocal != null) {
                                sobreOLocal.verificaEncerrada();
                            }
                            if (sobreOFato != null) {
                                sobreOFato.verificaEncerrada();
                            }
                            if (telaListaVestigios != null) {
                                telaListaVestigios.verificaEncerrada();
                            }
                            if (telaTestemunhas != null) {
                                telaTestemunhas.verificaEncerrada();
                            }

                        } else {
                            Toast toast = Toast.makeText(pager.getContext(), "Erro: " + htp.getStatusCode(), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(pager.getContext(), "Erro interno", Toast.LENGTH_SHORT);
                        toast.show();
                        e.printStackTrace();
                    }
                }
            }
        });

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (telaAnterior) {
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
                    case 6:
                        telaTestemunhas.salvaTestemunha();
                        break;
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pager.getWindowToken(), 0);

                telaAnterior = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);

        if (getIntent().getExtras() != null) {
            int intentFragment = getIntent().getExtras().getInt("frgToLoad");
            pager.setCurrentItem(intentFragment);
        }

    }

    public void gerarRelatorioPDF() {
        verifyStoragePermissions(this);
        Document document = new Document();
        try {

            //Infos basicas do arquivo
            String nomeArq = StaticProperties.getIdOcorrencia() + ".pdf";
            File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + nomeArq);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            Paragraph pCabecalho1 = new Paragraph();
            Paragraph pCabecalho2 = new Paragraph();

            Font cabFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);

            pCabecalho1.add(new Chunk("SERVIÇO PÚBLICO FEDERAL", cabFont));
            pCabecalho2.add(new Chunk("PRE-LAUDO DE PERÍCIA CRIMINAL FEDERAL\n", cabFont));

            pCabecalho1.setAlignment(Element.ALIGN_CENTER);
            pCabecalho2.setAlignment(Element.ALIGN_CENTER);

            document.add(pCabecalho1);
            document.add(pCabecalho2);

            document.add(new Chunk(" "));
            //Fontes dos paragrafos e capitulos
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);

            //
            //          Dados gerais
            //

            Chunk dadosGeraisTitle = new Chunk("Dados gerais", chapterFont);

            PdfPTable tableDadosGerais = new PdfPTable(2);
            tableDadosGerais.setTotalWidth(200);
            tableDadosGerais.setWidths(new int[]{35, 65});
            tableDadosGerais.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cell;

            //Numero da ocorrencia
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Número da ocorrência: "));
            tableDadosGerais.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getDgNumeroOcorrencia()));
            tableDadosGerais.addCell(cell);

            //Sede da ocorrencia
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Sede da ocorrência: "));
            tableDadosGerais.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getDgSedeOcorrencia()));
            tableDadosGerais.addCell(cell);

            //Policiais envolvidos
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Policiais envolvidos: "));
            tableDadosGerais.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            List list = new List(List.UNORDERED);
            for (String s : CarregarOcorrencia.listaPeritos)
                list.add(new ListItem(new Chunk(s)));
            cell.addElement(list);
            tableDadosGerais.addCell(cell);

            //Data de acionamento
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Data de acionamento: "));
            tableDadosGerais.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getDgDataAcionamento()));
            tableDadosGerais.addCell(cell);

            //Hora de acionamento
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Hora de acionamento: "));
            tableDadosGerais.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getDgHoraAcionamento()));
            tableDadosGerais.addCell(cell);

            Paragraph pDadosGerais = new Paragraph();
            pDadosGerais.add(dadosGeraisTitle);
            pDadosGerais.add(tableDadosGerais);

            document.add(pDadosGerais);

            document.add(new Chunk(" "));
            //
            //          Endereco
            //


            Chunk enderecoTitle = new Chunk("Endereço", chapterFont);

            PdfPTable tableEndereco = new PdfPTable(2);
            tableEndereco.setTotalWidth(200);
            tableEndereco.setWidths(new int[]{35, 65});
            tableEndereco.setHorizontalAlignment(Element.ALIGN_LEFT);

            //Estado
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Estado: "));
            tableEndereco.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getEndEstado()));
            tableEndereco.addCell(cell);

            //Cidade
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Cidade: "));
            tableEndereco.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getEndCidade()));
            tableEndereco.addCell(cell);

            //Local
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Local: "));
            tableEndereco.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            if (CarregarOcorrencia.getEndLocal().equals("Outro"))
                cell.addElement(new Paragraph(CarregarOcorrencia.getEndOutroLocal()));
            else
                cell.addElement(new Paragraph(CarregarOcorrencia.getEndLocal()));
            tableEndereco.addCell(cell);

            //Rua/Avenida
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Rua/Avenida: "));
            tableEndereco.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getEndRua()));
            tableEndereco.addCell(cell);

            //Numero
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Número: "));
            tableEndereco.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getEndNumero()));
            tableEndereco.addCell(cell);

            //Complemento
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Complemento: "));
            tableEndereco.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getEndComplemento()));
            tableEndereco.addCell(cell);

            Paragraph pEndereco = new Paragraph();
            pEndereco.add(enderecoTitle);
            pEndereco.add(tableEndereco);

            document.add(pEndereco);

            document.add(new Chunk(" "));
            //
            //          Responsavel do local
            //


            Chunk responsavelTitle = new Chunk("Responsável do local", chapterFont);

            PdfPTable tableResponsavel = new PdfPTable(2);
            tableResponsavel.setTotalWidth(200);
            tableResponsavel.setWidths(new int[]{35, 65});
            tableResponsavel.setHorizontalAlignment(Element.ALIGN_LEFT);

            //Cargo
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Cargo: "));
            tableResponsavel.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getRespCargo()));
            tableResponsavel.addCell(cell);

            //Nome do responsavel
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Nome: "));
            tableResponsavel.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getRespNome()));
            tableResponsavel.addCell(cell);

            //Documento do responsalve
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Documento: "));
            tableResponsavel.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getRespDoc()));
            tableResponsavel.addCell(cell);

            //Entrevista
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Entrevista: "));
            tableResponsavel.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getRespEntrevista()));
            tableResponsavel.addCell(cell);

            Paragraph pResponsavel = new Paragraph();
            pResponsavel.add(responsavelTitle);
            pResponsavel.add(tableResponsavel);

            document.add(pResponsavel);

            document.add(new Chunk(" "));
            //
            //          Sobre o local
            //


            Chunk sobreLocalTitle = new Chunk("Sorbe o local", chapterFont);

            PdfPTable tableSobreLocal = new PdfPTable(2);
            tableSobreLocal.setTotalWidth(200);
            tableSobreLocal.setWidths(new int[]{35, 65});
            tableSobreLocal.setHorizontalAlignment(Element.ALIGN_LEFT);

            //Data de chegada
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Data de chegada dos policiais: "));
            tableSobreLocal.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getSbDatachegada()));
            tableSobreLocal.addCell(cell);

            //Hora de chegada
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Hora de chegada dos policiais: "));
            tableSobreLocal.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getSbHoraChegada()));
            tableSobreLocal.addCell(cell);

            //Condicoes do local
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Condição do local: "));
            tableSobreLocal.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getSbCondicoesLocal()));
            tableSobreLocal.addCell(cell);

            //Infos adicionais
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Informações adicionais: "));
            tableSobreLocal.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getSbInfo()));
            tableSobreLocal.addCell(cell);

            Paragraph pSobreLocal = new Paragraph();
            pSobreLocal.add(sobreLocalTitle);
            pSobreLocal.add(tableSobreLocal);

            document.add(pSobreLocal);

            document.add(new Chunk(" "));
            //
            //          Sobre o local
            //


            Chunk sobreFatoTitle = new Chunk("Sorbe o fato", chapterFont);

            PdfPTable tableSobreFato = new PdfPTable(2);
            tableSobreFato.setTotalWidth(200);
            tableSobreFato.setWidths(new int[]{35, 65});
            tableSobreFato.setHorizontalAlignment(Element.ALIGN_LEFT);

            //Data provavel
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Data provável: "));
            tableSobreFato.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getSfDataProvavel()));
            tableSobreFato.addCell(cell);

            //Hora provavel
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Hora provável: "));
            tableSobreFato.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getSfHoraProvavel()));
            tableSobreFato.addCell(cell);

            //Tipos de delito
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Delitos: "));
            tableSobreFato.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            List listDelitos = new List(List.UNORDERED);
            for (String s : CarregarOcorrencia.listaDelitos)
                listDelitos.add(new ListItem(new Chunk(s)));
            if (!CarregarOcorrencia.outroTipodeDelito.equals(""))
                listDelitos.add(new ListItem(new Chunk(CarregarOcorrencia.outroTipodeDelito)));
            cell.addElement(listDelitos);
            tableSobreFato.addCell(cell);

            //Possíveis suspeitos
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Possíveis suspeitos: "));
            tableSobreFato.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getPossiveisSuspeitos()));
            tableSobreFato.addCell(cell);

            //Valores subtraidos
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Valores subtraídos: "));
            tableSobreFato.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getValoresSubtraidos()));
            tableSobreFato.addCell(cell);

            Paragraph pSobreFato = new Paragraph();
            pSobreFato.add(sobreFatoTitle);
            pSobreFato.add(tableSobreFato);

            document.add(pSobreFato);

            document.add(new Chunk(" "));
            //
            //          Vestigios
            //


            Chunk vestigiosTitle = new Chunk("Vestigios", chapterFont);

            PdfPTable tableVestigios = new PdfPTable(2);
            tableVestigios.setTotalWidth(200);
            tableVestigios.setWidths(new int[]{35, 65});
            tableVestigios.setHorizontalAlignment(Element.ALIGN_LEFT);

            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("ETIQUETA"));
            tableVestigios.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("INFORMAÇÕES"));
            tableVestigios.addCell(cell);

            //Vestigios
            JSONArray vestigios = StaticProperties.getJsonArrayVestigios();
            for (int i = 0; i < vestigios.length(); i++) {
                JSONObject vestigio = vestigios.getJSONObject(i);
                cell = new PdfPCell();
                cell.setBorder(PdfPCell.TOP);
                cell.addElement(new Paragraph(vestigio.getString("etiqueta")));
                tableVestigios.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(PdfPCell.TOP);

                List listVestigios = new List(List.UNORDERED);
                if (vestigio.getBoolean("coletado"))
                    listVestigios.add(new ListItem(new Chunk("Coletado: sim")));
                else
                    listVestigios.add(new ListItem(new Chunk("Coletado: não")));

                listVestigios.add(new ListItem(new Chunk("Tipo: " + vestigio.getJSONObject("tipo").getString("tipoVestigio") +
                        " - " + vestigio.getJSONObject("tipo").getString("nomeVestigio"))));

                listVestigios.add(new ListItem(new Chunk("Informações adicionais: " + vestigio.getString("informacoesAdicionais"))));

                cell.addElement(listVestigios);
                tableVestigios.addCell(cell);
            }
            Paragraph pVestigios = new Paragraph();
            pVestigios.add(vestigiosTitle);
            pVestigios.add(tableVestigios);

            document.add(pVestigios);


            document.add(new Chunk(" "));
            //
            //          Testemunha
            //


            Chunk testemunhaTitle = new Chunk("Testemunha", chapterFont);

            PdfPTable tableTestemunha = new PdfPTable(2);
            tableTestemunha.setTotalWidth(200);
            tableTestemunha.setWidths(new int[]{35, 65});
            tableTestemunha.setHorizontalAlignment(Element.ALIGN_LEFT);

            //Nome
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Nome: "));
            tableTestemunha.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getTestNome()));
            tableTestemunha.addCell(cell);

            //RG/CPF
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("RG/CPF: "));
            tableTestemunha.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getTestDoc()));
            tableTestemunha.addCell(cell);

            //Funcao
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Função: "));
            tableTestemunha.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getTestFuncao()));
            tableTestemunha.addCell(cell);

            //Entrevista
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph("Entrevista: "));
            tableTestemunha.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.addElement(new Paragraph(CarregarOcorrencia.getTestEntrevista()));
            tableTestemunha.addCell(cell);

            Paragraph pTestemunha = new Paragraph();
            pTestemunha.add(testemunhaTitle);
            pTestemunha.add(tableTestemunha);

            document.add(pTestemunha);

            Toast.makeText(this, "Arquivo '" + nomeArq + "' gerado na pasta 'Download", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Sem permissão",
                    Toast.LENGTH_LONG).show();
        }
        document.close();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
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


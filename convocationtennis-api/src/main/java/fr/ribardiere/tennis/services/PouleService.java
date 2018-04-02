package fr.ribardiere.tennis.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.ribardiere.tennis.bean.Convocation;
import fr.ribardiere.tennis.bean.Equipe;
import fr.ribardiere.tennis.bean.Rencontre;

public class PouleService {

    private static final String URL_EQUIPES = "http://www.gs.applipub-fft.fr/fftfr/poule.do?dispatch=load&pou_iid=";
    private static final String URL_RENCONTRES = "http://www.gs.applipub-fft.fr/fftfr/pouleRencontres.do?dispatch=load&pou_iid=";
    private static final String URL_RENCONTRES_PAGE1 = "http://www.gs.applipub-fft.fr/fftfr/sort.do?layoutCollection=0&layoutCollectionProperty=&layoutCollectionState=0&pagerPage=1";
    private static final String URL_RENCONTRES_PAGE2 = "http://www.gs.applipub-fft.fr/fftfr/sort.do?layoutCollection=0&layoutCollectionProperty=&layoutCollectionState=1&pagerPage=2";

    // URL en /poule/{id}
    public List<Equipe> consulterEquipe(int idPoule) {
        List<Equipe> result = new ArrayList<>();

        String reponseStr = "";
        
        // Appel http
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            RequestConfig config = RequestConfig.custom()./* setProxy(Main.proxy). */build();

            HttpGet get = new HttpGet(URL_EQUIPES + idPoule);
            get.setConfig(config);

            CloseableHttpResponse response = httpClient.execute(get);

            reponseStr = EntityUtils.toString(response.getEntity());

        } catch (IOException ioex) {
            throw new RuntimeException(
                    "Erreur lors de l'appel pour la récupération des divisions/poules du championnat", ioex);
        }

        // Parsing de la réponse
        Document doc = Jsoup.parse(reponseStr);
        Elements elems = doc.select("#tabs0tab0 table tbody tr td table tbody tr td table tbody tr td table tbody tr");
        for (Element elem : elems) {
            Elements elems2 = elem.select("td");
            if (!elems2.isEmpty()) { // Permet de ne pas lire la ligne d'entête qui ne contient que des th
                // Récupération de l'ID de l'équipe
                Element img = elems2.get(0).select("img").get(0);
                String onclick = img.attr("onclick");
                int index1 = onclick.indexOf('\'');
                int index2 = onclick.indexOf('\'', index1 + 2);
                int idEquipe = Integer.parseInt(onclick.substring(index1 + 1, index2));

                // Récupération du nom de l'équipe
                String nomEquipe = elems2.get(1).ownText().trim();

                Equipe equipe = new Equipe(idEquipe);
                equipe.setNom(nomEquipe);
                result.add(equipe);
            }
        }

        return result;
    }

    // URL en /poule/{id}/equipe/{id} (c'est moche d'avoir 2 ID dans l'URL je sais)
    public List<Integer> consulterCalendrier(int idPoule, int idEquipe) {
        List<Integer> result = new ArrayList<>();
        List<Rencontre> rencontres = consulterRencontres(idPoule);
        for (Rencontre rencontre : rencontres) {
            if (rencontre.getEquipeDomicile().getId() == idEquipe) {
                result.add((Integer) rencontre.getJournee()); // TODO : passer en Java 9 :-)
            }
        }
        return result;
    }

    private List<Rencontre> consulterRencontres(int idPoule) {

        List<Rencontre> rencontres = new ArrayList<>();

        // Appel http
        BasicCookieStore basicCookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(basicCookieStore).build();

        RequestConfig config = RequestConfig.custom()./* setProxy(Main.proxy). */build();

        HttpGet get1 = new HttpGet(URL_RENCONTRES + idPoule);
        get1.setConfig(config);
        HttpGet get2 = new HttpGet(URL_RENCONTRES_PAGE1);
        get2.setConfig(config);
        HttpGet get3 = new HttpGet(URL_RENCONTRES_PAGE2);
        get3.setConfig(config);

        String reponse1Str = "";
        String reponse2Str = "";
        String reponse3Str = "";

        try {
            CloseableHttpResponse response1 = httpClient.execute(get1);
            reponse1Str = EntityUtils.toString(response1.getEntity());
            CloseableHttpResponse response2 = httpClient.execute(get2);
            reponse2Str = EntityUtils.toString(response2.getEntity());
            CloseableHttpResponse response3 = httpClient.execute(get3);
            reponse3Str = EntityUtils.toString(response3.getEntity());
        } catch (IOException ioex) {
            throw new RuntimeException(
                    "Erreur lors de l'appel pour la récupération des divisions/poules du championnat", ioex);
        }

        // Parsing de la réponse
        rencontres.addAll(recupererRencontresDansPage(reponse1Str, 0));
        rencontres.addAll(recupererRencontresDansPage(reponse2Str, 1));
        rencontres.addAll(recupererRencontresDansPage(reponse3Str, 2));
        return rencontres;
    }

    private List<Rencontre> recupererRencontresDansPage(String reponse, int page) {
        List<Rencontre> rencontres = new ArrayList<>();
        Document doc = Jsoup.parse(reponse);
        Elements trs = doc.select("#tabs" + page
                + "tab2>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
        for (int i = 1; i < trs.size(); i++) { // On bypass le 1er élément correspondant au header de la table
            Elements tds = null;
            if (i % 2 == 0) {
                tds = trs.get(i).select("td.L1");
            } else {
                tds = trs.get(i).select("td.L2");
            }
            String hrefDom = tds.get(2).select("a").get(0).attr("href");
            int index1 = hrefDom.indexOf('(');
            int index2 = hrefDom.indexOf(')');
            Equipe equipeDomicile = new Equipe(Integer.parseInt(hrefDom.substring(index1 + 1, index2)));
            String hrefExt = tds.get(3).select("a").get(0).attr("href");
            index1 = hrefExt.indexOf('(');
            index2 = hrefExt.indexOf(')', index1 + 2);
            Equipe equipeExterieur = new Equipe(Integer.parseInt(hrefExt.substring(index1 + 1, index2)));
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            String dateStr = tds.get(4).html();
            if (dateStr.contains("b")) { // On vire le bold en cas de report
                dateStr = dateStr.substring(4, 12);
            }
            Date date = null;
            try {
                date = formatter.parse(dateStr);
            } catch (ParseException pex) {
                throw new RuntimeException("Impossible de parser la date " + dateStr, pex);
            }
            int journee = Integer.parseInt(tds.get(5).ownText());

            Rencontre rencontre = new Rencontre();
            rencontre.setEquipeDomicile(equipeDomicile);
            rencontre.setEquipeExterieur(equipeExterieur);
            rencontre.setDate(date);
            rencontre.setJournee(journee);

            rencontres.add(rencontre);
        }

        return rencontres;

    }

    // URL en /poule/{id}/equipe/{id}/journee/{numero}
    /**
     * 
     * @param idPoule
     * @param idEquipe
     * @param journee
     * @param dateReelle
     *            la date réelle de la rencontre (jour + heure de début)
     * @param isReport
     * @param motifReport
     * @return
     */
    public Convocation recupererDonneesConvocation(int idPoule, int idEquipe, int journee, Date dateReelle,
            boolean isReport, String motifReport) {

        List<Rencontre> rencontres = consulterRencontres(idPoule);
        Rencontre rencontre = null;
        for (Rencontre rencontreTmp : rencontres) {
            if ((rencontreTmp.getEquipeDomicile().getId() == idEquipe) && (rencontreTmp.getJournee() == journee)) {
                rencontre = rencontreTmp;
            }
        }

        Convocation convocation = new Convocation();

        convocation.setDateEnvoi(new Date());

        if (isReport) {
            convocation.setEnvoiDixJoursAvantRencontre(false);
            convocation.setReportUnMoisAvantRencontre(true);
        } else {
            convocation.setEnvoiDixJoursAvantRencontre(true);
            convocation.setReportUnMoisAvantRencontre(false);
        }

        // TODO

        return convocation;
    }

}

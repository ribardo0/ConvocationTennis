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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ribardiere.tennis.bean.Convocation;
import fr.ribardiere.tennis.bean.Equipe;
import fr.ribardiere.tennis.bean.Poule;
import fr.ribardiere.tennis.bean.Rencontre;

@RestController
public class PouleService {

    private static final String URL_POULE = "http://www.gs.applipub-fft.fr/fftfr/poule.do?dispatch=load&pou_iid=";
    private static final String URL_RENCONTRES = "http://www.gs.applipub-fft.fr/fftfr/pouleRencontres.do?dispatch=load&pou_iid=";
    private static final String URL_RENCONTRES_PAGE1 = "http://www.gs.applipub-fft.fr/fftfr/sort.do?layoutCollection=0&layoutCollectionProperty=&layoutCollectionState=0&pagerPage=1";
    private static final String URL_RENCONTRES_PAGE2 = "http://www.gs.applipub-fft.fr/fftfr/sort.do?layoutCollection=0&layoutCollectionProperty=&layoutCollectionState=1&pagerPage=2";

    // URL en /poule/{id}
    @RequestMapping("/poule/{idPoule}")
    public Poule consulterPoule(@PathVariable int idPoule) {
        Poule result = new Poule(idPoule);

        String reponseStr = "";
        
        // Appel http
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            RequestConfig config = RequestConfig.custom()./* setProxy(Main.proxy). */build();

            HttpGet get = new HttpGet(URL_POULE + idPoule);
            get.setConfig(config);

            CloseableHttpResponse response = httpClient.execute(get);

            reponseStr = EntityUtils.toString(response.getEntity());

        } catch (IOException ioex) {
            throw new RuntimeException(
                    "Erreur lors de l'appel pour la r�cup�ration des divisions/poules du championnat", ioex);
        }

        // Parsing de la r�ponse
        Document doc = Jsoup.parse(reponseStr);
        // Recup�ration du nom du championnat
        Elements elems = doc.select("center form table tbody tr td table tbody tr td table tbody tr td b");
        result.setCategorieChampionnat(elems.get(0).ownText().trim());
        result.setNomChampionnat(elems.get(1).ownText().trim());
        elems = doc.select("center form table tbody tr td table tbody tr td table tbody tr td b nobr");
        result.setNomDivision(elems.get(0).ownText().trim());
        elems = doc.select("table.form2 tbody tr td table tbody tr td select option");
        for (Element elem : elems) {
            if (elem.attr("value").equals(Integer.toString(idPoule))) {
                result.setLettrePoule(elem.ownText().trim());
            }
        }
        
        // Recuperation des �quipes
        List<Equipe> equipes = new ArrayList<>();
        
        elems = doc.select("#tabs0tab0 table tbody tr td table tbody tr td table tbody tr td table tbody tr");
        for (Element elem : elems) {
            Elements elems2 = elem.select("td");
            if (!elems2.isEmpty()) { // Permet de ne pas lire la ligne d'ent�te qui ne contient que des th
                // R�cup�ration de l'ID de l'�quipe
                Element img = elems2.get(0).select("img").get(0);
                String onclick = img.attr("onclick");
                int index1 = onclick.indexOf('\'');
                int index2 = onclick.indexOf('\'', index1 + 2);
                int idEquipe = Integer.parseInt(onclick.substring(index1 + 1, index2));

                // R�cup�ration du nom de l'�quipe
                String nomEquipe = elems2.get(1).ownText().trim();

                Equipe equipe = new Equipe(idEquipe);
                equipe.setNom(nomEquipe);
                equipes.add(equipe);
            }
        }
        
        result.setEquipes(equipes);

        return result;
    }
  
    // URL en /poule/{id}/equipe/{id} (c'est moche d'avoir 2 ID dans l'URL je sais)
    @RequestMapping("/poule/{idPoule}/equipe/{idEquipe}")
    public List<Integer> consulterCalendrier(@PathVariable int idPoule, @PathVariable int idEquipe) {
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
                    "Erreur lors de l'appel pour la r�cup�ration des divisions/poules du championnat", ioex);
        }

        // Parsing de la r�ponse
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
        for (int i = 1; i < trs.size(); i++) { // On bypass le 1er �l�ment correspondant au header de la table
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
     *            la date r�elle de la rencontre (jour + heure de d�but)
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

        Poule poule = consulterPoule(idPoule);
        if (poule.getCategorieChampionnat().contains("Homme")) {
            if (poule.getCategorieChampionnat().contains("75")) {
                convocation.setSeniorPlus75Messieurs(true);
            } else if (poule.getCategorieChampionnat().contains("65")) {
                if (poule.getNomChampionnat().contains("Ete")) {
                    convocation.setSeniorPlusEte65Messieurs(true);
                } else {
                    convocation.setSeniorPlus65Messieurs(true);
                }
            } else if (poule.getCategorieChampionnat().contains("55")) {
                if (poule.getNomChampionnat().contains("Ete")) {
                    convocation.setSeniorPlusEte55Messieurs(true);
                } else {
                    convocation.setSeniorPlus55Messieurs(true);
                }
            } else if (poule.getCategorieChampionnat().contains("45")) {
                if (poule.getNomChampionnat().contains("Ete")) {
                    convocation.setSeniorPlusEte45Messieurs(true);
                } else {
                    convocation.setSeniorPlus45Messieurs(true);
                }
            } else if (poule.getCategorieChampionnat().contains("35")) {
                if (poule.getNomChampionnat().contains("Ete")) {
                    convocation.setSeniorPlusEte35Messieurs(true);
                } else {
                    convocation.setSeniorPlus35Messieurs(true);
                }
            } else {
                if (poule.getNomChampionnat().contains("Ete")) {
                    convocation.setSeniorEteMessieurs(true);
                } else {
                    convocation.setSeniorHiverMessieurs(true);
                }
            }
        } else if (poule.getCategorieChampionnat().contains("Femme")) {
            if (poule.getCategorieChampionnat().contains("35")) {
                if (poule.getNomChampionnat().contains("ETE")) {
                    convocation.setSeniorPlusEte35Dames(true);
                } else {
                    convocation.setSeniorPlus35Dames(true);
                }
            } else {
                if (poule.getNomChampionnat().contains("COUPE")) {
                    convocation.setCoupeHiverDames(true);
                } else if (poule.getNomChampionnat().contains("ETE")) {
                    convocation.setSeniorEteDames(true);
                } else {
                    convocation.setSeniorHiverDames(true);
                }
            }
        } else {
            convocation.setSeniorEteMixte(true);
        }
        convocation.setDivision(poule.getNomDivision());
        convocation.setPoule(poule.getLettrePoule());
        convocation.setJournee(journee);
        // TODO

        return convocation;
    }

}

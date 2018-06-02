package fr.ribardiere.tennis.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ribardiere.tennis.bean.Championnat;
import fr.ribardiere.tennis.bean.Division;
import fr.ribardiere.tennis.bean.Poule;

@RestController
public class ChampionnatService {

    private static final String URLFORMULAIRE = "http://www.gs.applipub-fft.fr/fftfr/championnat.do";
    private static final String URLCHAMPIONNAT = "http://www.gs.applipub-fft.fr/fftfr/championnatPoules.do?dispatch=load&hoi_iid=";
    private static final String URLPOULE_START = "http://www.gs.applipub-fft.fr/fftfr/treeview.do?open=0*";
    private static final String URLPOULE_END = "*1&bundle=org.apache.struts.action.MESSAGE&name=listePoules&class=treeview1";

    /**
     * R�cup�re la liste des championnats (ID + nom) par rapport aux crit�res en
     * entr�e URL en /championnat/
     * 
     * @param niveau
     * @param annee
     * @param sexe
     * @param categorie
     * @param departement
     * @return
     */
    @RequestMapping("/championnat")
    public List<Championnat> rechercherChampionnat(@RequestParam String niveau, @RequestParam String annee, @RequestParam String sexe, @RequestParam String categorie,
            @RequestParam String departement) {
        String reponseChampionnats = postChampionnat(niveau, annee, sexe, categorie, departement);
        return recupererChampionnats(reponseChampionnats);
    }

    private String postChampionnat(String niveau, String annee, String sexe, String categorie, String departement) {
        HttpPost post = new HttpPost(URLFORMULAIRE);

        String result = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            RequestConfig config = RequestConfig.custom()./* setProxy(Main.proxy). */build();

            post.setConfig(config);

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("dispatch", "rechercher"));

            urlParameters.add(new BasicNameValuePair("niv_hierarchique", niveau));
            urlParameters.add(new BasicNameValuePair("mil_sisport", annee));
            urlParameters.add(new BasicNameValuePair("hoi_asexe", sexe));
            urlParameters.add(new BasicNameValuePair("cah_icod", categorie));
            urlParameters.add(new BasicNameValuePair("cod_cno", departement));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            CloseableHttpResponse response = httpClient.execute(post);

            try {
                result = EntityUtils.toString(response.getEntity());
            } finally {
                response.close();
            }
        } catch (IOException ioex) {
            throw new RuntimeException("Erreur lors de l'appel pour la r�cup�ration de l'identifiant du championnat",
                    ioex);
        }

        return result;
    }

    private List<Championnat> recupererChampionnats(String reponseChampionnats) {
        List<Championnat> result = new ArrayList<>();
        if (!reponseChampionnats.contains("openChampionnat")) {
            return result;
        } else {
            Document doc = Jsoup.parse(reponseChampionnats);
            Elements elems = doc.select("#tabs0tab1 tr td table tr td table tr td table tr");
            if (elems.size() > 1) {
                for (int i = 1; i < elems.size(); i++) { // On ignore la
                                                         // premi�re ligne de
                                                         // header
                    Element elem = elems.get(i);
                    Elements elems2 = elem.select("td");

                    Element a = elems2.get(0).select("a").get(0);
                    String href = a.attr("href");
                    int premierIndex = href.indexOf('\'');
                    int deuxiemeIndex = href.indexOf('\'', premierIndex + 1);
                    int troisiemeIndex = href.indexOf('\'', deuxiemeIndex + 1);
                    int quatriemeIndex = href.indexOf('\'', troisiemeIndex + 1);
                    int id = Integer.parseInt(href.substring(troisiemeIndex + 1, quatriemeIndex));

                    String nom = elems2.get(1).ownText().trim();

                    Championnat championnat = new Championnat(id);
                    championnat.setNom(nom);

                    result.add(championnat);
                }
            }
        }
        return result;
    }

    // URL en /championnat/{id}
    @RequestMapping("/championnat/{idChampionnat}")
    public List<Division> consulterDivisions(@PathVariable int idChampionnat) {
        List<Division> result = new ArrayList<>();

        HttpGet get = new HttpGet(URLCHAMPIONNAT + idChampionnat);

        CloseableHttpResponse response = null;
        Document doc = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            RequestConfig config = RequestConfig.custom()./* setProxy(Main.proxy). */build();

            get.setConfig(config);

            response = httpClient.execute(get);
            
            doc = Jsoup.parse(EntityUtils.toString(response.getEntity()));
        } catch (IOException ioex) {
            throw new RuntimeException(
                    "Erreur lors de l'appel pour la r�cup�ration des divisions/poules du championnat", ioex);
        }
        
        Elements elems = doc.select("table tbody tr td div font");
        for (Element elem : elems) {
            String divisionNom = elem.ownText().replace("\u00a0", ""); // Supprime les &nbsp; de la chaine
            Division division = new Division(elems.indexOf(elem));
            division.setNom(divisionNom);
            result.add(division);
        }
        return result;
    }

    // URL en /championnat/{id}/division/{numeroDivision}
    @RequestMapping("/championnat/{idChampionnat}/division/{idDivision}")
    public List<Poule> consulterPoules(@PathVariable int idChampionnat, @PathVariable int idDivision) {
        List<Poule> poules = new ArrayList<>();

        // Appels http
        BasicCookieStore basicCookieStore = new BasicCookieStore();
        HttpGet get1 = new HttpGet(URLCHAMPIONNAT + idChampionnat);
        HttpGet get2 = new HttpGet(URLPOULE_START + idDivision + URLPOULE_END);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(basicCookieStore).build();

        RequestConfig config = RequestConfig.custom()./* setProxy(Main.proxy). */build();

        get1.setConfig(config);
        get2.setConfig(config);

        String result = "";

        try {
            httpClient.execute(get1); // Permet de setter le cookie qui sert � se souvenir de l'ID de championnat (la
                                      // fftest statefull)
            CloseableHttpResponse response = httpClient.execute(get2);

            result = EntityUtils.toString(response.getEntity());

        } catch (IOException ioex) {
            throw new RuntimeException(
                    "Erreur lors de l'appel pour la r�cup�ration des divisions/poules du championnat", ioex);
        }

        // Parsing de la r�ponse (javascript donc pas via JSOUP)
        int i = 0;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        while (result.contains("openPoule")) {
            int index = result.indexOf("openPoule");
            result = result.substring(index + 3);
            int index1 = result.indexOf('\'');
            int index2 = result.indexOf('\'', index1 + 1);
            int idPoule = Integer.parseInt(result.substring(index1 + 1, index2));
            Poule poule = new Poule(idPoule);
            poule.setNom("POULE " + alphabet.charAt(i));
            poules.add(poule);
            i++;
        }
        return poules;
    }

}

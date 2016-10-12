package webParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SignificationFinder {

    private static HashMap<String, String> significations = new HashMap<String, String>();
    private static String signification = new String();

    public static HashMap<String, String> getSignifications(ArrayList<String> content, String url) throws IOException {
        int nbNameFound = 0;

        if ("http://www.signification-prenom.com/prenom/prenom-".equals(url)) {
            nbNameFound = getFromSignif(content, url);
        } else if ("http://www.prenoms.com/prenom/signification-prenom-".equals(url)) {
            nbNameFound = getFromPren(content, url);
        }
        System.out.println("NOMBRE == " + nbNameFound);
        // System.out.println(significations);
        return significations;
    }

    public static String getSignification(String name, String url) throws IOException {
        int nbNameFound = 0;

        if ("http://www.signification-prenom.com/prenom/prenom-".equals(url)) {
            nbNameFound = getOneFromSignif(name, url);
        } else if ("http://www.prenoms.com/prenom/signification-prenom-".equals(url)) {
            nbNameFound = getOneFromPren(name, url);
        }
        // System.out.println(url + " NOMBRE == " + nbNameFound);
        // System.out.println(significations);
        return signification;
    }

    private static int getOneFromSignif(String name, String url) {
        int i = 0;

        if ("'abbas".equals(name) || "'abla".equals(name))
            return 0;
        String signif = "";
        String link = url + name + ".html";
        Document doc;
        try {
            doc = Jsoup.connect(link).get();
            Elements divs = doc.select("h1+p");
            if (divs.size() > 0) {
                signif = divs.get(0).toString();
                if (!"".equals(signif.replaceAll(" ", "")) && signif.length() > 10) {
                    signif = signif.substring(33, signif.length() - 4);
                    signif = StringEscapeUtils.unescapeHtml(signif);
                    if (!"".equals(signif))
                        i++;
                    // System.out.println(name + ": " + signif);
                }
            }
            signification = signif;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + name);
        } catch (HttpStatusException e) {
            System.out.println("HttpStatusException: " + name);
        } catch (IOException e) {
            System.out.println("IOException: " + name);
        }

        return i;
    }

    private static int getOneFromPren(String name, String url) {
        int i = 0;

        String signif = "";
        String tmp;

        String link = url + name + ".html";
        Document doc;
        try {
            doc = Jsoup.connect(link).get();
            Elements divs = doc.select("#etymologie+p");

            if (divs.size() > 0) {
                i++;
                signif += divs.get(0).toString();
                signif = signif.substring(3, signif.length() - 4);
                signif = StringEscapeUtils.unescapeHtml(signif);
                // System.out.println(name + ": " + signif);

                signification = signif;
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + name);
        } catch (HttpStatusException e) {
            System.out.println("HttpStatusException: " + name);
        } catch (IOException e) {
            System.out.println("IOException: " + name);
        }
        return i;
    }

    private static int getFromPren(ArrayList<String> content, String url) throws IOException {
        int i = 0;

        for (String name : content) {
            if (getOneFromPren(name, url) != 0)
                i++;

            significations.put(name, signification);
        }
        return i;
    }

    private static int getFromSignif(ArrayList<String> content, String url) throws IOException {
        int i = 0;

        for (String name : content) {

            if ("'abbas".equals(name) || "'abla".equals(name))
                continue;

            if (getOneFromSignif(name, url) != 0)
                i++;

            significations.put(name, signification);
        }

        return i;
    }
}

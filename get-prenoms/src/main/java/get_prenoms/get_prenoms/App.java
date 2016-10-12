package get_prenoms.get_prenoms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import csvMaster.csvFactory;
import webParser.SignificationFinder;

public class App {

    private static String prenoms = "http://www.prenoms.com/prenom/signification-prenom-";
    private static String significationPrenom = "http://www.signification-prenom.com/prenom/prenom-";
    private static HashMap<String, String> significations = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        System.out.println(new Date());

        ArrayList<String> content = csvFactory.getCSVPrenoms("prenoms/prenoms.csv", true);

        for (String name : content) {
            String signification = SignificationFinder.getSignification(name, significationPrenom);
            if ("".equals(signification))
                signification = SignificationFinder.getSignification(name, prenoms);
            significations.put(name, signification);
        }

        csvFactory.setCSVPrenoms("prenoms/presmallResult.csv", significations);
        System.out.println(new Date());
    }
}

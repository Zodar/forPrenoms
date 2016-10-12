package csvMaster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class csvFactory {

    private static BufferedReader br;
    private static FileWriter fr;

    public static ArrayList<String> getCSVPrenoms(String path, Boolean hasHeader) throws IOException {
        ArrayList<String> result = new ArrayList<String>();
        String line;
        boolean first = true;

        br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            String[] name = line.split(";");
            if (hasHeader && first) {
                first = false;
            } else {
                result.add(name[0]);
            }
        }
        return result;
    }

    public static void setCSVPrenoms(String path, HashMap<String, String> significations) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("jedis.txt"), "UTF-8"));
        // OutputStreamWriter chars_into_his_stdin = new OutputStreamWriter(path, /* DO NOT OMIT! */ Charset.forName("UTF-8").newEncoder());
        fr = new FileWriter(path);
        for (Map.Entry<String, String> entry : significations.entrySet()) {
            fr.append(entry.getKey());
            fr.append(';');
            fr.append(entry.getValue());
            fr.append(';');

            fr.append("\n");
        }
        fr.flush();
        fr.close();

    }
}

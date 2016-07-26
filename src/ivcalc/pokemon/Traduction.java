package ivcalc.pokemon;

import jdk.nashorn.api.scripting.URLReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Traduction {
    private static Map<String, String> traduction;

    public static String toFr(String name){
        return traduction.get(name);
    }

    public static void load(URL url) {
        traduction = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new URLReader(url, "ISO-8859-1"));
            String line = reader.readLine();
            while (line != null) {
                String[] params = line.split(";");
                traduction.put(params[0], params[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

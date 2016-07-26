package ivcalc.pokemon.data;

import jdk.nashorn.api.scripting.URLReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Translation {
    private static Map<String, String> translation;

    public static String toFr(String name){
        return translation.get(name);
    }

    public static void load(URL url) {
        translation = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new URLReader(url, "ISO-8859-1"));
            String line = reader.readLine();
            while (line != null) {
                String[] params = line.split(";");
                translation.put(params[0], params[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void load(){
        load(Translation.class.getResource("entofr.csv"));
    }
}

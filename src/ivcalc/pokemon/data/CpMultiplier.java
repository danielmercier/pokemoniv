package ivcalc.pokemon.data;

import jdk.nashorn.api.scripting.URLReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CpMultiplier {
    private static Map<Double, Double> cpMultiplierByLevel;
    public static double getCpMultiplier(double l){
        return cpMultiplierByLevel.get(l);
    }

    public static void load(URL url){
        try {
            BufferedReader reader = new BufferedReader(new URLReader(url));
            String line = reader.readLine();
            cpMultiplierByLevel = new HashMap<>();
            while(line != null){
                String[] params = line.split(";");
                cpMultiplierByLevel.put(Double.parseDouble(params[0]),
                        Double.parseDouble(params[1]));
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
        load(CpMultiplier.class.getResource("cpmultiplierbylevel.csv"));
    }
}

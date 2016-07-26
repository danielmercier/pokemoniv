package ivcalc.util;

import ivcalc.pokemon.data.Species;
import ivcalc.userdata.PokeCollection;
import ivcalc.userdata.Pokemon;

import java.io.*;

public class Util {
    public static double clamp(double min, double x, double max){
        return Math.min(Math.max(min, x), max);
    }

    public static int round(double x, double places){
        return (int)(Math.round(x * Math.pow(10, places)) / Math.pow(10, places));
    }

    public static void updateIvCalcData() {
        if(PokeCollection.dataFile.exists()){
            try {
                PokeCollection collection = new PokeCollection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PokeCollection.dataFile), "UTF-8"));
                String line = reader.readLine();
                if(line != null && line.split(";").length == 4){
                    while(line != null) {
                        String[] params = line.split(";");
                        String name = params[0];
                        Integer cp = Integer.parseInt(params[1]);
                        Integer hp = Integer.parseInt(params[2]);
                        Integer dust = Integer.parseInt(params[3]);
                        Pokemon p = new Pokemon(Species.getByName(name), cp, hp, dust);
                        line = reader.readLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

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
                PokeCollection collection = new PokeCollection(false);
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PokeCollection.dataFile), "UTF-8"));
                String line = reader.readLine();
                if(line != null){
                    String params[] = line.split(";");
                    if(params.length == 5) {
                        while (line != null) {
                            params = line.split(";");
                            String name = params[0];
                            Integer cp = Integer.parseInt(params[1]);
                            Integer hp = Integer.parseInt(params[2]);
                            Integer dust = Integer.parseInt(params[3]);
                            Pokemon p = new Pokemon(Species.getByName(name), cp, hp, dust);
                            System.out.println(p);
                            collection.add(p);
                            line = reader.readLine();
                        }
                        reader.close();
                        PokeCollection.dataFile.delete();
                        collection.save();
                    } else {
                        reader.close();
                    }
                } else {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

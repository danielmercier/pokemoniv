package ivcalc.pokemon.data;

import javafx.util.Pair;
import jdk.nashorn.api.scripting.URLReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LevelsByStardust {
    public static Pair<Integer, Integer>[] levelsByStardustAncient;
    public static Integer[] levelsByStardust;
    public static Map<Integer, Integer> levelsByStardustMap;
    public static void load(URL url){
        try {
            BufferedReader reader = new BufferedReader(new URLReader(url));
            String line = reader.readLine();
            List<Pair<Integer, Integer>> pairList = new LinkedList<>();
            List<Integer> intList = new LinkedList<>();
            levelsByStardustMap = new HashMap<>();
            int i = 0;
            while(line != null){
                String[] params = line.split(";");
                pairList.add(new Pair<>(Integer.parseInt(params[0]),Integer.parseInt(params[1])));
                intList.add(Integer.parseInt(params[1]));
                levelsByStardustMap.put(Integer.parseInt(params[0]),i++);
                line = reader.readLine();
            }
            reader.close();
            levelsByStardustAncient = pairList.toArray(new Pair[pairList.size()]);
            levelsByStardust = intList.toArray(new Integer[intList.size()]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Pair<Double, Double> getLevelMinMax(Integer dust){
        Integer i = levelsByStardustMap.get(dust);
        return new Pair<>((double) levelsByStardust[i], (double) levelsByStardust[i+1] - 0.5);
    }
    public static void load(){
        load(LevelsByStardust.class.getResource("levelsbydust.csv"));
    }
}

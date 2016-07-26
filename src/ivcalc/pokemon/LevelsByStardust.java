package ivcalc.pokemon;

import javafx.util.Pair;
import jdk.nashorn.api.scripting.URLReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class LevelsByStardust {
    public static Pair<Integer, Integer>[] levelsByStardust;
    public static void load(URL url){
        try {
            BufferedReader reader = new BufferedReader(new URLReader(url));
            String line = reader.readLine();
            List<Pair<Integer, Integer>> pairList = new LinkedList<>();
            while(line != null){
                String[] params = line.split(";");
                pairList.add(new Pair<>(Integer.parseInt(params[0]),Integer.parseInt(params[1])));
                line = reader.readLine();
            }
            reader.close();
            levelsByStardust = pairList.toArray(new Pair[pairList.size()]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

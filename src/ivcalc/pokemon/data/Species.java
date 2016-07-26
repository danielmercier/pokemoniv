package ivcalc.pokemon.data;

import jdk.nashorn.api.scripting.URLReader;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Species implements Serializable {
    private static final long serialVersionUID = 95320648038204232L;
    public static Species[] allSpecies;
    private int id;
    private int att;
    private int def;
    private int sta;
    private String name;

    private Species(int id, String name, int att, int def, int sta){
        this.name = name;
        this.id = id;
        this.att = att;
        this.def = def;
        this.sta = sta;
    }

    public int getAtt() {
        return att;
    }

    public int getDef() {
        return def;
    }

    public int getSta() {
        return sta;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public static void load(URL url){
        try {
            BufferedReader reader = new BufferedReader(new URLReader(url));
            String line = reader.readLine();
            List<Species> speciesList = new LinkedList<>();
            int i = 0;
            while(line != null){
                String[] params = line.split(";");
                String name = Translation.toFr(params[0]);
                if(name == null){
                    System.err.println("No trad found for " + params[0]);
                    name = params[0];
                }
                speciesList.add(new Species(i++, name,
                        Integer.parseInt(params[1]),
                        Integer.parseInt(params[2]),
                        Integer.parseInt(params[3])));
                line = reader.readLine();
            }
            reader.close();
            allSpecies = speciesList.toArray(new Species[speciesList.size()]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void load(){
        load(Species.class.getResource("species.csv"));
    }

    @Override
    public String toString(){
        return getName();
    }

    public static Species getByName(String name){
        for(Species species : allSpecies){
            if(species.getName().equals(name)){
                return species;
            }
        }
        return null;
    }
}

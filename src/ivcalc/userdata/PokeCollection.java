package ivcalc.userdata;

import java.io.*;
import java.util.ArrayList;

public class PokeCollection extends ArrayList<Pokemon> {
    public static final String prefix = System.getProperty("user.home");
    public static final String name = ".ivcalcdata.db";
    public static final File dataFile = new File(prefix + File.separator + name);
    private static final long serialVersionUID = -617103552990359907L;

    public PokeCollection(boolean load){
        if(load){
            load();
        }
    }
    public PokeCollection(){
        this(true);
    }
    public void createFileIfNotExists() throws IOException {
        if(!dataFile.exists()){
            dataFile.createNewFile();
        }
    }
    public void save(){
        try {
            createFileIfNotExists();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile));
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void load(){
        try {
            createFileIfNotExists();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile));
            this.addAll((ArrayList<Pokemon>)ois.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package ivcalc.userdata;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Data {
    private String name;
    private int cp;
    private int hp;
    private int dust;
    private double percent;

    public Data(String name, int cp, int hp, int dust, double percent){
        this.name = name;
        this.cp = cp;
        this.hp = hp;
        this.dust = dust;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public int getCp() {
        return cp;
    }

    public int getHp() {
        return hp;
    }

    public int getDust() {
        return dust;
    }

    public double getPercent() {
        return percent;
    }

    public static void save(Collection<Data> data){
        try{
            String home = System.getProperty("user.home");
            File f = new File(home + File.separator + ".ivcalcdata.db");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Data d : data){
                writer.write(d.getName() + ";" +
                        d.getCp() + ";" +
                        d.getHp() + ";" +
                        d.getDust() + ";" +
                        d.getPercent() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Data> load(){
        String home = System.getProperty("user.home");
        File f = new File(home + File.separator + ".ivcalcdata.db");
        List<Data> dataList = new LinkedList<>();
        if(f != null && f.exists()){
            try{
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String line = reader.readLine();
                while(line != null){
                    String params[] = line.split(";");
                    dataList.add(new Data(params[0],
                            Integer.parseInt(params[1]),
                            Integer.parseInt(params[2]),
                            Integer.parseInt(params[3]),
                            Double.parseDouble(params[4])));
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dataList;
    }
}

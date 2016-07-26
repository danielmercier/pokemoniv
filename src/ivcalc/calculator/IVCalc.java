package ivcalc.calculator;

import ivcalc.pokemon.Iv;
import ivcalc.pokemon.data.CpMultiplier;
import ivcalc.pokemon.data.LevelsByStardust;
import ivcalc.pokemon.data.Species;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class IVCalc implements Serializable {
    private static final long serialVersionUID = -1233532349705217167L;
    private transient Iv IVs;
    private transient Species species;
    private List<Iv> possibleIvs;

    public IVCalc(Species species, int cp, int hp, int dustPrice, boolean poweredUp){
        possibleIvs = new LinkedList<>();
        IVs = new Iv();
        this.species = species;
        Pair<Double, Double> levelMinMax = LevelsByStardust.getLevelMinMax(dustPrice);
        double levelMin = levelMinMax.getKey();
        double levelMax = levelMinMax.getValue();

        //Searching level
        for(double lvl = levelMin ; lvl <= levelMax ; lvl += (poweredUp ? 0.5 : 1.)){
            double cpm = CpMultiplier.getCpMultiplier(lvl);

            //Searching IVs
            for(IVs.STA = 0 ; IVs.STA < 16 ; IVs.STA ++) {
                int staHp = getHp(cpm);
                if(staHp == hp) {
                    for (IVs.ATT = 0 ; IVs.ATT < 16 ; IVs.ATT++) {
                        for(IVs.DEF = 0 ; IVs.DEF < 16 ; IVs.DEF++) {
                            int calcCp = getCP(cpm);
                            if(calcCp == cp) {
                                possibleIvs.add(new Iv(IVs));
                            }
                        }
                    }
                }
            }
        }
    }

    public List<Iv> getPossibleIvs(){
        return possibleIvs;
    }

    public IVCalc(Species species, int cp, int hp, int dustPrice){
        this(species, cp, hp, dustPrice, false);
    }

    private int getDef(int cp, double cpm){
        double base_def = species.getDef();
        double base_att = species.getAtt();
        double base_sta = species.getSta();
        return (int)Math.floor(Math.pow((double)cp / ((base_att + IVs.ATT) *
                Math.pow(base_sta + IVs.STA, 0.5) *
                Math.pow(cpm, 2) / 10.), 2) - base_def);
    }

    private int getCP(double cpm){
        return (int)Math.floor((species.getAtt() + IVs.ATT) *
                Math.pow(species.getDef() + IVs.DEF, 0.5) *
                (Math.pow(species.getSta() + IVs.STA, 0.5) * Math.pow(cpm, 2)) / 10);
    }

    private int getHp(double cpm){
        return (int)Math.floor((species.getSta() + IVs.STA) * cpm);
    }
}

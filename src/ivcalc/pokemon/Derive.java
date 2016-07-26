package ivcalc.pokemon;

import java.util.LinkedList;
import java.util.List;

class Param {
    public double level;
    public double STA;
    public double ATT;
    public double DEF;

    public Param(double level, double STA, double ATT, double DEF){
        this.level = level;
        this.STA = STA;
        this.ATT = ATT;
        this.DEF = DEF;
    }
}

public class Derive {
    private List<Param> valids = new LinkedList<>();
    private List<Param> all = new LinkedList<>();

    public Derive(Species species, int hp, int cp, int dustPrice){
        Pokemon p = new Pokemon(species, hp, cp, dustPrice, null);


        for ( int l = (int)p.levelMin; l <= p.levelMax; l += 1 ) {
            boolean valid = p.setLevel( l );

            Param out = new Param( p.level, p.IVs.STA, p.IVs.ATT, p.IVs.DEF );

            p.IVs.STA = -1;
            p.IVs.ATT = -1;
            p.IVs.DEF = -1;
            if ( valid ) {
                valids.add( out );
            }
            all.add( out );
        }
    }

    public double percent() throws Error{
        double percent = -1;
        if(valids.size() == 1){
            percent = (valids.get(0).STA + valids.get(0).ATT + valids.get(0).DEF) / 45.;
        } else if (valids.isEmpty()){
            throw new Error("Pas de possibilité trouvée");
        } else {
            throw new Error("Plusieurs possibilités trouvées");
        }
        return percent;
    }
}

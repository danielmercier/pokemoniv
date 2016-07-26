package ivcalc.pokemon;

import java.io.Serializable;

public class Iv implements Serializable {
    public double STA;
    public double ATT;
    public double DEF;

    public Iv(){
        this(-1, -1, -1);
    }

    public Iv(double STA, double ATT, double DEF) {
        this.STA = STA;
        this.ATT = ATT;
        this.DEF = DEF;
    }

    public Iv(Iv IVs) {
        this(IVs.STA, IVs.ATT, IVs.DEF);
    }

    public double getPerfect() {
        return (STA + ATT + DEF) / 45.;
    }
}

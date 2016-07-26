package ivcalc.userdata;

import ivcalc.calculator.IVCalc;
import ivcalc.pokemon.Iv;
import ivcalc.pokemon.data.Species;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Pokemon implements Serializable {
    private static final long serialVersionUID = 7200631044639683118L;
    public Species species;
    private int cp;
    private int hp;
    private int dust;
    private IVCalc calc;

    public Pokemon(Species species, int cp, int hp, int dust, boolean poweredUp){
        this.species = species;
        this.cp = cp;
        this.hp = hp;
        this.dust = dust;
        calc = new IVCalc(species, cp, hp, dust, poweredUp);
    }

    public Pokemon(Species species, int cp, int hp, int dust){
        this(species, cp, hp, dust, false);
    }

    public String getName() {
        return species.getName();
    }

    public Species getSpecies(){
        return species;
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

    public double getMinPerfect(){
        return this.getPossibleIvs().stream().mapToDouble(ivs -> ivs.getPerfect()).min().getAsDouble();
    }

    public double getMaxPerfect(){
        return this.getPossibleIvs().stream().mapToDouble(ivs -> ivs.getPerfect()).max().getAsDouble();
    }

    public double getAvgPerfect() {
        return this.getPossibleIvs().stream().mapToDouble(ivs -> ivs.getPerfect()).average().getAsDouble();
    }

    public List<Iv> getPossibleIvs() {
        return calc.getPossibleIvs();
    }

    @Override
    public String toString(){
        return species.toString() + " " + getCp() + " " + getHp() + " " + getDust() + getPossibleIvs();
    }
}

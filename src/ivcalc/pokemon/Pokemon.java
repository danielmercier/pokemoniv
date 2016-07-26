package ivcalc.pokemon;

import ivcalc.util.Util;

class Iv{
    public double STA;
    public double ATT;
    public double DEF;
}

public class Pokemon {
    public int hp;
    public int cp;
    public int dustPrice;
    public double level;
    public int STA;
    public int ATT;
    public int DEF;
    public Iv IVs;
    public Species species;
    public double levelMin;
    public double levelMax;

    public Pokemon(Species species, int hp, int cp, int dustPrice, Double level){
        this.species = species;
        this.hp = hp;
        this.cp = cp;
        this.dustPrice = dustPrice;
        this.STA = this.species.getSta();
        this.ATT = this.species.getAtt();
        this.DEF = this.species.getDef();
        this.IVs = new Iv();

        if(level != null){
            this.setLevel(level);
        } else {
            for (int i = 0; i < LevelsByStardust.levelsByStardust.length; i++ ) {
                if ( dustPrice == LevelsByStardust.levelsByStardust[i].getKey() ) {
                    this.levelMin = LevelsByStardust.levelsByStardust[i].getValue();
                    this.levelMax = LevelsByStardust.levelsByStardust[i+1].getValue() - 0.5;

                    break;
                }
            }
        }
    }

    public boolean setLevel(double lvl){
        this.level = lvl;

        int minHP = this.getHp( 0 );
        int maxHP = this.getHp( 15 );

        if ( this.hp < this.getHp( 0 ) || this.hp > this.getHp( 15 ) ) {
            return false;
        }

        double min = Util.clamp( 0, ( this.hp / this.getCpMultiplier() ) - this.STA, 15 );
        double max = Util.clamp( 0, ( ( this.hp + 1.0 ) / this.getCpMultiplier() ) - this.STA, 15 );
        this.IVs.STA = ( min + max ) / 2;

        if ( this.cp < this.getCp( min, 0., 0. ) || this.cp > this.getCp( max, 15., 15. ) ) {
            return false;
        }

        Integer bestDistance = Integer.MAX_VALUE;
        Double bestIV = 0.;

        // calculate ATT and DEF IVs, assuming ATT = DEF
        for ( double x = -0.05; x <= 15.05; x += 0.05 ) {
            double cp = this.getCp( null, x, x );

            if ( Math.abs( this.cp - cp ) < bestDistance ) {
                bestDistance = (int)Math.abs( this.cp - cp );
                bestIV = x;
            }
        }

        this.IVs.ATT = bestIV;
        this.IVs.DEF = bestIV;

        return true;
    }

    public double compositeSTA( Double iv ) {
        iv = iv == null ? this.IVs.STA : iv;
        return ( this.STA + iv ) * this.getCpMultiplier();
    }

    public double compositeATT( Double iv ) {
        iv = iv == null ? this.IVs.ATT : iv;
        return ( this.ATT + iv ) * this.getCpMultiplier();
    }

    public double compositeDEF( Double iv ) {
        iv = iv == null ? this.IVs.DEF : iv;
        return ( this.DEF + iv ) * this.getCpMultiplier();
    }

    public int getCp( Double sta, Double att, Double def ) {
        return (int)(Math.floor( 0.1 * Math.pow( this.compositeSTA( sta ), 0.5 ) * this.compositeATT( att ) * Math.pow( this.compositeDEF( (double) def ), 0.5 ) ));
    }

    public int getHp( int sta ) {
        return (int)(Math.floor( this.compositeSTA( (double) sta ) ));
    }

    public double getCpMultiplier() {
        return CpMultiplier.getCpMultiplier( this.level );
    }
}

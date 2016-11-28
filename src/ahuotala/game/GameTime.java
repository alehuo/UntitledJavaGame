/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

/**
 * GameTime class
 * @author Aleksi Huotala
 */
public class GameTime implements Tickable {

    /**
     * Game time
     */
    private int gametime = 0;

    /**
     * Returns the gametime value
     *
     * @return Gametime (0-2359)
     */
    public int getGametime() {
        return gametime;
    }

    /**
     * Sets the gametime value
     *
     * @param gametime Game time between 0 - 2359
     */
    public void setGametime(int gametime) {
        this.gametime = gametime;
    }

    @Override
    public void tick() {
        if (gametime < 2359) {
            gametime++;
        } else {
            gametime = 0;
        }
    }

    /**
     * Returns the current brightness factor for use with the DarkenFilter
     *
     * @return Brightness factor
     */
    public double getFactor() {

        double factor = 1.0;

        if (gametime >= 900 && gametime <= 1200) {
            //9:00 - 12:00 = 1.0
            factor = 1.0;
        } else if (gametime >= 1201 && gametime <= 1600) {
            //12:01 - 16:00 = 1.0 -> 0.7
            factor = 1.9030075187969908 - 0.00075187969924811915 * gametime;
        } else if (gametime >= 1601 && gametime <= 2359) {
            //16:01 - 23:59 = 0.7 -> 0.2
            factor = 1.7560686015831133 - 0.00065963060686015829 * gametime;
        } else if (gametime >= 0 && gametime <= 500) {
            //00:00 - 05:00 = 0.2 -> 0.3
            factor = 0.20000000000000001 + 0.00020000000000000001 * gametime;
        } else if (gametime >= 501 && gametime <= 899) {
            //05:01 - 09:00 = 0.3 -> 1.0
            factor = -0.57894736842105254 + 0.0017543859649122805 * gametime;
        }

        return factor;
    }
}

/*
 * Copyright 2017 alehuo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alehuo.untitledjavagame;

/**
 * GameTime class
 * @author alehuo
 */
public class GameTime implements Tickable {

    /**
     * Game time
     */
    private int time = 0;
    
    /**
     * Returns the time value
     *
     * @return Gametime (0-2359)
     */
    public int getTime() {
        return time;
    }

    /**
     * Sets the time value
     *
     * @param time Game time between 0 - 2359
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     *
     */
    @Override
    public void tick() {
        if (time < 2359) {
            time++;
        } else {
            time = 0;
        }
    }

    /**
     * Returns the current brightness factor for use with the DarkenFilter
     *
     * @return Brightness factor
     */
    public double getFactor() {

        double factor = 1.0;

        if (time >= 900 && time <= 1200) {
            //9:00 - 12:00 = 1.0
            factor = 1.0;
        } else if (time >= 1201 && time <= 1600) {
            //12:01 - 16:00 = 1.0 -> 0.7
            factor = 1.9030075187969908 - 0.00075187969924811915 * time;
        } else if (time >= 1601 && time <= 2359) {
            //16:01 - 23:59 = 0.7 -> 0.2
            factor = 1.7560686015831133 - 0.00065963060686015829 * time;
        } else if (time >= 0 && time <= 500) {
            //00:00 - 05:00 = 0.2 -> 0.3
            factor = 0.20000000000000001 + 0.00020000000000000001 * time;
        } else if (time >= 501 && time <= 899) {
            //05:01 - 09:00 = 0.3 -> 1.0
            factor = -0.57894736842105254 + 0.0017543859649122805 * time;
        }

        return factor;
    }
}

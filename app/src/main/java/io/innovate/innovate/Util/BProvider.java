package io.innovate.innovate.Util;

import com.squareup.otto.Bus;

/**
 * Created by NottyNerd on 06/02/2017.
 */
public class BProvider {
    public static Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BProvider(){}

}

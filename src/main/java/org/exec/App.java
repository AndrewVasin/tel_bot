package org.exec;

import java.io.IOException;

/**
 * @author Andrew
 */
public class App {

    public static final String BOT_TOKEN = ;
    public static final long CHAT_ID = ;

    // public static final String DATE = "11/08/2022";

    public static void main( String[] args ) throws IOException {

        JamesWebbSpaceTelescope jamesWebbSpaceTelescope = new JamesWebbSpaceTelescope(BOT_TOKEN, CHAT_ID);
        jamesWebbSpaceTelescope.run();

        /*
        CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate(BOT_TOKEN, CHAT_ID);
        currencyExchangeRate.run();
        */

        /*Nasa nasa = new Nasa(BOT_TOKEN, CHAT_ID);
        nasa.run();
        */

        /*
        ITunes iTunes = new ITunes(BOT_TOKEN, CHAT_ID);
        iTunes.run();
        */

        /*
        WakeUp newWakeUpBot = new WakeUp(BOT_TOKEN, CHAT_ID,20, 3, "wake up, fast!!!");
        newWakeUpBot.run();
        */
    }
}

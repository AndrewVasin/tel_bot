package org.exec;

/**
 * Курс валют ЦБР API - https://www.cbr.ru/scripts/XML_daily.asp?date_req=
 * формат date_req = DD.MM.YYYY
 * Отдает XML, для обработки используется Jsoup
 */

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Setter
@Getter
public class CurrencyExchangeRate {
    private String token;
    private long chatId;

    public CurrencyExchangeRate(String token, long chatId) {
        setToken(token);
        setChatId(chatId);
    }

    public void run() {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    String incomeMessage = upd.message().text(); // date = DD.MM.YYYY
                    String date = incomeMessage;
                    Document doc = Jsoup.connect("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date).get();
                    System.out.println(doc.title());
                    Elements valutes = doc.select("Valute");
                    for (Element valute : valutes) {
                        String result = valute.select("Name").text() + " " + valute.select("Value").text();
                        SendMessage request = new SendMessage(chatId, result);
                        bot.execute(request);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}

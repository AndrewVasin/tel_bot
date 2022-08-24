package org.exec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;

@Getter
@Setter
public class Nasa {
    private String token;
    private long chatId;

    public Nasa(String token, long chatId) {
        setToken(token);
        setChatId(chatId);
    }

    public void run() {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    String incomeMessage = upd.message().text();
                    // logic
                    String date = incomeMessage; // date format 2010-12-25 (YYYY-MM-DD)
                    String jsonString = Jsoup.connect("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=" + date)
                            .ignoreContentType(true)
                            .execute()
                            .body();
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(jsonString);
                    String imageUrl = jsonNode.get("url").asText();
                    String explanation = jsonNode.get("explanation").asText();
                    String result = imageUrl + "\n" + explanation;
                    // send response
                    SendMessage request = new SendMessage(chatId, result);
                    bot.execute(request);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}

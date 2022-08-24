package org.exec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendVideo;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;

@Getter
@Setter
public class ITunes {
    private String token;
    private long chatId;

    public ITunes(String token, long chatId) {
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
                    String movieName = incomeMessage;
                    String jsonString = Jsoup.connect("https://itunes.apple.com/search?media=movie&term=" + movieName)
                            .ignoreContentType(true)
                            .execute()
                            .body();
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(jsonString);
                    String result = jsonNode.get("results").get(0).get("previewUrl").asText();
                    SendVideo request = new SendVideo(chatId, result);
                    bot.execute(request);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}

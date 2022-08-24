package org.exec;

/**
 * James Webb Space Telescope API - https://jwstapi.com
 * API отдает json, для обработки используется Jackson
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;

import java.io.IOException;

@Setter
@Getter
public class JamesWebbSpaceTelescope {
    private String token;
    private long chatId;

    public JamesWebbSpaceTelescope(String token, long chatId) {
        setToken(token);
        setChatId(chatId);
    }

    public void run() throws IOException {
        TelegramBot bot = new TelegramBot(token);

        String jsonString = Jsoup.connect("https://api.jwstapi.com/all/type/jpg?page=3")
                .ignoreContentType(true)
                .header("X-API-KEY", "8c2e1285-1e40-4081-aecf-4f2c559e4ef1")
                .maxBodySize(0) // Java Jsoup - FIX for exception com.fasterxml.jackson.core.io.JsonEOFException: Unexpected
                                     // end-of-input in field name at
                .execute()
                .body();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        String result = jsonNode.get("body").get(0).get("location").asText();

        SendMessage request = new SendMessage(chatId, result);
        bot.execute(request);

    }
}
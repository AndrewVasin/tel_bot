package org.exec;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WakeUp {
    private String token;
    private long chatId;
    private String message;
    private int hour;
    private int minute;

    public WakeUp(String token, long chatId, int hour, int minute, String message) {
        setToken(token);
        setHour(hour);
        setMinute(minute);
        setMessage(message);
        setChatId(chatId);
    }

    public void run() throws InterruptedException {
        while (true) {
            TelegramBot bot = new TelegramBot(token);
            Thread.sleep(1000 * 60);
            int currentHour = LocalDateTime.now().getHour();
            int currentMinute = LocalDateTime.now().getMinute();
            if (currentHour == hour && currentMinute == minute) {
                SendMessage request = new SendMessage(chatId, message);
                bot.execute(request);
            }
        }
    }
}

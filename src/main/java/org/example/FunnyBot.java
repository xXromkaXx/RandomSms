package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;
import java.util.Random;

public class FunnyBot extends TelegramLongPollingBot {

        private final String BOT_USERNAME = ("randomSMS");
        private final String BOT_TOKEN = ("8014919412:AAFHfmhqeT3u4gbD1Q5h1hHvhaMYd0Sg3IM");
    private final Random random = new Random();

    private final List<String> responses = List.of(
            "Я тут, щоб тролити вас! 😆",
            "Ти впевнений, що хочеш це знати? 🤔",
            "Навіщо мені відповідати, якщо ти сам знаєш відповідь? 😏",
            "Гугл в допомогу, друже! 😂",
            "Якщо довго дивитись на це повідомлення, можна побачити сенс життя... або ні.",
            "лох просто"
    );

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            // Бот відповідає в групі, якщо його тегнули або з ймовірністю 50%
            if (update.getMessage().isGroupMessage()) {
                if (messageText.contains("@" + getBotUsername()) || random.nextInt(100) < 70) {
                    String randomResponse = responses.get(random.nextInt(responses.size()));
                    sendTextMessage(chatId, randomResponse);
                }
            } else { // В особистих чатах відповідає завжди
                String randomResponse = responses.get(random.nextInt(responses.size()));
                sendTextMessage(chatId, randomResponse);
            }
        }
    }


    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FunnyBot());
            System.out.println("Бот запущено!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
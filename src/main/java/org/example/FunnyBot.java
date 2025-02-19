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

    private final String BOT_USERNAME = System.getenv("BOT_USERNAME");
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    //System.getenv

    private final Random random = new Random();

    private final List<String> responses = List.of(
            "Я тут, щоб тролити вас! 😆",
            "Ти впевнений, що хочеш це знати? 🤔",
            "Навіщо мені відповідати, якщо ти сам знаєш відповідь? 😏",
            "Гугл в допомогу, друже! 😂",
            "Якщо довго дивитись на це повідомлення, можна побачити сенс життя... або ні.",
            "Лох просто 🤡",
            "Знаєш, я б зробив це, але мені ліньки. 😅",
            "Ти серйозно зараз? 🤨",
            "Хто це дозволив? 😂",
            "Але я ж просто хотів бути нормальним... 😆",
            "Так, це я, і що з того? 😎",
            "Не знаю, як ви, але я намагаюсь вижити. 🫣",
            "Не біда, просто життя. 😜",
            "Ну що, поїхали? 🤙",
            "Я ще не вирішив, чи це погано, чи дуже погано. 😏",
            "Це просто шикарно! 🔥",
            "Йди нафіг 😆",
            "Коли не знаєш, що сказати, просто напиши '😎'.",
            "Це повідомлення самознищиться через... ой, вже пізно. 💥",
            "Просто роби вигляд, що це норма.",
            "Ти впевнений, що хочеш цього? 🤔",
            "Вітаю, ви виграли… нічого! 🎉",
            "Люди кажуть, що я розумний, але я просто випадково відповідаю. 🤓",
            "Сьогодні гарний день, щоб нічого не робити. 😌",
            "Не перевіряй мене, я просто бот. 🤖",
            "Бувають моменти, коли навіть я не знаю, що сказати. Це один з них. 😶",
            "А що, так можна було? 😳",
            "Ти питаєш мене? Серйозно? 😅",
            "Скучав за мною? 😏",
            "Якщо ти це читаєш, значить я працюю! 🏆",
            "Ніколи не здавайся! Ну, хіба що дуже ліньки. 😜",
            "Я тут тільки заради фану. А ти?",
            "Давай зробимо вигляд, що це важливе повідомлення. 📢",
            "Слухай, а може не треба? 😆",
            "Заспокойся, я просто бот, я нічого не вирішую! 🤷‍♂️",
            "А можна мені теж щось спитати?",
            "В житті кожного настає момент, коли треба просто забити. Можливо, це він. 🤔",
            "Я відповідаю випадково, тому не ображайся! 😅",
            "Я тут для того, щоб підняти настрій… або зіпсувати, як пощастить. 🎭",
            "Ні, ну це вже перебір! 😂",
            "Твоя проблема – не моя проблема. Але мені цікаво. 😏",
            "Тримайся, друже! Я вірю в тебе! 💪",
            "Здається, я потрапив у неправильну компанію. 🤔",
            "Ой, а хто це тут у нас такий розумний? 😆",
            "Коротше, все буде добре. Ну, або не буде. 🤷‍♂️",
            "Хмм... цікаво, а якщо я просто промовчу? 😶",
            "Ти точно хочеш продовжувати це обговорення? 🤨",
            "Один бот – добре, а два краще! Але не тут.",
            "Я щойно думав про сенс життя, і тут ти пишеш мені. Співпадіння? 🤔",
            "Ти втомився? А я ні! 😂",
            "Я не знаю, що сказати, тому просто відповім – 'Привіт!' 👋",
            "Тут могло бути твоє оголошення, але я бот. 😆",
            "Немає дурних питань, є тільки я – бот, який відповідає на них. 🤖",
            "Якщо ти це читаєш, значить у нас щось спільне – ми в цій групі. 😆"
    );


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
// Бот відповідає в групі тільки іноді
            if (update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat()) {
                if (messageText.contains("@" + getBotUsername())) {
                    // Якщо тегнули – відповідає завжди
                    sendTextMessage(chatId, responses.get(random.nextInt(responses.size())));
                } else if (random.nextInt(100) < 30) { // 30% шанс відповіді
                    sendTextMessage(chatId, responses.get(random.nextInt(responses.size())));
                }
            } else { // В особистих чатах відповідає завжди
                sendTextMessage(chatId, responses.get(random.nextInt(responses.size())));
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

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
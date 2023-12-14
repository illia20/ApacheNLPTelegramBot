import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotApi = new TelegramBotsApi();
        Bot bot = new Bot();

        try {
            telegramBotApi.registerBot(bot);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
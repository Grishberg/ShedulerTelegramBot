package main;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import services.ContactService;
import services.ContactServiceImpl;
import services.DbService;
import updateshandlers.TelegramHandler;

/**
 * Created by grishberg on 06.09.16.
 */
// Example taken from https://github.com/rubenlagus/TelegramBotsExample
public class Main {
    public static void main(String[] args) {
        final DbService dbService = null;
        final ContactService contactService = new ContactServiceImpl(dbService);
        TelegramBotsApi telegramBotsApi = createLongPollingTelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramHandler(contactService));
            //telegramBotsApi.registerBot(new ChannelHandlers());
            //telegramBotsApi.registerBot(new DirectionsHandlers());
            //telegramBotsApi.registerBot(new RaeHandlers());
            //telegramBotsApi.registerBot(new WeatherHandlers());
            //telegramBotsApi.registerBot(new TransifexHandlers());
            //telegramBotsApi.registerBot(new FilesHandlers());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static TelegramBotsApi createLongPollingTelegramBotsApi() {
        return new TelegramBotsApi();
    }
}

package updateshandlers;

import config.BotConfig;
import content.ContentGenerator;
import content.LetterContentGenerator;
import content.parts.GreetingsGenerator;
import content.parts.GreetingsGeneratorImpl;
import content.parts.IntroduceGenerator;
import content.parts.IntroduceGeneratorImpl;
import data.models.Contact;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import services.ContactService;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик запросов Telegram бота
 */
public class TelegramHandler extends TelegramLongPollingBot {
    public static final String START = "/start";
    public static final String STOP = "/stop";
    private final GreetingsGenerator greetingsGenerator = new GreetingsGeneratorImpl();
    private final IntroduceGenerator introduceGenerator = new IntroduceGeneratorImpl();
    private final ContactService contactService;

    public TelegramHandler(final ContactService contactService) {
        super();
        this.contactService = contactService;
    }

    @Override
    public String getBotUsername() {
        // TODO Auto-generated method stub
        return BotConfig.USERNAMEMYPROJECT;
    }

    @Override
    public void onUpdateReceived(Update update) {

        //check if the update has a message
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.getContact() != null) {
                System.out.printf("phoneNumber = %s\n", message.getContact().getPhoneNumber());
            }
            //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
            if (message.hasText()) {
                if (START.equals(message.getText())) {
                    processStart(message);
                    return;
                }

                //create a object that contains the information to send back the message
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
                sendMessageRequest.setText();
                System.out.printf("new message>>%s\n", message.getText());
                try {
                    sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }//end catch()
            }//end if()
        }//end  if()
    }//end onUpdateReceived()

    /**
     * Обработка начала диалога с ботом, добавление в контакты
     *
     * @param message
     */
    private void processStart(final Message message) {
        if (contactService.isExists(message.getFrom().getId())) {
            return;
        }
        contactService.addContact(new Contact(message.getFrom().getId(), message.getChatId()));

        ReplyKeyboardMarkup replyKeyboardMarkup = getUnsubscribeKeyboard();
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
        sendMessageRequest.setText("Подписка оформлена.");
        sendMessageRequest.setReplyMarkup(replyKeyboardMarkup);
        try {
            sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удалить из рассылки
     *
     * @param message
     */
    private void processEnd(final Message message) {
        contactService.removeContact(message.getFrom().getId());
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
        sendMessageRequest.setText("Подписка отклонена.");

        //sendMessageRequest.setReplyMarkup(replyKeyboardMarkup);
        try {
            sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String generateMessage(String text) {
        String[] words = text.split(",");
        if (words.length != 2) {
            return "Нужно ввести: Имя Менеджера, Имя Клиента";
        }
        String hrName = words[0].trim();
        String clientName = words[1].trim();

        ContentGenerator contentGenerator = new LetterContentGenerator(hrName, clientName,
                greetingsGenerator, introduceGenerator);
        return contentGenerator.generateContent();
    }

    @Override
    public String getBotToken() {
        // TODO Auto-generated method stub
        return BotConfig.PROJECT_TOKEN;
    }

    private static ReplyKeyboardMarkup getUnsubscribeKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton button = new KeyboardButton();
        button.setRequestContact(true);
        button.setText("Отписаться");

        keyboardFirstRow.add(button);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup getSubscribeKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton button = new KeyboardButton();
        button.setRequestContact(true);
        button.setText("Подписаться");

        keyboardFirstRow.add(button);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup getPhoneKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton button = new KeyboardButton();
        button.setRequestContact(true);
        button.setText("Отправить номер телефона");

        keyboardFirstRow.add(button);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}

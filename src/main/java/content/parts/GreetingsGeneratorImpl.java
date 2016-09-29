package content.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by grishberg on 06.09.16.
 */
public class GreetingsGeneratorImpl implements GreetingsGenerator {
    private static final Random RANDOM = new Random();
    private static final String HR_NEEDLE = "{%HR%}";
    private static final String CLIENT_NEEDLE = "{%CLIENT%}";
    private final List<String> messages;

    public GreetingsGeneratorImpl() {
        messages = new ArrayList<>(10);
        messages.add("Здарова {%CLIENT%}!!! Че как?\n");
        messages.add("Доброго времени суток, уважаемый {%CLIENT%}! Как Ваше ничего? Может сразу на ТЫ ;) ?\n");
        messages.add("Yo нига,{%CLIENT%}, вазап! Че !?\n");
        messages.add("Стравстфуйтэ, юважаемый {%CLIENT%}, салам всэм туарам!\n");
    }

    @Override
    public String makeGreetings(String hrName, String clientName) {
        int index = RANDOM.nextInt(messages.size());
        String message = messages.get(index);
        message = message.replace(HR_NEEDLE, hrName);
        message = message.replace(CLIENT_NEEDLE, clientName);
        return message;
    }
}

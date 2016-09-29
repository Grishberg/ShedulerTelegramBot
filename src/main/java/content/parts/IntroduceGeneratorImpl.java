package content.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by grishberg on 06.09.16.
 */
public class IntroduceGeneratorImpl implements IntroduceGenerator {
    private static final Random RANDOM = new Random();
    private static final String HR_NEEDLE = "{%HR%}";
    private static final String CLIENT_NEEDLE = "{%CLIENT%}";
    private final List<String> messages;

    public IntroduceGeneratorImpl() {
        messages = new ArrayList<>(10);
        messages.add("Я - {%HR%}!!! Бум знакомы, че лив?\n");
        messages.add("Слющая, я уважаэмый тют чэулауэк, мая софут {%HR%} да.\n");
        messages.add("Твою мать, я - {%HR%} нах, молчи сука, я тут говорю!\n");
        messages.add("Разрешите мне предствиться - я {%HR%}! Ведущий специалист в области херятины)\n");
    }

    @Override
    public String makeIntroduce(String hrName, String clientName) {
        int index = RANDOM.nextInt(messages.size());
        String message = messages.get(index);
        message = message.replace(HR_NEEDLE, hrName);
        message = message.replace(CLIENT_NEEDLE, clientName);
        return message;
    }
}

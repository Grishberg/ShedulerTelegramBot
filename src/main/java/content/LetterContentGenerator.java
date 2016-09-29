package content;

import content.parts.GreetingsGenerator;
import content.parts.IntroduceGenerator;

/**
 * Created by grishberg on 06.09.16.
 */
public class LetterContentGenerator implements ContentGenerator {
    public static final int CAPACITY = 128;
    private final String hrName;
    private final String clientName;
    private final GreetingsGenerator greetingsGenerator;
    private final IntroduceGenerator introduceGenerator;

    public LetterContentGenerator(final String hrName, final String clientName,
                                  final GreetingsGenerator greetingsGenerator,
                                  final IntroduceGenerator introduceGenerator) {
        this.hrName = hrName;
        this.clientName = clientName;
        this.greetingsGenerator = greetingsGenerator;
        this.introduceGenerator = introduceGenerator;
    }

    @Override
    public String generateContent() {
        final StringBuilder sb = new StringBuilder(CAPACITY);
        sb.append(greetingsGenerator.makeGreetings(hrName, clientName));
        sb.append(introduceGenerator.makeIntroduce(hrName, clientName));
        return sb.toString();
    }
}

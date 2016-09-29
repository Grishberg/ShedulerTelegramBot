package data.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by grishberg on 29.09.16.
 */
public class Contact {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private Long chatId;

    public Contact(Integer id, Long chatId) {
        this.id = id;
        this.chatId = chatId;
    }
}

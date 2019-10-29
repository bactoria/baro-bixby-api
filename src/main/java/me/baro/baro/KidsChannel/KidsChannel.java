package me.baro.baro.KidsChannel;/*
package me.baro.baro.youtubeApiKey;

import lombok.ToString;
import me.baro.baro.utils.DateTimeUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

*/


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Bactoria
 * @since 2019-10-28 [2019.10월.28]
 */

@ToString
@Entity
@Getter
@NoArgsConstructor
public class KidsChannel {
    @Id
    private String channelId;

    private String title;
    private String subscriber;
    private Integer subscriberNumber;
    private String imageUrl;

    @Column(columnDefinition = "boolean default false")
    private boolean visible;

    public KidsChannel(String channelId) {
        this.channelId = channelId;
    }

    public void updateInfo(String title, String imageUrl, String subscriber) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.subscriber = subscriber;
        this.subscriberNumber = parseToNumber(subscriber);
        this.visible = true;
    }

    private int parseToNumber(String subscriber) {

        if (subscriber.isEmpty()) {
            return -1;
        }

        if (subscriber.matches("[0-9]+만")) { // 1234만 or 123만 or 12만
            String token = subscriber.split("[^0-9]+")[0]; // [1234] or [123] or [12]
            return Integer.parseInt(token + "0000");
        }

        if (subscriber.matches("[0-9][0-9]\\.[0-9]만")) { // 12.3만
            String[] token = subscriber.split("[^0-9]+"); // [12, 3]
            return Integer.parseInt(token[0] + token[1] + "000");
        }

        if (subscriber.matches("[0-9]\\.[0-9][0-9]만")) { // 1.23만
            String[] token = subscriber.split("[^0-9]+"); // [1, 23]
            return Integer.parseInt(token[0] + token[1] + "00");
        }

        if (subscriber.matches("[0-9]\\.[0-9]만")) { // 1.2만
            String[] token = subscriber.split("[^0-9]+"); // [1, 2]
            return Integer.parseInt(token[0] + token[1] + "000");
        }

        if (subscriber.matches("[0-9]만")) { // 1만
            String[] token = subscriber.split("[^0-9]+"); // [1]
            return Integer.parseInt(token[0] + "0000");
        }

        if (subscriber.matches("[0-9]\\.[0-9][0-9]천")) { // 1.23천
            String[] token = subscriber.split("[^0-9]+"); // [1, 23]
            return Integer.parseInt(token[0] + token[1] + "0");
        }

        if (subscriber.matches("[0-9]\\.[0-9]천")) { // 1.2천
            String[] token = subscriber.split("[^0-9]+"); // [1, 2]
            return Integer.parseInt(token[0] + token[1] + "00");
        }

        if (subscriber.matches("[0-9]천")) { // 1천
            String[] token = subscriber.split("[^0-9]+"); // [1]
            return Integer.parseInt(token[0] + "000");
        }

        return Integer.parseInt(subscriber);
    }
}

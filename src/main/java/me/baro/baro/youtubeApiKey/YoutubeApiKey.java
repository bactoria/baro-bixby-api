package me.baro.baro.youtubeApiKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Bactoria
 * @since 2019-10-28 [2019.10월.28]
 */

@Getter
@ToString
@Entity
@NoArgsConstructor
public class YoutubeApiKey {
    @Id
    private String name;

    private LocalDateTime lastExpiredDateTime;

    public void setExpired(LocalDateTime curDateTime) {
        this.lastExpiredDateTime = curDateTime;
    }

    public String getKeyId() {
        return name;
    }
}

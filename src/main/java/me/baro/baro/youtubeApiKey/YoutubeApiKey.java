package me.baro.baro.youtubeApiKey;

import lombok.*;
import me.baro.baro.utils.DateTimeUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;


import lombok.ToString;
import me.baro.baro.utils.DateTimeUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
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

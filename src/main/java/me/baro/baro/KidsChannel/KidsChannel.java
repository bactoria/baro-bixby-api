package me.baro.baro.KidsChannel;/*
package me.baro.baro.youtubeApiKey;

import lombok.ToString;
import me.baro.baro.utils.DateTimeUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

*/


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Bactoria
 * @since 2019-10-28 [2019.10월.28]
 */


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class KidsChannel {
    @Id
    private String channelId;

    private String name;
}

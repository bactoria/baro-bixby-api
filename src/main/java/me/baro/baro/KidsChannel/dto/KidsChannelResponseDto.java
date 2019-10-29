package me.baro.baro.KidsChannel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.baro.baro.KidsChannel.KidsChannel;

/**
 * @author Bactoria
 * @since 2019-10-30 [2019.10월.30]
 */

@Getter
@NoArgsConstructor
public class KidsChannelResponseDto {
    private String channelId;
    private String title;
    private String subscriber;
    private String imageUrl;

    public KidsChannelResponseDto(KidsChannel kidsChannel) {
        this.channelId = kidsChannel.getChannelId();
        this.title = kidsChannel.getTitle();
        this.subscriber = kidsChannel.getSubscriber();
        this.imageUrl = kidsChannel.getImageUrl();
    }
}

package me.baro.baro.youtubeSearch.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author Bactoria
 * @since 2019-10-25 [2019.10월.25]
 */

@ToString
@Setter @Getter
@NoArgsConstructor
public class SearchResponseDto {
    private String thumbnailUrl;
    private String title;
    private String videoId;

    private transient static final String PREFIX = "vnd.youtube:";

    public SearchResponseDto(String videoId, String title, String thumbnailUrl) {
        this.videoId = PREFIX + videoId;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
    }
}
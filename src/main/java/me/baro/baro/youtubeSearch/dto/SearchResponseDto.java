package me.baro.baro.youtubeSearch.dto;

import lombok.*;

/**
 * @author Bactoria
 * @since 2019-10-25 [2019.10월.25]
 */

@ToString
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDto {
    private String thumbnailUrl;
    private String title;
    private String videoId;
}
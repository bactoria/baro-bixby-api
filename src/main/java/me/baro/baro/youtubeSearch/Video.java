package me.baro.baro.youtubeSearch;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Bactoria
 * @since 2019-10-25 [2019.10월.25]
 */

@Setter
@Getter
@Embeddable
@NoArgsConstructor
@Entity
public class Video {
    @Id @GeneratedValue
    private Long videoIdx;

    private String channelId;
    private String videoId;
    private String title;
    private String thumbnailUrl;

    @Column(columnDefinition = "boolean default false")
    private boolean visited;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "search_id")
    private SearchEntity searchEntity;

    public Video(String videoId, String channelId, String title, String thumbnailUrl) {
        this.videoId = videoId;
        this.channelId = channelId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }
}

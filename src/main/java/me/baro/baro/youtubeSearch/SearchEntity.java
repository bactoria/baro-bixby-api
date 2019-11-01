package me.baro.baro.youtubeSearch;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bactoria
 * @since 2019-10-25 [2019.10월.25]
 */

@Builder @AllArgsConstructor
@EqualsAndHashCode(of="searchId")
@Setter @Getter
@NoArgsConstructor
@Entity
@Table(name="youtube_search_log")
public class SearchEntity {

    @Id @GeneratedValue
    private Long searchId;

    private String userId;

    private String searchData;

    @CreationTimestamp
    private LocalDateTime searchedTime;

    private String usedApiKey;

/*
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="youtube_search_video_log", joinColumns = @JoinColumn(name="search_id"))
*/

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="search_id")
    private List<Video> videos = new ArrayList<>();

}

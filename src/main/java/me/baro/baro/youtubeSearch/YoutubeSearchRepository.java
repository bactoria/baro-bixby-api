package me.baro.baro.youtubeSearch;

import me.baro.baro.youtubeSearch.dto.SearchResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.Optional;

/**
 * @author Bactoria
 * @since 2019-10-25 [2019.10월.25]
 */

public interface YoutubeSearchRepository extends JpaRepository<SearchEntity, Long> {

    Optional<SearchEntity> findFirstByUserIdOrderBySearchIdDesc(String userId);

/*
    //    @Query(value="UPDATE Channel channel SET channel.title=:title, channel.content=:content, channel.image=:image, channel.subscriber=:subscriber, channel.joinDate=:joinDate, channel.views=:views, channel.updatedTime=:updatedTime WHERE channel.id=:id")
    @Query(value="SELECT new me.baro.baro.youtubeSearch.dto.SearchResponseDto(y.thumbnail_url, y.video_id) FROM Video y WHERE video_idx = 1")
    SearchResponseDto findNextVideo();
*/
}

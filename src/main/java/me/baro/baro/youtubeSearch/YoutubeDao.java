package me.baro.baro.youtubeSearch;

import me.baro.baro.youtubeSearch.dto.SearchResponseDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Bactoria
 * @since 2019-10-26 [2019.10월.26]
 */

public interface YoutubeDao {
    Video findVideo(Long searchId);
}

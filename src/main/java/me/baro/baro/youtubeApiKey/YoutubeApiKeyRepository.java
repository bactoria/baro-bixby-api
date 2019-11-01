package me.baro.baro.youtubeApiKey;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Bactoria
 * @since 2019-10-28 [2019.10월.28]
 */



public interface YoutubeApiKeyRepository extends JpaRepository<YoutubeApiKey, String> {
    Optional<YoutubeApiKey> findFirstByOrderByLastExpiredDateTimeAsc();
}

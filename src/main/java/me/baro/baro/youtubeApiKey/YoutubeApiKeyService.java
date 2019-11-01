package me.baro.baro.youtubeApiKey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Bactoria
 * @since 2019-10-28 [2019.10월.28]
 */



@Service
@RequiredArgsConstructor
public class YoutubeApiKeyService {

    private final YoutubeApiKeyRepository repository;

    public YoutubeApiKey fetchNewApiKey() {
        return repository.findFirstByOrderByLastExpiredDateTimeAsc()
                .orElseThrow(() -> new RuntimeException("fetchNewApiKey Exception"));
    }

    public void updateKeyExpiredTime(YoutubeApiKey apiKey) {
        LocalDateTime curDateTime = LocalDateTime.now(ZoneOffset.UTC);
        apiKey.setExpired(curDateTime);
        repository.saveAndFlush(apiKey);
    }
}

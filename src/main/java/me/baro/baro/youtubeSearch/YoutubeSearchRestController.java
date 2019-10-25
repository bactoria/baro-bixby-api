package me.baro.baro.youtubeSearch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.baro.baro.youtubeSearch.dto.SearchResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bactoria
 * @since 2019-10-25 [2019.10월.25]
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class YoutubeSearchRestController {

    private final YoutubeSearchService service;

    @GetMapping("/search")
    public SearchResponseDto asd(String userId, String searchData) {
        log.info("Get:: /search?userId={}&searchData={}", userId, searchData);
        service.searchVideo(userId, searchData);
        return service.fetchNextVideo(userId);
    }

    @GetMapping("/nextVideo")
    public SearchResponseDto nextVideo(String userId) {
        log.info("Get:: /nextVideo?userId={}", userId);
        return service.fetchNextVideo(userId);
    }

}

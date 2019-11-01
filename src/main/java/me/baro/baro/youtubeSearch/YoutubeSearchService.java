package me.baro.baro.youtubeSearch;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.baro.baro.KidsChannel.KidsChannelRepository;
import me.baro.baro.error.ErrorCode;
import me.baro.baro.youtubeApiKey.YoutubeApiKey;
import me.baro.baro.youtubeApiKey.YoutubeApiKeyService;
import me.baro.baro.youtubeSearch.dto.SearchResponseDto;
import me.baro.baro.youtubeSearch.exceptions.NextVideoNotFoundException;
import me.baro.baro.youtubeSearch.exceptions.SearchNotFoundException;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bactoria
 * @since 2019-10-25 [2019.10월.25]
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class YoutubeSearchService {

    private final YoutubeSearchRepository youtubeSearchRepos;
    private final YoutubeDao youtubeDao;
    private final KidsChannelRepository kidsChannelRepository;
    private final YoutubeApiKeyService youtubeApiKeyService;

    private final JsonParser Parser = new JsonParser();

    public void searchVideo(String userId, String searchData) {
        // 최초로 검색하는 거임. 뭘 검색했는지, 누구인지. 이제 여기서 실제로 유튜브 API 이용해서 검색해야 함.

        boolean isFetched = false;
        JsonArray items = null;
        YoutubeApiKey youtubeApiKey = null;
        int count = 0;

        while(!isFetched && count < 5) {
            isFetched = true;
            count++;

            youtubeApiKey = youtubeApiKeyService.fetchNewApiKey();

            log.info("사용 할 키:: key={}", youtubeApiKey.getKeyId());
            String str = search(youtubeApiKey.getKeyId(), searchData);

            try {
                JsonObject jsonObj = (JsonObject) Parser.parse(str);
                items = jsonObj.getAsJsonArray("items");
            } catch(Exception e) {
                isFetched = false;
                log.info("키 에러:: key={}", youtubeApiKey.getKeyId());
                youtubeApiKeyService.updateKeyExpiredTime(youtubeApiKey);
            }
        }

        if (items == null) {
            throw new RuntimeException("키 부족함");
        }

        if (items.size() == 0) {
            throw new SearchNotFoundException(ErrorCode.SEARCH_NOT_FOUND);
        }

        List<Video> videos = parseToVideos(items);
        List<Video> filteredVideos = filterVideo(videos);

        SearchEntity searchEntity = SearchEntity.builder()
                .userId(userId)
                .searchData(searchData)
                .videos(filteredVideos)
                .usedApiKey(youtubeApiKey.getKeyId())
                .build();

        youtubeSearchRepos.save(searchEntity);
    }

    private List<Video> filterVideo(List<Video> videos) {
        return videos.stream()
                .filter(v -> kidsChannelRepository.findById(v.getChannelId()).isPresent())
                .collect(Collectors.toList());
    }

    private List<Video> parseToVideos(JsonArray items) {
        List<Video> videos = new ArrayList<>();

        for (JsonElement item : items) {

            String videoId = item.getAsJsonObject().get("id").getAsJsonObject().get("videoId").getAsString();
            String channelId = item.getAsJsonObject().get("snippet").getAsJsonObject().get("channelId").getAsString();
            String titleEscaped = item.getAsJsonObject().get("snippet").getAsJsonObject().get("title").getAsString();
            String thumbnailUrl = item.getAsJsonObject().get("snippet").getAsJsonObject().get("thumbnails").getAsJsonObject().get("high").getAsJsonObject().get("url").getAsString();

            String title = StringEscapeUtils.unescapeHtml4(titleEscaped);

            videos.add(new Video(videoId, channelId, title, thumbnailUrl));
        }

        return videos;
    }

    private String search(String apiKey, String searchData) {

        StringBuffer response = new StringBuffer();

        try {
            StringBuffer apiurl = new StringBuffer().append("https://www.googleapis.com/youtube/v3/search");
            apiurl.append("?key=").append(apiKey);
            apiurl.append("&q=").append(URLEncoder.encode(searchData, "UTF-8"));

            apiurl.append("&regionCode=KR");
            apiurl.append("&part=snippet");
            apiurl.append("&type=video");
            apiurl.append("&maxResults=20");
            apiurl.append("&safeSearch=strict");

            URL url = new URL(apiurl.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
        } catch (Exception e) {
        }

        return response.toString();
    }

    @Transactional
    public SearchResponseDto fetchNextVideo(String userId) {
        SearchEntity searchEntity = youtubeSearchRepos.findFirstByUserIdOrderBySearchIdDesc(userId)
                .orElseThrow(() -> new NextVideoNotFoundException(ErrorCode.NEXT_VIDEO_NOT_FOUND));

        Video video = youtubeDao.findVideo(searchEntity.getSearchId());
        video.setVisited(true);

        return new SearchResponseDto(video.getVideoId(), video.getTitle(), video.getThumbnailUrl());
    }
}

package me.baro.baro.youtubeSearch;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import me.baro.baro.error.ErrorCode;
import me.baro.baro.youtubeSearch.dto.SearchResponseDto;
import me.baro.baro.youtubeSearch.exceptions.NextVideoNotFoundException;
import me.baro.baro.youtubeSearch.exceptions.SearchNotFoundException;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bactoria
 * @since 2019-10-25 [2019.10월.25]
 */

@Service
@RequiredArgsConstructor
public class YoutubeSearchService {

    private final YoutubeSearchRepository repository;
    private final YoutubeDao repository2;

    @Value("${youtube.key}")
    private String youtubeKey;

    private final JsonParser Parser = new JsonParser();

    public void searchVideo(String userId, String searchData) {
        // 최초로 검색하는 거임. 뭘 검색했는지, 누구인지. 이제 여기서 실제로 유튜브 API 이용해서 검색해야 함.

        String str = search(searchData);
        JsonObject jsonObj = (JsonObject) Parser.parse(str);
        JsonArray items = jsonObj.getAsJsonArray("items");

        if (items.size() == 0) {
            throw new SearchNotFoundException(ErrorCode.SEARCH_NOT_FOUND);
        }

        List<Video> videos = new ArrayList<>();
        for (JsonElement item: items) {

            String thumbnailUrl = item.getAsJsonObject().get("snippet").getAsJsonObject().get("thumbnails").getAsJsonObject().get("high").getAsJsonObject().get("url").getAsString();
            String titleEscaped = item.getAsJsonObject().get("snippet").getAsJsonObject().get("title").getAsString();
            String title = StringEscapeUtils.unescapeHtml4(titleEscaped);
            String videoId = item.getAsJsonObject().get("id").getAsJsonObject().get("videoId").getAsString();

            Video video = new Video(thumbnailUrl, title, videoId);
            videos.add(video);
        }

        SearchEntity searchEntity = SearchEntity.builder()
                .userId(userId)
                .searchData(searchData)
                .videos(videos)
                .build();

        repository.save(searchEntity);
    }

    private String search(String searchData) {

        StringBuffer response = new StringBuffer();

        try {
            StringBuffer apiurl = new StringBuffer().append("https://www.googleapis.com/youtube/v3/search");
            apiurl.append("?key=").append(youtubeKey);
            apiurl.append("&q=").append(URLEncoder.encode(searchData, "UTF-8"));

            apiurl.append("&regionCode=KR");
            apiurl.append("&part=snippet");
            apiurl.append("&type=video");
            apiurl.append("&maxResults=20");
            apiurl.append("&videoCategoryId=15");
            apiurl.append("&videoCategoryId=27");
            apiurl.append("&videoCategoryId=31");
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
        SearchEntity searchEntity = repository.findFirstByUserIdOrderBySearchIdDesc(userId)
                .orElseThrow(() -> new NextVideoNotFoundException(ErrorCode.NEXT_VIDEO_NOT_FOUND));

        Video video = repository2.findVideo(searchEntity.getSearchId());
        video.setVisited(true);

        return new SearchResponseDto(video.getVideoId(), video.getTitle(), video.getThumbnailUrl());
    }
}

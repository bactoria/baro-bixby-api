package me.baro.baro.KidsChannel;

import lombok.RequiredArgsConstructor;
import me.baro.baro.KidsChannel.dto.KidsChannelResponseDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bactoria
 * @since 2019-10-29 [2019.10월.29]
 */

@Component
@RequiredArgsConstructor
public class KidsChannelService {

    private final KidsChannelRepository repository;

    public Page<KidsChannelResponseDto> fetchKidsChannels(Pageable pageable) {
        Page<KidsChannel> page = repository.findAll(pageable);
        Page<KidsChannelResponseDto> dtoPage = page.map(KidsChannelResponseDto::new);
        return dtoPage;
    }

    public void crawlAll() {
        final int START_PAGE_NUM = 1;
        final int END_PAGE_NUM = 10;

        for (int pageNum = START_PAGE_NUM; pageNum <= END_PAGE_NUM; pageNum++) {
            crawl(pageNum);
        }
    }

    public void crawl(int pageNum) {
        Document doc = fetchDocument(pageNum);

        String docStr = doc.toString();

        String[] strs = docStr.split("jQuery.parseJSON\\(JSON.stringify\\(");

        List<KidsChannel> kidsChannels = new ArrayList<>();

        for (String re : strs[2].split("ci\":\"")) {
            String channelId = re.split("\"")[0];
            if (channelId.startsWith("U")) {
                kidsChannels.add(new KidsChannel(channelId));
            }
        }

        repository.saveAll(kidsChannels);
    }

    private Document fetchDocument(int pageNum) {
        Document doc = null;

        try {
            String TARGET_URL = "http://cchart.xyz/ytcollect/YoutubeChannelTrackingServiceV2/kids/" + pageNum;
            doc = Jsoup.connect(TARGET_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    public void updateKidsChannelInfo() {
        List<KidsChannel> kidsChannels = repository.findAllByVisibleIsFalse();

        for (KidsChannel kidsChannel : kidsChannels) {
            String channelId = kidsChannel.getChannelId();
            final String targetUrl = "https://www.youtube.com/channel/" + channelId + "/about";

            try {
                Document doc = Jsoup.connect(targetUrl).header("Accept-language", "ko-KR").get();

                Elements elements = doc.select("meta[property]");

                String title = elements.select("meta[property=og:title]").attr("content");
                String imageUrl = elements.select("meta[property=og:image]").attr("content");
                String subscriber = doc.select("span.subscribed").attr("title");

                kidsChannel.updateInfo(title, imageUrl, subscriber);

                repository.saveAndFlush(kidsChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<KidsChannelResponseDto> fetchKidsChannels() {
        List<KidsChannel> kindChannels = repository.findAllByOrOrderBySubscriberNumberDesc();

        List<KidsChannelResponseDto> result = kindChannels.stream()
                .map(KidsChannelResponseDto::new)
                .collect(Collectors.toList());

        return result;
    }
}

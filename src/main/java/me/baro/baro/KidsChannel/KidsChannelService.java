package me.baro.baro.KidsChannel;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bactoria
 * @since 2019-10-29 [2019.10월.29]
 */

@Component
@RequiredArgsConstructor
public class KidsChannelService {

    private final KidsChannelRepository repository;

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
/*
        List<KidsChannel> kidsChannels = new ArrayList<>();

        for(String channelId : channelIds) {
            final String targetUrl = "https://www.youtube.com/channel/" + channelId + "/about";

            try {
                Document doc = Jsoup.connect(targetUrl).header("Accept-language", "ko-KR").get();

                Elements elements = doc.select("meta[property]");

                String title = elements.select("meta[property=og:title]").attr("content");
                String imageUrl = elements.select("meta[property=og:image]").attr("content");
                String subscriber = doc.select("span.subscribed").attr("title");

                KidsChannel kidsChannel = new KidsChannel(channelId, title, subscriber, imageUrl);
                System.out.println(kidsChannel);
                kidsChannels.add(kidsChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return kidsChannels;
*/
    }
}

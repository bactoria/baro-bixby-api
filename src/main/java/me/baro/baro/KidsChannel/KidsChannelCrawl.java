package me.baro.baro.KidsChannel;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
public class KidsChannelCrawl {

    private final KidsChannelRepository repository;

    public void crawlAll() {
        final int PAGE_MAX_NUM = 10;

        for (int pageNum = 1; pageNum <= PAGE_MAX_NUM; pageNum++) {
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
                kidsChannels.add(new KidsChannel(channelId, null));
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
}

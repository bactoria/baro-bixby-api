package me.baro.baro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bactoria
 * @since 2019-10-29 [2019.10월.29]
 */

@RestController
@RequestMapping("/property")
public class PropertiesRestController {

    @Value("${youtube.guide.videoId}")
    private String guideVideoId;

    @GetMapping("/guideVideoUrl")
    public ResponseEntity<String> fetchGuideVideoId() {
        String body = "{\"videoId\":\""+guideVideoId+"\"}";
        return ResponseEntity.ok().body(body);
    }

}

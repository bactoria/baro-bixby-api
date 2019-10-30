package me.baro.baro;

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

    @GetMapping("/guideVideoUrl")
    public ResponseEntity<String> fetchGuideVideoId() {
        String body = "{\"videoId\":\"eP4ga_fNm-E\"}";
        return ResponseEntity.ok().body(body);
    }

}

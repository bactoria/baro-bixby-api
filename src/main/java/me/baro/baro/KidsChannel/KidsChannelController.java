package me.baro.baro.KidsChannel;

import lombok.RequiredArgsConstructor;
import me.baro.baro.KidsChannel.dto.KidsChannelResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Bactoria
 * @since 2019-10-29 [2019.10월.29]
 */

@RestController
@RequiredArgsConstructor
public class KidsChannelController {
    private static final int PAGE_SIZE = 10;

    private final KidsChannelService service;

    @GetMapping("/kidsChannels/page")
    public ResponseEntity<Page<KidsChannelResponseDto>> fetchKidsChannelPage(@PageableDefault(sort = "subscriberNumber", direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {
        Page<KidsChannelResponseDto> body = service.fetchKidsChannels(pageable);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/kidsChannels")
    public ResponseEntity<List<KidsChannelResponseDto>> fetchKidsChannels() {
        List<KidsChannelResponseDto> body = service.fetchKidsChannels();
        return ResponseEntity.ok().body(body);
    }
}

package me.baro.baro.appRunner;

import lombok.RequiredArgsConstructor;
import me.baro.baro.KidsChannel.KidsChannelService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Bactoria
 * @since 2019-10-29 [2019.10월.29]
 */


// 이 러너는 애플리케이션을 업데이트를 테스트하기 위한 러너이다. 로컬에서만 사용한다.
// @Component
@RequiredArgsConstructor
public class KidsChannelUpdateRunner implements ApplicationRunner {

    private final KidsChannelService kidsChannelService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        kidsChannelService.updateKidsChannelInfo();
    }
}


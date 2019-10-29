package me.baro.baro.KidsChannel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Bactoria
 * @since 2019-10-29 [2019.10월.29]
 */

public interface KidsChannelRepository extends JpaRepository<KidsChannel, String> {
    List<KidsChannel> findAllByVisibleIsFalse();

    @Query("select kc from KidsChannel kc order by kc.subscriberNumber desc")
    List<KidsChannel> findAllByOrOrderBySubscriberNumberDesc();
}

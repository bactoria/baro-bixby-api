package me.baro.baro.youtubeSearch;

import me.baro.baro.youtubeSearch.dto.SearchResponseDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Bactoria
 * @since 2019-10-26 [2019.10월.26]
 */

@Repository
public class YoutubeDaoImpl implements YoutubeDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Video findVideo(Long searchId) {
        String selectQuery =
                "select v "+
                "from Video v join v.searchEntity s " +
                "where s.searchId = :searchId and v.visited=false " +
                "order by v.videoIdx asc"
                ;

        TypedQuery<Video> query = em.createQuery(selectQuery, Video.class);
        query.setMaxResults(1);
        query.setParameter("searchId", searchId);

        Video result = query.getSingleResult();
        return result;
    }
}

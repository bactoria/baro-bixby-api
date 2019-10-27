package me.baro.baro.youtubeSearch.exceptions;

import me.baro.baro.error.BusinessException;
import me.baro.baro.error.ErrorCode;

/**
 * @author Bactoria
 * @since 2019-10-27 [2019.10월.27]
 */

public class SearchNotFoundException extends BusinessException {

    public SearchNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

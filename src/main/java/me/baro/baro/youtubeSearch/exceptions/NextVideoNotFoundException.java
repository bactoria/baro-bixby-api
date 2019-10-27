package me.baro.baro.youtubeSearch.exceptions;

import me.baro.baro.error.BusinessException;
import me.baro.baro.error.ErrorCode;

/**
 * @author Bactoria
 * @since 2019-10-27 [2019.10월.27]
 */

public class NextVideoNotFoundException extends BusinessException {
    public NextVideoNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

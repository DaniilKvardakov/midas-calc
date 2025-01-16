package midas.util;

import lombok.experimental.UtilityClass;
import midas.annotations.Commented;
import midas.dto.MidasResponseDTO;
import midas.models.MidasResponse;
import midas.models.Status;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Утилитарный класс для обработки типовых запросов.
 */
@Commented
@UtilityClass
public final class ResponseUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * Тут просто константы пар статус ошибки + заглушка для "плохого ответа".
     *
     * @see MidasResponse
     */
    public static final Map.Entry<Status, MidasResponse> NO_MIDAS_PROBLEM = new AbstractMap.SimpleEntry<>(Status.NO_MIDAS_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    public static final Map.Entry<Status, MidasResponse> MATCH_NOT_FOUND_OR_MATCH_ID_IS_INVALID_PROBLEM = new AbstractMap.SimpleEntry<>(Status.MATCH_NOT_FOUND_OR_MATCH_ID_IS_INVALID_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    public static final Map.Entry<Status, MidasResponse> DEFECTIVE_MATCH_PROBLEM = new AbstractMap.SimpleEntry<>(Status.DEFECTIVE_DOTA_MATCH, MidasResponse.BAD_MIDAS_RESPONSE);
    public static final Map.Entry<Status, MidasResponse> NICKNAME_PROBLEM = new AbstractMap.SimpleEntry<>(Status.INVALID_NICKNAME_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    public static final Map.Entry<Status, MidasResponse> NEED_CODE_PROBLEM = new AbstractMap.SimpleEntry<>(Status.NEED_FRIEND_CODE_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    public static final Map.Entry<Status, MidasResponse> MATCH_NOT_ANALYZED_PROBLEM = new AbstractMap.SimpleEntry<>(Status.MATCH_NOT_ANALYZED_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    public static final Map.Entry<Status, MidasResponse> CHECK_STATUS_PARSING_PROBLEM = new AbstractMap.SimpleEntry<>(Status.CHECK_STATUS_PARSING_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    public static final Map.Entry<Status, MidasResponse> REPORT_BUG_PROBLEM = new AbstractMap.SimpleEntry<>(Status.REPORT_BUG_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);

    /**
     * Метод проверяющий карту ответа на ошибки.
     * @param statusMap карта ответа.
     * @return имеет ли карта ответа ошибки.
     * @see MidasResponse
     * @see Status
     * @see MidasResponseDTO
     */
    public static boolean hasErrors(Map.Entry<Status, MidasResponse> statusMap) {
        return !statusMap.getKey().equals(Status.SUCCESS);
    }

    /**
     * Метод возвращающий ответ в случае ошибок.
     * @return сущность ответа, содержащая {@link MidasResponseDTO}.
     * @see MidasResponse
     * @see Status
     * @see MidasResponseDTO
     */
    public static ResponseEntity<MidasResponseDTO> getBadResponse(Status status) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(modelMapper.map(MidasResponse.BAD_MIDAS_RESPONSE.statusAndMessage(status), MidasResponseDTO.class));
    }

    /**
     * Метод возвращающий ответ в случае успеха.
     * @return сущность ответа, содержащая {@link MidasResponseDTO}.
     * @see MidasResponse
     * @see Status
     * @see MidasResponseDTO
     */
    public static ResponseEntity<MidasResponseDTO> getGoodResponse(Map.Entry<Status, MidasResponse> statusMap) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(modelMapper.map(statusMap.getValue().statusAndMessage(statusMap.getKey()), MidasResponseDTO.class));
    }

    /**
     * Метод проверяющий карту ответа ошибок, выдавая соответствующий проверке ответ.
     * @param statusMap ка
     * @return сущность ответа, содержащая {@link MidasResponseDTO}.
     * @see MidasResponse
     * @see Status
     * @see MidasResponseDTO
     */
    public static ResponseEntity<MidasResponseDTO> checkAndGet(Map.Entry<Status, MidasResponse> statusMap) {
        return hasErrors(statusMap) ? getBadResponse(statusMap.getKey()) : getGoodResponse(statusMap);
    }

}

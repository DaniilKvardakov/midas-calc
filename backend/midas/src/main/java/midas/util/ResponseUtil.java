package midas.util;

import midas.annotations.Commented;
import midas.dto.MidasResponseDTO;
import midas.models.MidasResponse;
import midas.models.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Компонент для обработки типовых запросов.
 */
@Commented
@Component
public class ResponseUtil {

    private final ModelMapper modelMapper;

    @Autowired
    public ResponseUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Метод проверяющий карту ответа на ошибки.
     * @param statusMap карта ответа.
     * @return имеет ли карта ответа ошибки.
     * @see MidasResponse
     * @see Status
     * @see MidasResponseDTO
     */
    public boolean hasErrors(Map.Entry<Status, MidasResponse> statusMap) {
        return !statusMap.getKey().equals(Status.SUCCESS);
    }

    /**
     * Метод возвращающий ответ в случае ошибок.
     * @return сущность ответа, cодержащая {@link MidasResponseDTO}.
     * @see MidasResponse
     * @see Status
     * @see MidasResponseDTO
     */
    public ResponseEntity<MidasResponseDTO> getBadResponse(Status status) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(modelMapper.map(MidasResponse.BAD_MIDAS_RESPONSE.statusAndMessage(status), MidasResponseDTO.class));
    }

    /**
     * Метод возвращающий ответ в случае успеха.
     * @return сущность ответа, cодержащая {@link MidasResponseDTO}.
     * @see MidasResponse
     * @see Status
     * @see MidasResponseDTO
     */
    public ResponseEntity<MidasResponseDTO> getGoodResponse(Map.Entry<Status, MidasResponse> statusMap) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(modelMapper.map(statusMap.getValue().statusAndMessage(statusMap.getKey()), MidasResponseDTO.class));
    }

    /**
     * Метод проверяющий карту ответа ошибок, выдавая соответствующий проверке ответ.
     * @param statusMap ка
     * @return сущность ответа, cодержащая {@link MidasResponseDTO}.
     * @see MidasResponse
     * @see Status
     * @see MidasResponseDTO
     */
    public ResponseEntity<MidasResponseDTO> checkAndGet(Map.Entry<Status, MidasResponse> statusMap) {
        return this.hasErrors(statusMap) ? this.getBadResponse(statusMap.getKey()) : this.getGoodResponse(statusMap);
    }

}

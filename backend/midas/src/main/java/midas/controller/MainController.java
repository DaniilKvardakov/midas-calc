package midas.controller;

import midas.response.Status;
import midas.model.MidasData;
import midas.response.MidasResponse;
import midas.util.CalcFarmUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController("/midas/api")
public class MainController {

    private final CalcFarmUtil calcFarmUtil;

    @Autowired
    public MainController(CalcFarmUtil calcFarmUtil) {
        this.calcFarmUtil = calcFarmUtil;
    }

    @GetMapping("/profit/{id}/{nick}")
    public ResponseEntity<MidasResponse> getById(@PathVariable long id, @PathVariable String nick) {

        Map<Status, MidasResponse> checkErrorsMap = calcFarmUtil.getFarmById(nick, id);
        if (checkErrorsMap.get(Status.PARSE_ERROR) != null)
            return new ResponseEntity<>(MidasResponse.BAD_MIDAS_RESPONSE.statusAndMessage(Status.PARSE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        else if (checkErrorsMap.get(Status.DOTABUFF_URL_ERROR) != null)
            return new ResponseEntity<>(MidasResponse.BAD_MIDAS_RESPONSE.statusAndMessage(Status.DOTABUFF_URL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);

        MidasResponse response = calcFarmUtil.getFarmById(nick, id).get(Status.NO_ERRORS);

        System.out.println(response);
        return new ResponseEntity<>(response.statusAndMessage(Status.NO_ERRORS), HttpStatus.OK);

    }

    @PostMapping("/profit")
    public ResponseEntity<MidasResponse> getByData(@RequestBody MidasData midasData) {

        MidasResponse response = calcFarmUtil.getFarmByMidasData(midasData).get(Status.NO_ERRORS);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

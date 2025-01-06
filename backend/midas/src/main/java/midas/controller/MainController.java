package midas.controller;

import midas.response.Status;
import midas.model.MidasData;
import midas.response.MidasResponse;
import midas.util.CalcFarmUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;


@RestController("/midas/api")
@PropertySource("classpath:redirect.properties")
public class MainController {

    @Value("${url.swagger}")
    private String swaggerURL;
    private final CalcFarmUtil calcFarmUtil;

    @Autowired
    public MainController(CalcFarmUtil calcFarmUtil) {
        this.calcFarmUtil = calcFarmUtil;
    }

    @GetMapping("/swagger")
    public RedirectView swagger() {
        return new RedirectView(swaggerURL);
    }

    @GetMapping("/profit/{id}/{nick}")
    public ResponseEntity<MidasResponse> getById(@PathVariable long id, @PathVariable String nick) {

        Map<Status, MidasResponse> checkErrorsMap = calcFarmUtil.getFarmById(nick, id);
        if (checkErrorsMap.get(Status.PARSE_ERROR) != null)
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                    .body(MidasResponse.BAD_MIDAS_RESPONSE.statusAndMessage(Status.PARSE_ERROR));
        else if (checkErrorsMap.get(Status.DOTABUFF_URL_ERROR) != null)
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                    .body(MidasResponse.BAD_MIDAS_RESPONSE.statusAndMessage(Status.DOTABUFF_URL_ERROR));

        MidasResponse response = calcFarmUtil.getFarmById(nick, id).get(Status.NO_ERRORS);

        System.out.println(response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(response.statusAndMessage(Status.NO_ERRORS));

    }

    @PostMapping("/profit")
    public ResponseEntity<MidasResponse> getByData(@RequestBody MidasData midasData) {

        MidasResponse response = calcFarmUtil.getFarmByMidasData(midasData).get(Status.NO_ERRORS);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(response.statusAndMessage(Status.NO_ERRORS));
    }

    @GetMapping("/test")
    public ResponseEntity<Integer> test(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(1);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

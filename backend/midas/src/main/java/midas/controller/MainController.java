package midas.controller;

import midas.model.PhraseEntity;
import midas.response.Status;
import midas.model.MidasData;
import midas.response.MidasResponse;
import midas.util.CalcFarmUtil;
import midas.util.DataSupplierUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;


@RestController("/midas/api")
@PropertySource("classpath:redirect.properties")
public class MainController {

    @Value("${url.swagger}")
    private String swaggerURL;
    private final CalcFarmUtil calcFarmUtil;
    private final DataSupplierUtil dataSupplierUtil;

    @Autowired
    public MainController(CalcFarmUtil calcFarmUtil, List<PhraseEntity> phraseEntities, DataSupplierUtil dataSupplierUtil) {
        this.calcFarmUtil = calcFarmUtil;
        this.dataSupplierUtil = dataSupplierUtil;
    }

    @GetMapping("/swagger")
    public RedirectView swagger() {
        return new RedirectView(swaggerURL);
    }

    @GetMapping("/profit/{id}/{nick}")
    public ResponseEntity<MidasResponse> getById(@PathVariable long id, @PathVariable String nick) {

        Map<Status, MidasResponse> checkErrorsMap = calcFarmUtil.getFarmById(nick, id);
        if (checkErrorsMap.get(Status.ERROR) != null)
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                    .body(MidasResponse.BAD_MIDAS_RESPONSE.statusAndMessage(Status.ERROR));


        MidasResponse response = calcFarmUtil.getFarmById(nick, id).get(Status.SUCCESS);

        System.out.println(response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(response.statusAndMessage(Status.SUCCESS));

    }

    @PostMapping(value = "/profit", consumes = "multipart/form-data")
    public ResponseEntity<MidasResponse> getByData(@ModelAttribute MidasData midasData) {

        MidasResponse response = calcFarmUtil.getFarmByMidasData(midasData).get(Status.SUCCESS);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(response.statusAndMessage(Status.SUCCESS));
    }

    @GetMapping(value = "/phrase", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<PhraseEntity>> test(){

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(dataSupplierUtil.getAllPhrases());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

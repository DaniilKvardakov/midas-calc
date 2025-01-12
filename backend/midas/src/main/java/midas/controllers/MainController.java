package midas.controllers;

import midas.annotations.NoCommentsNeeded;
import midas.dto.MidasResponseDTO;
import midas.models.PhraseEntity;
import midas.service.MidasService;
import midas.service.PhraseService;
import midas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@NoCommentsNeeded
@RestController("/midas/api")
@PropertySource("classpath:secret/redirect.properties")
public class MainController {

    @Value("${url.swagger}")
    private String swaggerURL;
    private final MidasService midasService;
    private final PhraseService phraseService;

    @Autowired
    public MainController(MidasService midasService, PhraseService phraseService) {
        this.midasService = midasService;
        this.phraseService = phraseService;
    }


    @GetMapping("/swagger")
    public RedirectView swagger() {
        return new RedirectView(swaggerURL);
    }

    @GetMapping("/profit/{id}/{nick}/{code}")
    public ResponseEntity<MidasResponseDTO> getByApi(@PathVariable long id, @PathVariable String nick, @PathVariable long code) {
        System.out.println("id: " + id + "\nnick: " + nick + "\ncode: " + code + "\n\n");
        return ResponseUtil.checkAndGet(midasService.getFarm(id, nick, code));
    }

    @GetMapping("/profit/{id}/{nick}/")
    public ResponseEntity<MidasResponseDTO> getByApi(@PathVariable long id, @PathVariable String nick) {
        System.out.println("id: " + id + "\nnick: " + nick + "\n\n");
        return ResponseUtil.checkAndGet(midasService.getFarm(id, nick, null));
    }

    @GetMapping(value = "/phrase", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<PhraseEntity>> getPhrases() {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(phraseService.getAllPhrases());
    }

}

package midas.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Tag(name = "Midas endpoints")
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


    @Hidden
    @GetMapping("/swagger")
    public RedirectView swagger() {
        return new RedirectView(swaggerURL);
    }

    @Operation(
            summary = "Возвращает сущность ответа с полным результатом анализа на основе принимаемых данных. (id матча, имя игрока, код дружбы Steam)",
            description = "Проверяет полученный данные, парсит матч с помощью Open Dota API, анализирует и выдает ответ анализа покупки мидаса. " +
                    "Учитывает возможные совпадения по именам игроков, если передать дополнительно код дружбы Steam! " +
                    "Так уже через Steam API возможно определить конкретного теску."
    )
    @GetMapping("/profit/{id}/{nick}")
    public ResponseEntity<MidasResponseDTO> getByApi(
            @Parameter(required = true, description = "id матча.", example = "8128585252") @PathVariable long id,
            @Parameter(required = true, description = "Имя игрока", example = "Sex metal designer") @PathVariable String nick,
            @Parameter(description = "Код дружбы Steam", example = "2205770033") @RequestParam(required = false) Long code
    ) {
        System.out.println("id: " + id + "\nnick: " + nick + "\ncode: " + code + "\n\n");
        return ResponseUtil.checkAndGet(midasService.getFarm(id, nick, code));
    }

    @Operation(
            summary = "Возвращает список сущностей фраз героев. (фразы, пути к иконкам)"
    )
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

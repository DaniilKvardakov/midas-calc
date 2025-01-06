package midas.controller;

import midas.model.MidasData;
import midas.util.CalcFarmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/midas/api")
public class MainController {

    private final CalcFarmUtil calcFarmUtil;

    @Autowired
    public MainController(CalcFarmUtil calcFarmUtil) {
        this.calcFarmUtil = calcFarmUtil;
    }

    @GetMapping("/get/byId/{id}/{nick}")
    public ResponseEntity<Integer> getById(@PathVariable long id, @PathVariable String nick) {

        Integer result = calcFarmUtil.getFarmById(nick, id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/byData")
    public ResponseEntity<Integer> getByData(@RequestBody MidasData midasData){

        Integer result = calcFarmUtil.getFarmByMidasData(midasData);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

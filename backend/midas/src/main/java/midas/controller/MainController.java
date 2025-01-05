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


@RestController("/get")
public class MainController {

    private final CalcFarmUtil calcFarmUtil;

    @Autowired
    public MainController(CalcFarmUtil calcFarmUtil) {
        this.calcFarmUtil = calcFarmUtil;
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Integer> getById(@PathVariable int id) {

        return null;
    }

    @PostMapping("/byData")
    public ResponseEntity<Integer> getByData(@RequestBody MidasData midasData){
        return new ResponseEntity<>(calcFarmUtil.getFarmByMidasData(midasData), HttpStatus.OK);
    }
}

package midas.controller;

import midas.model.MidasData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/get")
public class MainController {

    @GetMapping("/byId/{id}")
    public ResponseEntity<Integer> getById(@PathVariable int id) {

        return null;
    }

    @GetMapping("/byData")
    public ResponseEntity<Integer> getByData(@RequestBody MidasData midasData){

        return null;
    }
}

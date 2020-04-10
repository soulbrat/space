package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
@RestController — говорит спрингу, что данный класс является REST контроллером.
Т.е. в данном классе будет реализована логика обработки клиентских запросов
*/
@RestController
public class ShipController {

    private ShipService shipService;

    /* Dependency Injection
    @Autowired — говорит спрингу, что в этом месте необходимо внедрить зависимость.
    В конструктор мы передаем интерфейс ShipService. Реализацию данного сервиса мы пометили аннотацией
    @Service ранее, и теперь спринг сможет передать экземпляр этой реализации в конструктор контроллера.
    */
    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    // create
    @PostMapping(value = "/rest/ships")
    public ResponseEntity<?> create(@RequestBody Ship ship) {
        shipService.create(ship);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // get all
    @GetMapping(value = "/rest/ships")
    public ResponseEntity<List<Ship>> readAll(
            @RequestParam(required = false) Map<String,String> allParams
    ) {
        System.out.println("DEBUG: CONTROLLER GET readAll");
        System.out.println("Parameters are " + allParams.entrySet();
        final List<Ship> ships = shipService.readAll();
        return ships != null &&  !ships.isEmpty()
                ? new ResponseEntity<>(ships, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // get with params
    public String getFoos(@RequestParam(required = false) String id) {
        return "ID: " + id;
    }
    // count
    @GetMapping(value = "/rest/ships/count")
    public ResponseEntity<Long> count() {
        System.out.println("DEBUG: CONTROLLER GET count");
        final long shipsCount = shipService.count();
        return new ResponseEntity<>(shipsCount, HttpStatus.OK);
    }
}

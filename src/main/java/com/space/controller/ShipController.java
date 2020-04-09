package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping(value = "/ships")
    public ResponseEntity<?> create(@RequestBody Ship ship) {
        System.out.println("DEBUG: CONTROLLER POST");
        shipService.create(ship);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/ships")
    public ResponseEntity<List<Ship>> read() {
        System.out.println("DEBUG: CONTROLLER GET");
        final List<Ship> ships = shipService.readAll();

        return ships != null &&  !ships.isEmpty()
                ? new ResponseEntity<>(ships, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

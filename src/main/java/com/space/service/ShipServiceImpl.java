package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/*
Аннотация @Service говорит спрингу, что данный класс является сервисом.
Это специальный тип классов, в котором реализуется некоторая бизнес логика приложения.
Впоследствии, благодаря этой аннотации Spring будет предоставлять нам экземпляр данного класса в местах, где это,
нужно с помощью Dependency Injection.

https://howtodoinjava.com/hibernate/hibernate-jpa-2-persistence-annotations-tutorial/

@Column(name="FNAME",length=100,nullable=false)
private String  firstName;
*/

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipsRepository shipsRepository;

    @Override
    public void create(Ship ship) {
        System.out.println("DEBUG: ShipServiceImpl CREATE");
        System.out.println("/**** Check INPUT parameters");
        System.out.println(ship.toString());
        shipsRepository.save(ship);
        if (shipsRepository.existsById(ship.id)){
            System.out.println("SHIP CREATED SUCCESSFULLY!");
        }
        System.out.println("***********************");
    }

    // get without params
    @Override
    public List<Ship> readAll() {
        List<Ship> ships = new ArrayList<>();
        System.out.println("DEBUG: ShipServiceImpl SHOW ALL");

            ships = shipsRepository.findAll();
        return ships;
    }
    // get with params
    // Parameters are [name=www, shipType=MERCHANT, after=-62126972453848, isUsed=true, pageNumber=0, pageSize=3, order=ID]
    public List<Ship> readAll(Map<String, String> allParams) {
        List<Ship> ships = new ArrayList<>();
        System.out.println("DEBUG: ShipServiceImpl SHOW ALL");

        ships = shipsRepository.findAll();
        return ships;
    }




    @Override
    public Ship read(long id) {
        return shipsRepository.getOne(id);
    }

    @Override
    public long count() {
        return shipsRepository.count();
    }

    @Override
    public boolean update(Ship ship, long id) {
        if (shipsRepository.existsById(id)) {
            ship.setId(id);
            shipsRepository.save(ship);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (shipsRepository.existsById(id)) {
            shipsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

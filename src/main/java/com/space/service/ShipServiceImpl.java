package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
//@Repository(value = "shipsRepository")
public class ShipServiceImpl implements ShipService {

    //@Autowired
    //private ShipsRepository shipsRepository;
    private final ShipsRepository shipsRepository;

    public ShipServiceImpl(ShipsRepository shipsRepository) {
        this.shipsRepository = shipsRepository;
    }

    @Override
    public void create(Ship ship) {
        System.out.println("DEBUG: ShipServiceImpl CREATE");
        shipsRepository.save(ship);
    }

    @Override
    public List<Ship> readAll() {
        System.out.println("DEBUG: ShipServiceImpl SHOW ALL");
        return shipsRepository.findAll();
    }

    @Override
    public Ship read(long id) {
        return shipsRepository.getOne(id);
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

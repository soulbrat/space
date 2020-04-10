package com.space.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ship")
public class Ship {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO, SEQUENCE, TABLE
    public long id;
    @Column(name = "name")
    public String name;
    @Column(name = "planet")
    public String planet;
    @Column(name = "shipType")
    @Enumerated(EnumType.STRING)
    public ShipType shipType;
    @Column(name = "prodDate")
    public Date prodDate;
    @Column(name = "isUsed")
    public boolean isUsed;
    @Column(name = "speed")
    public double speed;
    @Column(name = "crewSize")
    public int crewSize;
    @Column(name = "rating")
    public double rating;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPlanet() {
        return planet;
    }
    public void setPlanet(String planet) {
        this.planet = planet;
    }
    public ShipType getShipType() {
        return shipType;
    }
    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }
    public Date getProdDate() {
        return prodDate;
    }
    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }
    public boolean isUsed() {
        return isUsed;
    }
    public void setUsed(boolean used) {
        isUsed = used;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public int getCrewSize() {
        return crewSize;
    }
    public void setCrewSize(int crewSize) {
        this.crewSize = crewSize;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("Ship: {" +
                "id: %s, name: %s, " +
                "planet: %s, " +
                "shipType: %s, " +
                "prodDate: %s, " +
                "isUsed: %s, " +
                "speed: %s, " +
                "crewSize: %s, " +
                "rating: %s" +
                "}", getId(), getName(), getPlanet(), getShipType(), getProdDate(), isUsed(), getSpeed(), getCrewSize(), getRating());
    }
}

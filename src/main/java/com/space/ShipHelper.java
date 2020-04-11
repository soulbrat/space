package com.space;

import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.*;

public class ShipHelper {

    private static boolean isDebugEnabled = true;   // enable/disable log to console while debug
    public static int count = 0;                    // get count of founded ships

    public static void printMessage(String message){
        if (isDebugEnabled) {
            System.out.println(message);
        }
    }

    // need to return correct count of ships, depends on "findByName", pageNumber, pageSize and order
    public static List<Ship> getShipsOnPage(List<Ship> ships, Map<String, String> allParams){

        ShipHelper.printMessage("DEBUG: getShipsOnPage");

        /* check additional filters
        Example of possible parameters: [name=Falcon9, planet=Mars, shipType=MILITARY, after=32165295455955, before=32228367455955, \
        isUsed=true, minSpeed=0.7, maxSpeed=0.9, minCrewSize=2, maxCrewSize=20, minRating=1.1, maxRating=1.3, \
        pageNumber=0, pageSize=3, order=ID]
        */
        ships = getShipsByFilter(ships, allParams);

        /*
        Get correct Ships count after filter and before pageSize/Number
        Used for: 'Ships found'
        */
        count = ships.size();

        /*
        return correct size
        Example: [pageNumber=0, pageSize=3]
        */
        ships = getCorrectPageCount(ships, allParams);

        /*
        sort by request
        Example: [order=SPEED]
        */
        ships = getCorrectSort(ships, allParams);


        return ships;
    }

    // get ships by filters
    public static List<Ship> getShipsByFilter(List<Ship> ships, Map<String, String> allParams){
        // name
        if (allParams.containsKey("name")){
            ships = getShipsByName(ships, allParams.get("name"));
        }
        // planet
        if (allParams.containsKey("planet")){
            ships = getShipsByPlanet(ships, allParams.get("planet"));
        }
        // shipType
        if (allParams.containsKey("shipType")){
            ships = getShipsByType(ships, allParams.get("shipType"));
        }
        // Date after
        if (allParams.containsKey("after")){
            ships = getShipsByDateAfter(ships, allParams.get("after"));
        }
        // Date before
        if (allParams.containsKey("before")){
            ships = getShipsByDateBefore(ships, allParams.get("before"));
        }
        // isUsed
        if (allParams.containsKey("isUsed")){
            ships = getShipsByUsed(ships, allParams.get("isUsed"));
        }
        // minSpeed
        if (allParams.containsKey("minSpeed")){
            ships = getShipsByMinSpeed(ships, allParams.get("minSpeed"));
        }
        // maxSpeed
        if (allParams.containsKey("maxSpeed")){
            ships = getShipsByMaxSpeed(ships, allParams.get("maxSpeed"));
        }
        // minCrewSize
        if (allParams.containsKey("minCrewSize")){
            ships = getShipsByMinCrewSize(ships, allParams.get("minCrewSize"));
        }
        // maxCrewSize
        if (allParams.containsKey("maxCrewSize")){
            ships = getShipsByMaxCrewSize(ships, allParams.get("maxCrewSize"));
        }
        // minRating
        if (allParams.containsKey("minRating")){
            ships = getShipsByMinRating(ships, allParams.get("minRating"));
        }
        // maxRating
        if (allParams.containsKey("maxRating")){
            ships = getShipsByMaxRating(ships, allParams.get("maxRating"));
        }
        return ships;
    }

    // get ships by name
    public static List<Ship> getShipsByName(List<Ship> ships, String name){
        List<Ship> result = new ArrayList<>();
        String searchName = name.toLowerCase();
        for (Ship ship : ships){
            if (ship.getName().toLowerCase().indexOf(searchName) != -1){
                printMessage(String.format("DEBUG: getShipsByName | searchByName %s was found ship %s", searchName, ship.getName()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by Planet
    public static List<Ship> getShipsByPlanet(List<Ship> ships, String planet){
        List<Ship> result = new ArrayList<>();
        String searchPlanet = planet.toLowerCase();
        for (Ship ship : ships){
            if (ship.getPlanet().toLowerCase().indexOf(searchPlanet) != -1){
                printMessage(String.format("DEBUG: getShipsByPlanet | searchByPlanet %s was found ship %s", searchPlanet, ship.getPlanet()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by ShipType
    public static List<Ship> getShipsByType(List<Ship> ships, String param){
        List<Ship> result = new ArrayList<>();
        // get Enum from String
        ShipType shipType = ShipType.valueOf(param);
        for (Ship ship : ships){
            if (ship.getShipType() == shipType){
                printMessage(String.format("DEBUG: getShipsByType | searchByType %s was found ship %s", shipType, ship.getShipType()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by Date | after
    public static List<Ship> getShipsByDateAfter(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        long after;
        if (isLong(param)){
            after = Long.parseLong(param);
        } else {
            // if after/before is not correct -> return unmodified list
            return ships;
        }
        for (Ship ship : ships) {
            long shipDate = ship.getProdDate().getTime();
            if (shipDate >= after){
                printMessage(String.format("DEBUG: getShipsByDate | searchByAfter %d was found ship %s with Date %d", after, ship.getName(), shipDate));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by Date | before
    public static List<Ship> getShipsByDateBefore(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        long before;
        if (isLong(param)){
            before = Long.parseLong(param);
        } else {
            // if after/before is not correct -> return unmodified list
            return ships;
        }
        for (Ship ship : ships) {
            long shipDate = ship.getProdDate().getTime();
            if (shipDate <= before ){
                printMessage(String.format("DEBUG: getShipsByDate | searchByBefore %d was found ship %s with Date %d", before, ship.getName(), shipDate));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by isUsed
    public static List<Ship> getShipsByUsed(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            boolean isUsed = getBoolean(param);
            if (ship.isUsed() == isUsed){
                printMessage(String.format("DEBUG: getShipsByDate | searchByisUsed %s was found ship %s isUsed = %s", isUsed, ship.getName(), ship.isUsed()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by minSpeed
    public static List<Ship> getShipsByMinSpeed(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        double minSpeed;
        if (isDouble(param)){
            minSpeed = Double.parseDouble(param);
        } else {
            // if provided speed is not correct -> return unmodified list
            return ships;
        }
        for (Ship ship : ships) {
            if (Double.compare(ship.getSpeed(), minSpeed) >= 0) {
                printMessage(String.format("DEBUG: getShipsBySpeed | searchByMinSpeed %s was found ship %s speed = %s", minSpeed, ship.getName(), ship.getSpeed()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by maxSpeed
    public static List<Ship> getShipsByMaxSpeed(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        double maxSpeed;
        if (isDouble(param)){
            maxSpeed = Double.parseDouble(param);
        } else {
            // if provided speed is not correct -> return unmodified list
            return ships;
        }
        for (Ship ship : ships) {
            if ((Double.compare(ship.getSpeed(), maxSpeed)) <= 0){
                printMessage(String.format("DEBUG: getShipsBySpeed | searchByMaxSpeed %s was found ship %s speed = %s", maxSpeed, ship.getName(), ship.getSpeed()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by minCrewSize
    public static List<Ship> getShipsByMinCrewSize(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        int minCrewSize;
        if (isDigit(param)){
            minCrewSize = Integer.parseInt(param);
        } else {
            // if provided crewSize is not correct -> return unmodified list
            return ships;
        }
        for (Ship ship : ships) {
            if (ship.getCrewSize() >= minCrewSize){
                printMessage(String.format("DEBUG: getShipsByMinCrewSize | searchByCrewSize %s was found ship %s crewSize = %s", minCrewSize, ship.getName(), ship.getCrewSize()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by maxCrewSize
    public static List<Ship> getShipsByMaxCrewSize(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        int maxCrewSize;
        if (isDigit(param)){
            maxCrewSize = Integer.parseInt(param);
        } else {
            // if provided crewSize is not correct -> return unmodified list
            return ships;
        }
        for (Ship ship : ships) {
            if (ship.getCrewSize() <= maxCrewSize){
                printMessage(String.format("DEBUG: getShipsByMaxCrewSize | searchByCrewSize %s was found ship %s crewSize = %s", maxCrewSize, ship.getName(), ship.getCrewSize()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by minRating
    public static List<Ship> getShipsByMinRating(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        double minRating;
        if (isDouble(param)){
            minRating = Double.parseDouble(param);
        } else {
            // if provided minRating is not correct -> return unmodified list
            return ships;
        }
        for (Ship ship : ships) {
            if (ship.getRating() >= minRating){
                printMessage(String.format("DEBUG: getShipsByMinRating | searchByMinRating %s was found ship %s speed = %s", minRating, ship.getName(), ship.getRating()));
                result.add(ship);
            }
        }
        return result;
    }
    // get ships by maxRating
    public static List<Ship> getShipsByMaxRating(List<Ship> ships, String param) {
        List<Ship> result = new ArrayList<>();
        double maxRating;
        if (isDouble(param)){
            maxRating = Double.parseDouble(param);
        } else {
            // if provided minRating is not correct -> return unmodified list
            return ships;
        }
        for (Ship ship : ships) {
            if (ship.getRating() <= maxRating){
                printMessage(String.format("DEBUG: getShipsByMaxRating | searchByMaxRating %s was found ship %s speed = %s", maxRating, ship.getName(), ship.getRating()));
                result.add(ship);
            }
        }
        return result;
    }

    // get getCorrectPageCount
    public static List<Ship> getCorrectPageCount(List<Ship> ships, Map<String, String> allParams){
        // Example: [pageNumber=0, pageSize=3, order=SPEED]
        // if no pageNumber -> use '0'
        // if no pageSize -> use '3'
        int pageNumber = allParams.containsKey("pageNumber") ? Integer.parseInt(allParams.get("pageNumber")) : 0;
        int pageSize = allParams.containsKey("pageSize") ? Integer.parseInt(allParams.get("pageSize")) : 3;

        //printMessage(String.format("DEBUG: Get pageNumber %d and pageSize %s", pageNumber, pageSize));

        int from = Math.max(0,pageNumber*pageSize);
        int to = Math.min(ships.size(),(pageNumber+1)*pageSize);

        return ships.subList(from,to);
    }

    // get correct sort
    public static List<Ship> getCorrectSort(List<Ship> ships, Map<String, String> allParams){
        /* if params contais 'order' -> set sort list with correct order */
        if (allParams.containsKey("order")){
            String order = allParams.get("order");
            if (order.equals("ID")){
                //printMessage("Order by ID");
                ships = sortByID(ships);
            } else if (order.equals("SPEED")){
                //printMessage("Order by SPEED");
                ships = sortBySpeed(ships);
            } else if (order.equals("DATE")){
                //printMessage("Order by DATE");
                ships = sortByDate(ships);
            } else if (order.equals("RATING")){
                //printMessage("Order by RATING");
                ships = sortByRating(ships);
            }
        }
        return ships;
    }
    // sort ships order by ID
    public static List<Ship> sortByID(List<Ship> ships){
        Comparator<Ship> compareById = new Comparator<Ship>() {
            @Override
            public int compare(Ship o1, Ship o2) {
                return Long.compare(o1.getId(), o2.getId());
            }
        };
        Collections.sort(ships, compareById);
        return ships;
    }
    // sort ships order by speed
    public static List<Ship> sortBySpeed(List<Ship> ships){
        Comparator<Ship> compareBySpeed = new Comparator<Ship>() {
            @Override
            public int compare(Ship o1, Ship o2) {
                return Double.compare(o1.getSpeed(), o2.getSpeed());
            }
        };
        Collections.sort(ships, compareBySpeed);
        return ships;
    }
    // sort ships order by Prod Year
    public static List<Ship> sortByDate(List<Ship> ships){
        Comparator<Ship> compareByDate = new Comparator<Ship>() {
            @Override
            public int compare(Ship o1, Ship o2) {
                return o1.getProdDate().compareTo(o2.getProdDate());
            }
        };
        Collections.sort(ships, compareByDate);
        return ships;
    }
    // sort ships order by Rating
    public static List<Ship> sortByRating(List<Ship> ships){
        Comparator<Ship> compareByRating = new Comparator<Ship>() {
            @Override
            public int compare(Ship o1, Ship o2) {
                return Double.compare(o1.getRating(), o2.getRating());
            }
        };
        Collections.sort(ships, compareByRating);
        return ships;
    }

    // check Long
    public static boolean isLong(String param){
        try {
            long l = Long.parseLong(param);
            // any long and especially ID must be correct positive numbers
            if (l >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }
    // check Double
    public static boolean isDouble(String param){
        try {
            double l = Double.parseDouble(param);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    // check Integer
    public static boolean isDigit(String param){
        try {
            int l = Integer.parseInt(param);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    // get boolean from String
    public static boolean getBoolean(String param){
        if (param.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    // debug ships list
    public static void printShipsList(List<Ship> ships){
        printMessage("*** print ships ***************");
        for (Ship ship : ships){
            printMessage(ship.toString());
        }
        printMessage("*******************************");
    }

}

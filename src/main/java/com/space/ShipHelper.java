package com.space;

import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.*;

public class ShipHelper {

    private static boolean isDebugEnabled = true;
    private static int pageNumber;
    private static int pageSize;
    private static String order;

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

    // get ships by filter
    public static List<Ship> getShipsByFilter(List<Ship> ships, Map<String, String> allParams){
        List<Ship> result = new ArrayList<>();
        // name=Falcon9
        if (allParams.containsKey("name")){
            ships = getShipsByName(ships, allParams.get("name"));
        }
        // planet=Mars
        if (allParams.containsKey("planet")){
            ships = getShipsByPlanet(ships, allParams.get("planet"));
        }
        // shipType=MILITARY
        if (allParams.containsKey("shipType")){
            ships = getShipsByType(ships, allParams.get("shipType"));
        }

        // after=32165295455955, before=32228367455955
        // isUsed=true
        // minSpeed=0.7, maxSpeed=0.9
        // minCrewSize=2, maxCrewSize=20
        // minRating=1.1, maxRating=1.3

        /* if no filters -> return original list, else -> return new list */
        return result.size() == 0 ? ships : result;
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
    public static List<Ship> getShipsByType(List<Ship> ships, String type){
        List<Ship> result = new ArrayList<>();
        // get Enum from String
        ShipType shipType = ShipType.valueOf(type);
        for (Ship ship : ships){
            if (ship.getShipType() == shipType){
                printMessage(String.format("DEBUG: getShipsByType | searchByType %s was found ship %s", shipType, ship.getShipType()));
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
        pageNumber = allParams.containsKey("pageNumber") ? Integer.parseInt(allParams.get("pageNumber")) : 0;
        pageSize = allParams.containsKey("pageSize") ? Integer.parseInt(allParams.get("pageSize")) : 3;

        //printMessage(String.format("DEBUG: Get pageNumber %d and pageSize %s", pageNumber, pageSize));

        int from = Math.max(0,pageNumber*pageSize);
        int to = Math.min(ships.size(),(pageNumber+1)*pageSize);

        return ships.subList(from,to);
    }

    // get correct sort
    public static List<Ship> getCorrectSort(List<Ship> ships, Map<String, String> allParams){
        /* if params contais 'order' -> set sort list with correct order */
        if (allParams.containsKey("order")){
            order = allParams.get("order");
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

    // debug ships list
    public static void printShipsList(List<Ship> ships){
        printMessage("*** print ships ***************");
        for (Ship ship : ships){
            printMessage(ship.toString());
        }
        printMessage("*******************************");
    }

}

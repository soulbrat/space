package com.space;

import com.space.model.Ship;

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
        // return correct count
        // Example: [pageNumber=0, pageSize=3]
        ships = getCorrectPageCount(ships, allParams);
        //sort by request
        // Example: [order=SPEED]
        ships = getCorrectSort(ships, allParams);


        return ships;
    }

    // get getCorrectPageCount
    public static List<Ship> getCorrectPageCount(List<Ship> ships, Map<String, String> allParams){
        // Example: [pageNumber=0, pageSize=3, order=SPEED]
        if (allParams.containsKey("pageNumber") && allParams.containsKey("pageSize")){
            pageNumber = Integer.parseInt(allParams.get("pageNumber"));
            pageSize = Integer.parseInt(allParams.get("pageSize"));
            printMessage(String.format("DEBUG: Get pageNumber %d and pageSize %s", pageNumber, pageSize));

            int from = Math.max(0,pageNumber*pageSize);
            int to = Math.min(ships.size(),(pageNumber+1)*pageSize);

            return ships.subList(from,to);
        }
        return ships;
    }

    // get correct sort
    public static List<Ship> getCorrectSort(List<Ship> ships, Map<String, String> allParams){
        if (allParams.containsKey("order")){
            order = allParams.get("order");
            if (order.equals("ID")){
                printMessage("Order by ID");
                ships = sortByID(ships);
            } else if (order.equals("SPEED")){
                printMessage("Order by SPEED");
                ships = sortBySpeed(ships);
            } else if (order.equals("DATE")){
                printMessage("Order by DATE");
                ships = sortByDate(ships);
            } else if (order.equals("RATING")){
                printMessage("Order by RATING");
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

    // create correct rating for ship

    // debug ships list
    public static void printShipsList(List<Ship> ships){
        printMessage("*** print ships ***************");
        for (Ship ship : ships){
            printMessage(ship.toString());
        }
        printMessage("*******************************");
    }

}

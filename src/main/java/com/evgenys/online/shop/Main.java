package com.evgenys.online.shop;


public class Main {
    private static void searchForNearestStore(String address){
        String [] deliveryAddress =  address.split(" ");
        String city = deliveryAddress[0];
        String street = deliveryAddress[1];
        String numberHouse = deliveryAddress[2];


    }
    public static void main(String[] args) {
        searchForNearestStore("Ростов-на-Дону Красноармейская 123 45");
    }
}

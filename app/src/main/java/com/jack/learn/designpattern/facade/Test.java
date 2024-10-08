package com.jack.learn.designpattern.facade;

public class Test {


    public static void main(String[] args) {
//        OrderManager orderManager = new OrderManager();
//        orderManager.order(111);
//        orderManager.bill(111,300.00);
//
//        QueryService queryService = new QueryService();
//        queryService.whereToilet();

        RestaurantFacade facade = new RestaurantFacade();
//        QueryService queryService = facade.queryService();
//        OrderManager orderManager = facade.orderManager();
//        orderManager.order(222);
//        orderManager.bill(222,3330.00);
//        String s = queryService.whereToilet();

        facade.order(222);

    }
}

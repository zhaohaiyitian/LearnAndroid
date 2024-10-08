package com.jack.learn.designpattern.facade;

/**
 * 对于调用方来说，所有的功能我都通过RestaurantFacade一个类获取，不关心也没必要知道其内部的实现和运作方式。
 * 不管该系统后续添加新功能还是删除了旧功能，交互门面不变。
 */
public class RestaurantFacade {

    private OrderManager orderManager = new OrderManager();
    private QueryService queryService = new QueryService();

    public OrderManager orderManager() {
        return orderManager;
    }

    public QueryService queryService() {
        return queryService;
    }


    public void order(int tableNo) {
        orderManager.order(tableNo);
    }

}

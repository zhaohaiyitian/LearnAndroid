package com.jack.learn.designpattern.staticproxy;

/**
 * 代理对象，包含真实的对象，为真实对象的服务进行增强，和真实对象继承同一个接口
 *
 * 缺点： 耦合性太高，违反了开闭原则 扩展能力差，可维护性差
 */
public class Jack implements ManToolsFactory ,WomanToolsFactory{

    // 被包含的真实对象
    private ManToolsFactory manToolsFactory;
    private WomanToolsFactory womanToolsFactory;
    public Jack(ManToolsFactory factory) {
        manToolsFactory = factory;
    }

    public Jack(WomanToolsFactory factory) {
        womanToolsFactory = factory;
    }
    @Override
    public void saleManTools(int size) {
        doSomeThingBefore();
        manToolsFactory.saleManTools(size);
        doSomeThingAfter();
    }

    @Override
    public void saleWomanTools(int length) {
        doSomeThingBefore();
        womanToolsFactory.saleWomanTools(length);
        doSomeThingAfter();
    }



    public void doSomeThingBefore() {

    }
    public void doSomeThingAfter() {

    }

}

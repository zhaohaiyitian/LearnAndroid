package com.jack.learn.hotfix.robust;

public class TestRobust {

//    public long getIndex() {
//        return 19;
//    }

    public static ChangeQuickRedirect changeQuickRedirect;

    public long getIndex() {
        if (changeQuickRedirect != null) {
            return (long) changeQuickRedirect.accessDispatch();
        }
        return 100L;
    }

    public static void main(String[] args) {
        run();
        fix();
    }

    private static void run() {
        new Thread(() -> {
            TestRobust t = new TestRobust();
            for (int i = 0; i < 10; i++) {
                System.out.println(t.getIndex());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private static void fix() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                //模拟动态载入
                TestRobust.changeQuickRedirect = (ChangeQuickRedirect) Class.forName("com.jack.learn.hotfix.robust.StatePatch").newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}

package com.enjoyf.framework.jdbc.impl;

public class SwtichMainDataSource extends Thread {

    public void changeDs() {
        BaseJDBCDAOImpl impl = new BaseJDBCDAOImpl();
        impl.switchToMainDs();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                Thread.sleep(5 * 60 * 1000);//每5分钟尝试一次切换
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.changeDs();
        }
    }
}

package com.pedromoreirareisgmail.pquiz.common;

public class TimeDelay {

    public static void delay(long time) {

        try {

            Thread.sleep(time);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

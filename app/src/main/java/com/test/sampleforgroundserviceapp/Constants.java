package com.test.sampleforgroundserviceapp;

/**
 * Created by saket.shriwas on 3/15/2018.
 */

public class Constants {
    public interface ACTION {
        String MAIN_ACTION = "com.test.sampleforgroundserviceapp.action.main";
        String PREV_ACTION = "com.test.sampleforgroundserviceapp.action.prev";
        String PLAY_ACTION = "com.test.sampleforgroundserviceapp.action.play";
        String NEXT_ACTION = "com.test.sampleforgroundserviceapp.action.next";
        String STARTFOREGROUND_ACTION = "com.test.sampleforgroundserviceapp.action.startforeground";
        String STOPFOREGROUND_ACTION = "com.test.sampleforgroundserviceapp.action.stopforeground";
    }
    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }
}

package com.test.sampleforgroundserviceapp;

/**
 * Created by saket.shriwas on 3/15/2018.
 */

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.test.sampleforgroundserviceapp.action.main";
        public static String PREV_ACTION = "com.test.sampleforgroundserviceapp.action.prev";
        public static String PLAY_ACTION = "com.test.sampleforgroundserviceapp.action.play";
        public static String NEXT_ACTION = "com.test.sampleforgroundserviceapp.action.next";
        public static String STARTFOREGROUND_ACTION = "com.test.sampleforgroundserviceapp.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.test.sampleforgroundserviceapp.action.stopforeground";
    }
    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}

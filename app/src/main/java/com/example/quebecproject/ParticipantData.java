package com.example.quebecproject;

import java.util.ArrayList;

public class ParticipantData {
    private static String name = "";
    private static String posture = "single";

    private static float[] testTimes = new float[3];
    /*private static float test1Time = 0;
    private static float test2Time = 0;
    private static float test3Time = 0;*/

    private static double[] testAccuracies = new double[3];

    /*private static double test1Accuracy = 0;
    private static double test2Accuracy = 0;
    private static double test3Accuracy = 0;*/

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ParticipantData.name = name;
    }

    public static String getPosture() {
        return posture;
    }

    public static void setPosture(String posture) {
        ParticipantData.posture = posture;
    }

    public static float getTest1Time() {
        return testTimes[0];
    }

    public static void setTest1Time(float test1Time) {
        ParticipantData.testTimes[0] = test1Time;
    }

    public static float getTest2Time() {
        return testTimes[1];
    }

    public static void setTest2Time(float test2Time) {
        ParticipantData.testTimes[1] = test2Time;
    }

    public static float getTest3Time() {
        return testTimes[2];
    }

    public static void setTest3Time(float test3Time) {
        ParticipantData.testTimes[2] = test3Time;
    }

    public static double getTest1Accuracy() {
        return testAccuracies[0];
    }

    public static void setTest1Accuracy(double test1Accuracy) {
        ParticipantData.testAccuracies[0] = test1Accuracy;
    }

    public static double getTest2Accuracy() {
        return testAccuracies[1];
    }

    public static void setTest2Accuracy(double test2Accuracy) {
        ParticipantData.testAccuracies[1] = test2Accuracy;
    }

    public static double getTest3Accuracy() {
        return testAccuracies[2];
    }

    public static void setTest3Accuracy(double test3Accuracy) {
        ParticipantData.testAccuracies[2] = test3Accuracy;
    }
}

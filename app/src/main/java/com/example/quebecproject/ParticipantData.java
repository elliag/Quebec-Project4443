package com.example.quebecproject;

public class ParticipantData {
    private static String name = "";
    private static String posture = "single";

    private static float test1Time = 0;
    private static float test2Time = 0;
    private static float test3Time = 0;

    private static double test1Accuracy = 0;
    private static double test2Accuracy = 0;
    private static double test3Accuracy = 0;

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
        return test1Time;
    }

    public static void setTest1Time(float test1Time) {
        ParticipantData.test1Time = test1Time;
    }

    public static float getTest2Time() {
        return test2Time;
    }

    public static void setTest2Time(float test2Time) {
        ParticipantData.test2Time = test2Time;
    }

    public static float getTest3Time() {
        return test3Time;
    }

    public static void setTest3Time(float test3Time) {
        ParticipantData.test3Time = test3Time;
    }

    public static double getTest1Accuracy() {
        return test1Accuracy;
    }

    public static void setTest1Accuracy(double test1Accuracy) {
        ParticipantData.test1Accuracy = test1Accuracy;
    }

    public static double getTest2Accuracy() {
        return test2Accuracy;
    }

    public static void setTest2Accuracy(double test2Accuracy) {
        ParticipantData.test2Accuracy = test2Accuracy;
    }

    public static double getTest3Accuracy() {
        return test3Accuracy;
    }

    public static void setTest3Accuracy(double test3Accuracy) {
        ParticipantData.test3Accuracy = test3Accuracy;
    }
}

package com.dnaport.assistanttextmessaging;

import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AssistantConfig {

    private static MainActivity.LogViewEventListener listener;
    private static final String ASSISTANT_CONFIG = "AssistantConfig";
    private static String trigger;
    private static AtomicInteger count = new AtomicInteger(0);
    private static ArrayList<String> userNumbers = new ArrayList<>();
    private static final boolean TEXT_ONLY_RESPONSE = false;
    private static final boolean TEXT_AND_AUDIO_RESPONSE = true;
    private static int RESPONSE_TYPE = 1;
    private static boolean RESPONSE_WITH_AUDIO = false;
    private static File tempFileSaveDir;

    public static String getTrigger() {
        return AssistantConfig.trigger;
    }

    public static void setTrigger(String tr) {
        AssistantConfig.trigger = tr;
    }

    public static String getAssistantConfig() {
        return ASSISTANT_CONFIG;
    }

    public static void setListener(MainActivity.LogViewEventListener ls) {
        listener = ls;
    }

    public static void setResponseType(boolean text) {
        RESPONSE_WITH_AUDIO = text == TEXT_AND_AUDIO_RESPONSE;
    }

    public static int getResponseType() {
        return RESPONSE_TYPE;
    }

    public static void setTempFileSaveDir(File tempDir) {
        tempFileSaveDir = tempDir;
    }

    public static File getTempFileSaveDir() {
        return tempFileSaveDir;
    }

    public static boolean doResponseWithAudio() {
        return RESPONSE_WITH_AUDIO;
    }

    public static void addUserNumber(String number) {
        userNumbers.add(number);
    }

    public static ArrayList<String> getUserNumbers() {
        return userNumbers;
    }
}

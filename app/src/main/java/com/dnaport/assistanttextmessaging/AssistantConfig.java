package com.dnaport.assistanttextmessaging;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AssistantConfig {

    private static MainActivity.LogViewEventListener listener;
    private static final String ASSISTANT_CONFIG = "AssistantConfig";
    private static String trigger;
    private static ArrayList<String> assistantLogs = new ArrayList<String>();
    private static AtomicInteger count = new AtomicInteger(0);
    private static ArrayList<String> userNumbers = new ArrayList<>();

    public static String getTrigger() {
        return AssistantConfig.trigger;
    }

    public static void setTrigger(String tr) {
        AssistantConfig.trigger = tr;
    }

    public static String getAssistantConfig() {
        return ASSISTANT_CONFIG;
    }

    public static ArrayList<String> getLogArray() {
        return assistantLogs;
    }

    public static void updateAssistantLogs(String msg, String num) {
        String newLog = String.format(
                "[%2d]: SENT: %s TO %s",
                count.incrementAndGet(), msg, num
                );
        assistantLogs.add(newLog);
        listener.onLogUpdated();
    }

    public static void setListener(MainActivity.LogViewEventListener ls) {
        assistantLogs.add("Listening for logs...");
        listener = ls;
    }

    public static void addUserNumber(String number) {
        userNumbers.add(number);
    }

    public static ArrayList<String> getUserNumbers() {
        return userNumbers;
    }
}

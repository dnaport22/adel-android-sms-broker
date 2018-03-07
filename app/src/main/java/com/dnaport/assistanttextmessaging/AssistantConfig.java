package com.dnaport.assistanttextmessaging;

import android.app.Application;
import android.content.SharedPreferences;

public class AssistantConfig {

    public static final String ASSISTANT_CONFIG = "AssistantConfig";
    private static String trigger;

    public static String getTrigger() {
        return AssistantConfig.trigger;
    }

    public static void setTrigger(String tr) {
        AssistantConfig.trigger = tr;
    }

    public static String getAssistantConfig() {
        return ASSISTANT_CONFIG;
    }
}

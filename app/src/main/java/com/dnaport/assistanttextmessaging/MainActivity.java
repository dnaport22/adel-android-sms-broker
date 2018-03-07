package com.dnaport.assistanttextmessaging;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private String AssistTrigger = "ask google";
    private Button mButtonSaveConfig;
    private EditText mTriggerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AssistTrigger = getLatestTrigger();
        AssistantConfig.setTrigger(AssistTrigger);

        mButtonSaveConfig = (Button) findViewById(R.id.saveConfigButton);
        mTriggerEditText = (EditText) findViewById(R.id.triggerEdittext);
        mTriggerEditText.setText(AssistTrigger);
        mButtonSaveConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTrigger = mTriggerEditText.getText().toString();
                if (newTrigger != null) {
                    AssistTrigger = newTrigger;
                    loadPreferences();
                }
            }
        });
    }

    private void loadPreferences() {
        SharedPreferences.Editor sharedPreferences = getSharedPreferences(AssistantConfig.ASSISTANT_CONFIG, MODE_PRIVATE)
                .edit();
                sharedPreferences.putString("trigger", AssistTrigger);
                sharedPreferences.apply();
        AssistantConfig.setTrigger(AssistTrigger);
    }

    private String getLatestTrigger() {
        SharedPreferences sharedPreferences = getSharedPreferences(AssistantConfig.ASSISTANT_CONFIG, MODE_PRIVATE);
        String latestTrigger = sharedPreferences.getString("trigger", null);
        if (latestTrigger.isEmpty()) {
            return AssistTrigger;
        }
        return latestTrigger;
    }

}

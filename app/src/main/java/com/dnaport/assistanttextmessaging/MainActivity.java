package com.dnaport.assistanttextmessaging;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {
    private String AssistTrigger = "purple";
    private int ResponseType = 0;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private Dialog dialog;
    private EditText newUserNumberInput;
    private ArrayAdapter dialogAdapter;

    public interface LogViewEventListener {
        public void onLogUpdated();
    }

    private LogViewEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssistantConfig.setTrigger(AssistTrigger);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_MMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.READ_SMS},
                    1
            );
        }

        dialog = new Dialog(MainActivity.this);
        Button clickAddUserButton = (Button) findViewById(R.id.userGroupPopupButton);
        clickAddUserButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.activity_user_group);
                dialog.setTitle("Add Users");
                newUserNumberInput = (EditText) dialog.findViewById(R.id.newUserNumber);
                ListView userNumberlist = (ListView) dialog.findViewById(R.id.List);
                dialogAdapter = new ArrayAdapter<>(
                        MainActivity.this,android.R.layout.simple_list_item_1, AssistantConfig.getUserNumbers());
                userNumberlist.setAdapter(dialogAdapter);
                dialog.show();
            }
        });
        AssistantConfig.setListener(
                new LogViewEventListener() {
                    @Override
                    public void onLogUpdated() {
                        updateListView();
                    }
                }
        );

        final ToggleButton responseTypeButton = (ToggleButton) findViewById(R.id.changeResponseType);
        responseTypeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AssistantConfig.setResponseType(b);
            }
        });
    }

    private void updateListView() {
        adapter.notifyDataSetChanged();
    }

    public void closeAddUserDialog(View v) {
        dialog.dismiss();
    }

    public void addNewUserNumber(View v) {
        newUserNumberInput.getText();
        AssistantConfig.addUserNumber(
                newUserNumberInput.getText().toString()
        );
        dialogAdapter.notifyDataSetChanged();
        newUserNumberInput.setText("");
    }

}

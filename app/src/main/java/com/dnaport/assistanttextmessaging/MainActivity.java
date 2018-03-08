package com.dnaport.assistanttextmessaging;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    private String AssistTrigger = "ask google";
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
        listView = (ListView) findViewById(R.id.logListView);
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1 , AssistantConfig.getLogArray());
        listView.setAdapter(adapter);
        AssistantConfig.setListener(
                new LogViewEventListener() {
                    @Override
                    public void onLogUpdated() {
                        updateListView();
                    }
                }
        );
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

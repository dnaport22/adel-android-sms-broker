package com.dnaport.assistanttextmessaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    private String AssistTrigger = "ask google";
    private ArrayAdapter<String> adapter;
    private ListView listView;

    public interface LogViewEventListener {
        public void onLogUpdated();
    }

    private LogViewEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssistantConfig.setTrigger(AssistTrigger);

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

}

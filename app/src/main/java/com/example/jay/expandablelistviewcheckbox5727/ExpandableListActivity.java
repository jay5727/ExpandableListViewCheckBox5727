package com.example.jay.expandablelistviewcheckbox5727;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListActivity extends AppCompatActivity implements TotalListener {

    HashMap<String, List<StudentModel>> parent = null;
    private List<StudentModel> studList = null;//CHILD LIST
    List<String> departmentList = null;//PARENT KEY

    private ExpandableListAdapter mAdapter;
    ExpandableListView expandable_listView;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        expandable_listView = (ExpandableListView) findViewById(R.id.expandable_list);
        populateHashMapData(prepareData());

    }


    void populateHashMapData(List<StudentModel> studentList) {
        if (studentList != null && !studentList.isEmpty())
        {
            parent = new HashMap<>();
            departmentList = new ArrayList<String>();
            for (StudentModel s : studentList) {
                //SHRUTI,ALL,minusACTuser,All Menu
                if (!departmentList.contains(s.getDepartment()))
                {
                    departmentList.add(s.getDepartment());
                    studList = new ArrayList<StudentModel>();
                    for (StudentModel s1 : studentList) {
                        if ((departmentList.get(departmentList.size() - 1).equals(s1.getDepartment()))) {
                            studList.add(s1);
                        }
                    }
                    parent.put((departmentList.get(departmentList.size() - 1)), studList);
                }
            }
            mAdapter = new ExpandableListAdapter(ctx, departmentList, parent);
            //mAdapter.notifyDataSetChanged();
            expandable_listView.setAdapter(mAdapter);
        }
    }

    public List<StudentModel> prepareData() {
        try {
            List<StudentModel> studentList = new ArrayList<>();
            InputStream stream = getAssets().open("Student.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String data = builder.toString();
            studentList = new Gson().fromJson(data, new TypeToken<List<StudentModel>>() {
            }.getType());
            return studentList;
        } catch (Exception e) {
            //java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path $
            //dont keep root key...
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void expandGroupEvent(int groupPosition, boolean isExpanded) {
        if (isExpanded)
            expandable_listView.collapseGroup(groupPosition);
        else
            expandable_listView.expandGroup(groupPosition);
    }
}

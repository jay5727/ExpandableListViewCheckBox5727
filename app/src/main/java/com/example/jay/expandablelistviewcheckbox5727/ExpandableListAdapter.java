package com.example.jay.expandablelistviewcheckbox5727;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jay on 15-02-2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {


    public ArrayList<String> mGroupList = new ArrayList<String>();

    private String groupText;

    int lastExpandedPosition = -1;

    Context mContext;

    private TotalListener mListener;


    ArrayList<Boolean> selectedParentCheckBoxesState = null;

    //boolean parentCheckState[];

    private HashMap<Integer, boolean[]> mChildCheckStates;
    private HashMap<Integer, Boolean> mExpandedState;

    public HashMap<String, List<StudentModel>> parentHashMap;


    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;


    public ExpandableListAdapter(Context ctx, List<String> dapartmentList, HashMap<String, List<StudentModel>> parentHashMap) {
        this.mContext = ctx;
        this.mGroupList = (ArrayList<String>) dapartmentList;
        this.parentHashMap = parentHashMap;
        this.mListener = (TotalListener) ctx;
        mChildCheckStates = new HashMap<Integer, boolean[]>();
        mExpandedState = new HashMap<>();

        //parentCheckState = new boolean[dapartmentList.size()];
        selectedParentCheckBoxesState = new ArrayList<>();
    }

    //You can change the return type to your type also :D
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parentHashMap.get(this.mGroupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //return mGroupList.get(groupPosition).size();
        return parentHashMap.get(mGroupList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        //  return mGroupList.get(groupPosition);
        return mGroupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // return mGroupList.size();
        return this.mGroupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {

        Log.i("Adapter getGroupId", groupPosition + "");
        Log.i("Adapter getGroupId", (long) (groupPosition * 1024) + "");

        return groupPosition;
        //return (long) (groupPosition * 1024);  // To be consistent with getChildId
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {

        groupText = mGroupList.get(groupPosition);
        Log.i("Adapter getGroupView : ", "G:" + groupPosition + " ->" + groupText);

        //ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_layout, null);
            groupViewHolder = new GroupViewHolder();


            groupViewHolder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.group_chk_box);
            groupViewHolder.tvDeptName = (TextView) convertView.findViewById(R.id.tvDeptName);

            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.tvDeptName.setText(groupText);
        //groupViewHolder.groupCheckBox.setVisibility((type.equals("P") || type.equals("S")) ? View.GONE : View.VISIBLE);
        groupViewHolder.groupCheckBox.setOnClickListener(null);


        if (selectedParentCheckBoxesState.size() <= groupPosition) {
            selectedParentCheckBoxesState.add(groupPosition, false);
            mExpandedState.put(groupPosition,false);
        } else {
            groupViewHolder.groupCheckBox.setChecked(selectedParentCheckBoxesState.get(groupPosition));
        }


        groupViewHolder.groupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Callback to expansion of group item
                if (!isExpanded) {

                    if (lastExpandedPosition != -1) {
                        // if (lastExpandedPosition == groupPosition) {
                        //  lastExpandedPosition = -1;
                        if (!selectedParentCheckBoxesState.get(groupPosition)) {
                            makeParentAndChildMaintained(groupPosition, true);
                            Toast.makeText(mContext, "Expandedddd " + groupPosition + true, Toast.LENGTH_SHORT).show();
                            mListener.expandGroupEvent(groupPosition, isExpanded);
                        } else {
                            //if select group checkbox without expanding
                            //click on group
                            // uncheck the checkbox
                            makeParentAndChildMaintained(groupPosition, false);
                            Toast.makeText(mContext, "11", Toast.LENGTH_SHORT).show();

                        }
                        lastExpandedPosition = groupPosition;
                        /*} else {
                            Toast.makeText(mContext, "22", Toast.LENGTH_SHORT).show();

                        }*/
                    } else {
                        Toast.makeText(mContext, "33", Toast.LENGTH_SHORT).show();
                        makeParentAndChildMaintained(groupPosition, true);
                        Toast.makeText(mContext, "Expanded " + groupPosition + true, Toast.LENGTH_SHORT).show();
                        mListener.expandGroupEvent(groupPosition, isExpanded);

                    }
                   /* makeParentAndChildMaintained(groupPosition, true);
                    Toast.makeText(mContext, "Expanded " + groupPosition + true, Toast.LENGTH_SHORT).show();
                    mListener.expandGroupEvent(groupPosition, isExpanded);*/
                } else {
                    if (lastExpandedPosition == groupPosition && !selectedParentCheckBoxesState.get(groupPosition)) {
                        makeParentAndChildMaintained(groupPosition, true);
                        Toast.makeText(mContext, "lastExpandedPosition " + lastExpandedPosition + " groupPosition " + groupPosition + true, Toast.LENGTH_SHORT).show();
                        //mListener.expandGroupEvent(groupPosition, isExpanded);
                        //code for collapse needed here ...
                    } else {
                        lastExpandedPosition = -1;
                        makeParentAndChildMaintained(groupPosition, false);
                        Toast.makeText(mContext, "Collapse " + lastExpandedPosition + " groupPosition " + groupPosition + true, Toast.LENGTH_SHORT).show();
                        //mListener.expandGroupEvent(groupPosition, isExpanded);

                    }
                }
                notifyDataSetChanged();
            }
        });


        groupViewHolder.tvDeptName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean val;
                //val = mExpandedState.containsKey(groupPosition) ? mExpandedState.get(groupPosition) : false;
                lastExpandedPosition = groupPosition;
                mExpandedState.put(groupPosition, true);

                mListener.expandGroupEvent(groupPosition, isExpanded);
            }
        });

        return convertView;
    }

    //THIS is called
    private void makeParentAndChildMaintained(int mGroupPosition, boolean value) {

        boolean state = selectedParentCheckBoxesState.get(mGroupPosition);
        Log.d("Adapter MPACM", "STATE = " + state + ",groupPos " + mGroupPosition);
        selectedParentCheckBoxesState.remove(mGroupPosition);
        selectedParentCheckBoxesState.add(mGroupPosition, !state);

        boolean getChecked[];
        if (mChildCheckStates.containsKey(mGroupPosition)) {
            getChecked = mChildCheckStates.get(mGroupPosition);
        } else {
            getChecked = new boolean[getChildrenCount(mGroupPosition)];
        }
        //notice that in this case key is of type String...!
        String keyOnWhichUserClicked = mGroupList.get(mGroupPosition);

        for (int i = 0; i < parentHashMap.get(keyOnWhichUserClicked).size(); i++) {
            getChecked[i] = value;
            Log.d("Adapter MPACM forloop", mGroupPosition + "");
            //also make sure to save it in our child object as well
            parentHashMap.get(keyOnWhichUserClicked).get(i).setSelected(value);
        }
        mChildCheckStates.put(mGroupPosition, getChecked);
        //notifyDataSetChanged();
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, null);

            childViewHolder = new ChildViewHolder();


                /*childViewHolder.show_item_layout = (RelativeLayout) convertView.findViewById(R.id.show_item_layout);
                childViewHolder.params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);*/


            childViewHolder.childCheckBox = (CheckBox) convertView.findViewById(R.id.child_check_box);
            childViewHolder.tvuserNameChild = (TextView) convertView.findViewById(R.id.dummy_txt_view);
           /* childViewHolder.tvZone = (TextView) convertView.findViewById(R.id.tvZone);
            childViewHolder.tvDesignation = (TextView) convertView.findViewById(R.id.tvDesignation);*/

            convertView.setTag(childViewHolder);
            //convertView.setTag(R.layout.child_layout, childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
            // childViewHolder = (ChildViewHolder) convertView.getTag(R.layout.child_layout);
        }

        childViewHolder.tvuserNameChild.setText(this.parentHashMap.get(mGroupList.get(groupPosition)).get(childPosition).getUserName());
       /* childViewHolder.tvZone.setText(this.parentHashMap.get(mGroupList.get(groupPosition)).get(childPosition).getUserLocation());
        childViewHolder.tvDesignation.setText(this.parentHashMap.get(mGroupList.get(groupPosition)).get(childPosition).getDesignationName());*/
        childViewHolder.childCheckBox.setOnCheckedChangeListener(null);


        if (mChildCheckStates.containsKey(mGroupPosition)) {
            /*
             * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
			 * the value of the parent view (group) of this child (aka, the key),
			 * then retrive the boolean array getChecked[]
			*/
            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            childViewHolder.childCheckBox.setChecked(getChecked[mChildPosition]);

        } else {

			/*
             * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
			 * contain the value of the parent view (group) of this child (aka, the key),
			 * (aka, the key), then initialize getChecked[] as a new boolean array
			 *  and set it's size to the total number of children associated with
			 *  the parent group
			*/
            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];

            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
            mChildCheckStates.put(mGroupPosition, getChecked);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            childViewHolder.childCheckBox.setChecked(false);
        }

        childViewHolder.childCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                String text = " " + parentHashMap.get(mGroupList.get(groupPosition)).get(childPosition).getUserName() + " : ";

                if (isChecked) {
                   /* boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);

                    parentHashMap.get(mGroupList.get(groupPosition)).
                            get(childPosition).setSelected(true);*/

                    makeParentUncheckedIfAnyChildUncheckedOrParentCheckedIfAllChildChecked(mGroupPosition, mChildPosition, true);

                    Log.i("checked track", "G : " + groupPosition + " C : " + childPosition + text + isChecked);

                    Toast.makeText(mContext, "G : " + groupPosition + " C : " + childPosition + text + isChecked, Toast.LENGTH_SHORT).show();

                } else {
/*                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);

                    parentHashMap.get(mGroupList.get(groupPosition)).
                            get(childPosition).setSelected(false);*/
                    makeParentUncheckedIfAnyChildUncheckedOrParentCheckedIfAllChildChecked(mGroupPosition, mChildPosition, false);
                    Log.i("unchecked track", "G : " + groupPosition + " C : " + childPosition + text + isChecked);
                    Toast.makeText(mContext, "G : " + groupPosition + " C : " + childPosition + text + isChecked, Toast.LENGTH_SHORT).show();

                }
            }
        });

        return convertView;
    }


    int currentGroupPosition, currentChildPosition = -1;

    private void makeSingleSelectionInTotalGroup(int groupPosition, int childPosition) {
        try {
            //notice that in this case key is of type String...!

            boolean getChecked[];

            if (mGroupList != null && mGroupList.size() > 0) {

                Log.i("mGroupList SIZE", mGroupList.size() + "");
                for (int mGroupPosition = 0; mGroupPosition < mGroupList.size(); mGroupPosition++) {
                    Log.i("MGROUP", mGroupPosition + "");
                    //String keyOnWhichUserClicked = mGroupList.get(mGroupPosition);
                    getChecked = mChildCheckStates.containsKey(mGroupPosition)
                            ? mChildCheckStates.get(mGroupPosition) : new boolean[getChildrenCount(mGroupPosition)];
                    int childCount = getChildrenCount(mGroupPosition);
                    //getChecked = new boolean[childCount];

                    if (parentHashMap != null && parentHashMap.size() > 0) {
                    /*List<AssignActionModel> lstChild = parentHashMap.get(keyOnWhichUserClicked);
                    if (lstChild != null && lstChild.size() > 0) {
                        for (int mChildPosition = 0; mChildPosition < lstChild.size(); mChildPosition++) {
                            getChecked[mChildPosition] = (mGroupPosition == groupPosition && mChildPosition == childPosition);

                            //also make sure to save it in our child object as well
                            lstChild.get(mChildPosition).setSelected(mGroupPosition == groupPosition && mChildPosition == childPosition);

                        }
                        mChildCheckStates.put(mGroupPosition, getChecked);
                    }*/
                        for (int mChildPosition = 0; mChildPosition < parentHashMap.get(mGroupList.get(mGroupPosition)).size(); mChildPosition++) {
                            boolean isMatch = (mGroupPosition == groupPosition && mChildPosition == childPosition);

                            Log.i("MCHILD", "G: " + mGroupPosition + "C: " + mChildPosition + " isMatch " + isMatch);

                            getChecked[mChildPosition] = isMatch;
                            //also make sure to save it in our child object as well
                            parentHashMap.get(mGroupList.get(mGroupPosition)).get(mChildPosition).setSelected(isMatch);
                        }
                        mChildCheckStates.put(mGroupPosition, getChecked);
                    } else {
                        Log.i("parentHashMap", "NULL");
                    }
                }
                notifyDataSetChanged();
            }

        } catch (Exception ex) {
            Log.i("ex", ex.getMessage());
        }
    }


    //TWO CASES:
    //IF any child gets unchecked make parent unchecked :D
    //if all child gets selected make parent checked :D
    private void makeParentUncheckedIfAnyChildUncheckedOrParentCheckedIfAllChildChecked(int groupPosition, int childPosition, boolean value) {
        // if (currentGroupPosition != -1 && currentChildPosition != -1) {
        try {
            //notice that in this case key is of type String...!
            boolean getChecked[];
            int count = 0;
            if (mGroupList != null && mGroupList.size() > 0) {


                //String keyOnWhichUserClicked = mGroupList.get(mGroupPosition);
                getChecked = mChildCheckStates.containsKey(groupPosition)
                        ? mChildCheckStates.get(groupPosition) : new boolean[getChildrenCount(groupPosition)];

                if (parentHashMap != null && parentHashMap.size() > 0) {
                    //for (int mChildPosition = 0; mChildPosition < parentHashMap.get(mGroupList.get(groupPosition)).size(); mChildPosition++)
                    //{
                    getChecked[childPosition] = value;
                    //also make sure to save it in our child object as well
                    parentHashMap.get(mGroupList.get(groupPosition)).get(childPosition).setSelected(value);
                    //}

                    for (int mChildPosition = 0; mChildPosition < parentHashMap.get(mGroupList.get(groupPosition)).size(); mChildPosition++) {
                        //can check in model object or our  boolean child array...
                        //checking in latter case...!
                        if (getChecked[mChildPosition]) {
                            count++;
                        }
                    }
                    //CHECK IF ALL CHILDS WERE CHECKED MAKE PARENT CHECKED
                    selectedParentCheckBoxesState.remove(groupPosition);
                    selectedParentCheckBoxesState.add(groupPosition, parentHashMap.get(mGroupList.get(groupPosition)).size() == count);
                    mChildCheckStates.put(groupPosition, getChecked);
                } else {
                    Log.i("parentHashMap", "NULL");

                }
                notifyDataSetChanged();
            }

        } catch (Exception ex) {
            Log.i("ex", ex.getMessage());
        }
        //}

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void toggleSelection(boolean flag) {
        try {
            if (mGroupList != null && mGroupList.size() > 0) {

                for (int mGroupPosition = 0; mGroupPosition < mGroupList.size(); mGroupPosition++) {

                    selectedParentCheckBoxesState.remove(mGroupPosition);
                    selectedParentCheckBoxesState.add(mGroupPosition, flag);

                    boolean getChecked[];
                    if (mChildCheckStates.containsKey(mGroupPosition)) {
                        getChecked = mChildCheckStates.get(mGroupPosition);
                    } else {
                        getChecked = new boolean[getChildrenCount(mGroupPosition)];
                    }

                    //notice that in this case key is of type String...!
                    String keyOnWhichUserClicked = mGroupList.get(mGroupPosition);

                    if (parentHashMap != null && parentHashMap.size() > 0) {
                        /*List<AssignActionModel> lstChild = parentHashMap.get(keyOnWhichUserClicked);
                        if (lstChild != null && lstChild.size() > 0)
                        {
                            for (int mChildPosition = 0; mChildPosition < lstChild.size(); mChildPosition++) {
                                getChecked[mChildPosition] = flag;
                                //also make sure to save it in our child object as well :D
                                lstChild.get(mChildPosition).setSelected(flag);
                            }
                            mChildCheckStates.put(mGroupPosition, getChecked);
                        }*/
                        for (int mChildPosition = 0; mChildPosition < parentHashMap.get(keyOnWhichUserClicked).size(); mChildPosition++) {
                            getChecked[mChildPosition] = flag;
                            //also make sure to save it in our actual child object as well :D
                            parentHashMap.get(keyOnWhichUserClicked).get(mChildPosition).setSelected(flag);
                        }
                        mChildCheckStates.put(mGroupPosition, getChecked);
                    }
                }
                notifyDataSetChanged();
            }

        } catch (Exception ex) {

        }
    }


    class GroupViewHolder {
        public CheckBox groupCheckBox;
        public TextView tvDeptName;


    }

    class ChildViewHolder {
        public TextView tvuserNameChild;
        //public TextView tvZone;
        //public TextView tvDesignation;
        public CheckBox childCheckBox;
        public RelativeLayout show_item_layout;
        //final  LinearLayout.LayoutParams params;
        public FrameLayout.LayoutParams params;

        private void Layout_hide() {

            // show_item_layout.setVisibility(View.GONE);
            params.height = 0;
            //itemView.setLayoutParams(params); //This One.
            show_item_layout.setLayoutParams(params);   //Or This one.

        }
    }
}

package justjava.android.example.packagecom.Expandabale.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;

import justjava.android.example.packagecom.Expandabale.Pojo.Child;
import justjava.android.example.packagecom.Expandabale.Pojo.GroupModel;
import justjava.android.example.packagecom.Expandabale.R;


public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<GroupModel> groups;

    public ExpandListAdapter(Context context, List<GroupModel> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Child> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }



        TextView tv = (TextView) convertView.findViewById(R.id.sub_cat);
        Child child = (Child) getChild(groupPosition, childPosition);
        tv.setText(child.getName());


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Child> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.custom_group_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        GroupModel group = (GroupModel) getGroup(groupPosition);
        tv.setText(group.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
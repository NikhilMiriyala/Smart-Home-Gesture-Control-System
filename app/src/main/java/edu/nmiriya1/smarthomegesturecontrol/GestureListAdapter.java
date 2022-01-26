package edu.nmiriya1.smarthomegesturecontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GestureListAdapter extends BaseAdapter {

    private String [] gestureList;
    private LayoutInflater gesturesLayoutInflater;

    public GestureListAdapter(String[] gestureList, Context context) {
        this.gestureList = gestureList;
        this.gesturesLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gestureList.length;
    }

    @Override
    public Object getItem(int i) {
        return gestureList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = gesturesLayoutInflater.inflate(R.layout.gesture_list_layout, null);
        TextView gestureNameTextView = (TextView) v.findViewById(R.id.gestureNameTextView);
        gestureNameTextView.setText(gestureList[i]);
        return v;
    }
}

package zzheads.com.golfscorecard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zzhea on 11.07.2016.
 */
public class ListAdapter extends BaseAdapter {


    private final Context mContext;
    private final Hole[] mHoles;

    public ListAdapter (Context context, Hole[] holes) {
        mContext = context;
        mHoles = holes;
    }

    @Override
    public int getCount() {
        return mHoles.length;
    }

    @Override
    public Object getItem(int i) {
        return mHoles[i];
    }

    @Override
    public long getItemId(int i) {
        return 0; // Not implemented
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.holeLabel = (TextView) view.findViewById(R.id.holeLabel);
            holder.strokeCount = (TextView) view.findViewById(R.id.strokeCount);
            holder.addStroke = (Button) view.findViewById(R.id.addStrokeButton);
            holder.removeStroke = (Button) view.findViewById(R.id.removeStrokeButton);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.holeLabel.setText(mHoles[i].getLabel());
        holder.strokeCount.setText(mHoles[i].getStrokeCount()+"");
        holder.addStroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updatedStrokeCount = mHoles[i].getStrokeCount() + 1;
                mHoles[i].setStrokeCount(updatedStrokeCount);
                holder.strokeCount.setText(updatedStrokeCount+"");
            }
        });
        holder.removeStroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updatedStrokeCount = mHoles[i].getStrokeCount() - 1;
                if (updatedStrokeCount<0) updatedStrokeCount=0;
                mHoles[i].setStrokeCount(updatedStrokeCount);
                holder.strokeCount.setText(updatedStrokeCount+"");
            }
        });

        return view;
    }

    private static class ViewHolder {
        TextView holeLabel;
        TextView strokeCount;
        Button removeStroke;
        Button addStroke;
    }
}

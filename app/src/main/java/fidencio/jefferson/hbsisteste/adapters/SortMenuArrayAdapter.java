package fidencio.jefferson.hbsisteste.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fidencio.jefferson.hbsisteste.R;
import fidencio.jefferson.hbsisteste.enums.ListSortOption;

public class SortMenuArrayAdapter extends ArrayAdapter {

    private Context context;
    int layoutResourceId;
    ArrayList data;

    public SortMenuArrayAdapter(@NonNull Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((Activity) context).getLayoutInflater().inflate(layoutResourceId, parent, false);
        TextView textViewOrdem = convertView.findViewById(R.id.textViewOrdem);
        textViewOrdem.setText(data.get(position).toString());
        return convertView;
    }

    @Override
    public ListSortOption getItem(int position)
    {
        return (ListSortOption) data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}

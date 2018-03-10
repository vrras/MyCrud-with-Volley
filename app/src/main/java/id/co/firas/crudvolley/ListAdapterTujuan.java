package id.co.firas.crudvolley;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Firas Luthfi on 1/17/2018.
 */

public class ListAdapterTujuan extends ArrayAdapter<DataSet> {
    Context context;
    List<DataSet> tujuanList;
    public ListAdapterTujuan(Context context, int resource, List<DataSet> objects) {
        super(context, resource, objects);
        this.context=context;
        this.tujuanList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_tujuan,parent,false);
        TextView id, tujuan;
        id = (TextView) convertView.findViewById(R.id.txtId);
        tujuan = (TextView) convertView.findViewById(R.id.txtTujuan);
        DataSet dataTujuan = tujuanList.get(position);
        id.setText(dataTujuan.getId());
        tujuan.setText(dataTujuan.getTujuan());
        return convertView;
    }
}

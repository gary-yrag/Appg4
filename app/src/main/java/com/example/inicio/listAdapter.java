package com.example.inicio;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class listAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemname, itemdescripcion, fechahora,id;
    private final Integer[] integers;
    public listAdapter(Activity context, String[] itemname, String[] itemdescripcion, String[] fechahora, Integer[] integers, String[] id) {

        super(context, R.layout.activity_list_format, itemname);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.itemname=itemname;
        this.itemdescripcion = itemdescripcion;
        this.fechahora = fechahora;
        this.integers=integers;
        this.id = id;
    }

    public View getView(int posicion, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_format, null, true);


        TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto_secundario);
        TextView etxTextofechahora = (TextView) rowView.findViewById(R.id.texto_fechahora);
        EditText etxid = (EditText) rowView.findViewById(R.id.idnoti);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        etxDescripcion.setText(itemdescripcion[posicion]);
        etxTextofechahora.setText(fechahora[posicion]);
        etxid.setText(id[posicion]);
        return rowView;
    }
}

package com.example.inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.inicio.db.dbHandler;
import com.example.inicio.io.muestraapiAdapter;
import com.example.inicio.model.muestra;
import com.example.inicio.model.muestraEnviar;
import com.example.inicio.model.response;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;
    dbHandler db;
    ListView lista_objeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //displayNotification();

        db = new dbHandler(this);

        try {
            getDataMuestrasAPI();
            ejecutarNotifiacionAutomatico();
            //sendApiAutomatico();
            listInflate();


        }catch(Exception e){
            Log.e("getDataMuestras",e.getMessage());
    }

        /*
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        int icono = R.mipmap.ic_launcher;
        Intent i=new Intent(MainActivity.this, MensajeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, i, 0);

        mBuilder =new NotificationCompat.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Titulo")
                .setContentText("Hola que tal?")
                .setVibrate(new long[] {100, 250, 100, 500})
                .setAutoCancel(true);

        mNotifyMgr.notify(1, mBuilder.build());*/

        /*Intent notificationIntent = new Intent(this, MensajeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notif = new Notification.Builder(this).
                setContentTitle("Notificación SCAI " ).
                setContentText("Aqui el texto").
                setSmallIcon(R.mipmap.ic_launcher).setContentIntent(contentIntent).
                build();*/

        //api ..http request

        //Call<ArrayList<muestra>> call = muestraapiAdapter.getApiService().getMuestras("hash_padre1122");
        //call.enqueue(this);

        //this.displayNotificationReloj("Alerta papá","rojo");
        //ejecutarNotifiacionAutomatico();
        //ejecutarApiAutomatico();
        try {
            //sendDataApiRestFull();
        }catch (Exception e){
            Log.e("sendDataApiRestFull",e.getMessage());
        }

    }

    public void getDataMuestrasAPI (){
        Call<ArrayList<muestra>> call = muestraapiAdapter.getApiService().getMuestras("hash_padre1122");
        call.enqueue(new Callback<ArrayList<muestra>>() {
            @Override
            public void onResponse(Call<ArrayList<muestra>> call, Response<ArrayList<muestra>> response) {
                if (response.isSuccessful()) {
                    ArrayList<muestra> muestra = response.body();
                    setDatasql(muestra);
                    listInflate();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<muestra>> call, Throwable t) {

            }
        });
    }

    public void displayNotification() {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Notificacion Simple");
        builder.setContentText("Contenido de la notificacion");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public void displayNotificationReloj(String contenido, String color,String nick_hijo){
        createNotificationChannel();

        Intent intentAction = new Intent(this,MensajeActivity.class);
        intentAction.putExtra("titulo","Nick: "+nick_hijo);
        intentAction.putExtra("cuerpo",contenido);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intentAction,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Grooming");
        //builder.setSubText(nick_hijo);
        builder.setContentText(contenido);
        builder.setContentIntent(contentIntent);

        switch(color){
            case "rojo":
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                break;
            case "azul":
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                break;
            case "verde":
                builder.setPriority(NotificationCompat.PRIORITY_LOW);
                break;
            default:
                builder.setPriority(NotificationCompat.PRIORITY_MIN);
        }
        builder.setStyle(new NotificationCompat.BigTextStyle());
        builder.setContentIntent(contentIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        //pIntentlogin = PendingIntent.getBroadcast(context,1,intentAction,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion personal";
            String description = "Esta es la notificacion";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void setDatasql(ArrayList<muestra> muestra) {
        ContentValues values = new ContentValues();
    Log.e("setDarasql=>","--"+muestra.size());

        for (int i = 0; i < muestra.size(); i++) {
            String sql = "SELECT id,hash FROM muestra WHERE hash='" + muestra.get(i).getHash() + "'";
            Cursor c = db.onCursor(sql);

            if(c.getCount()==0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String tiempo_estadoactual = sdf.format(new Date());

                values.put("id", muestra.get(i).get_Id());
                values.put("hash", muestra.get(i).getHash());
                values.put("oracion", muestra.get(i).getOracion());
                values.put("hash_padre", muestra.get(i).getHash_padre());
                values.put("hash_hijo", muestra.get(i).getHash_hijo());
                values.put("nick_hijo", muestra.get(i).getNick_hijo());
                values.put("porcentaje_riesgo", muestra.get(i).getPorcentaje_riesgo());
                values.put("estado","0");
                values.put("tiempo_creado", muestra.get(i).getTiempo_creado());
                values.put("tiempo_estadoactual",tiempo_estadoactual);

                db.onInsert("muestra", values);
            }
        }
    }

    /*private void getDatasql() {
        String sql = "SELECT id,hash,oracion,hash_padre,hash_hijo,nick_hijo,porcentaje_riesgo,tiempo_creado FROM muestra";
        Cursor c = db.onCursor(sql);

        if (c.moveToFirst()) {
            do {
                Log.i("Column 0", c.getString(0));
                Log.i("Column 1", c.getString(1));
                Log.i("Column 2", c.getString(2));
            } while (c.moveToNext());
        }
    }*/

    private void lastNotification(){
        String sql = "SELECT id,hash,oracion,hash_padre,hash_hijo,nick_hijo, " +
                "strftime('%Y/%m/%d',tiempo_creado) as fecha ,strftime('%h:%M:%S',tiempo_creado) as hora," +
                " porcentaje_riesgo,estado " +
                "FROM muestra " +
                "WHERE estado IN('Pedido','0') "+
                "ORDER BY id ASC " +
                "LIMIT 1" ;
        Cursor c = db.onCursor(sql);

        if(c.getCount()>=1){
            c.moveToFirst();

            String oracion = c.getString(2);
            String nick_hijo = c.getString(5);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String tiempo_entregado = sdf.format(new Date());

            double porcentaje_riesgo = Double.parseDouble(c.getString(8));

            DecimalFormat df = new DecimalFormat("#.#");

            String color = "";

            if(porcentaje_riesgo<=20){
                color = "verde";
            }else if(porcentaje_riesgo>20 && porcentaje_riesgo<=39){
                color="azul";
            }else if(porcentaje_riesgo>40){
                color = "rojo";
            }

            try {
                displayNotificationReloj(oracion,color,nick_hijo);

                ContentValues values = new ContentValues();
                values.put("estado","Entregado");
                values.put("tiempo_entregado",tiempo_entregado);
                values.put("tiempo_estadoactual",tiempo_entregado);
                db.onUpdate("muestra", "hash=?", new String[]{String.valueOf(c.getString(1))}, values);
                listInflate();
            }catch(Exception e){
                Log.e("displayNoti---",e.getMessage());
            }
        }
    }

    public void sendDataApiRestFull(){
        String sql = "SELECT id,hash,estado,tiempo_estadoactual " +
                "FROM muestra "+
                "ORDER BY id ASC" ;
        Cursor c = db.onCursor(sql);

        if(c.getCount()>=1){
            ArrayList<muestraEnviar> muestraEnviars = new ArrayList<muestraEnviar>();
            c.moveToFirst();
            do {
                muestraEnviar muestraEnviar = new muestraEnviar();
                muestraEnviar.setId(c.getString(0));
                muestraEnviar.setHash(c.getString(1));
                muestraEnviar.setEstado(c.getString(2));
                muestraEnviar.setTiempo_estadoactual(c.getString(3));

                muestraEnviars.add(muestraEnviar);
            }while (c.moveToNext());

            Call<response> call = muestraapiAdapter.getApiService().setMuestras(muestraEnviars);
            call.enqueue(new Callback<response>() {
                @Override
                public void onResponse(Call<response> call, Response<response> response) {
                    Log.d("response-muestra=>",response.body().toString());
                }
                @Override
                public void onFailure(Call<response> call, Throwable t) {
                    Log.e("response-muetra-error=>",t.getMessage());
                }
            });
        }
    }

    public void ejecutarNotifiacionAutomatico(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lastNotification();
                handler.postDelayed(this,20000);//se ejecutara cada 10 segundos
            }
        },5000);//empezara a ejecutarse después de 5 milisegundos
    }

    public void sendApiAutomatico(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendDataApiRestFull();
                handler.postDelayed(this,90000);//se ejecutara cada 10 segundos
            }
        },5000);//empezara a ejecutarse después de 5 milisegundos
    }

    public void listInflate(){
        int icAzul = R.mipmap.azul;
        int icgris = R.mipmap.gris;
        int icrojo = R.mipmap.rojo;
        int icverde = R.mipmap.verde;

        String [] LTitulo,LDescripcion,LFecha,Lid;
        Integer [] LIcono;

        String sql = "SELECT id,hash,oracion,hash_padre,hash_hijo,nick_hijo, " +
                "strftime('%Y/%m/%d',tiempo_creado) as fecha ,strftime('%h:%M:%S',tiempo_creado) as hora," +
                " porcentaje_riesgo,estado, tiempo_creado " +
                "FROM muestra " +
                "ORDER BY id ASC ";
        Cursor c = db.onCursor(sql);

        if(c.getCount()>=1){
            LTitulo = new String[c.getCount()];
            LDescripcion = new String[c.getCount()];
            LIcono = new Integer[c.getCount()];
            LFecha = new String[c.getCount()];
            Lid = new String[c.getCount()];

            String[] lista_data = new String[c.getCount()];
            int contador = 0;

            if (c.moveToFirst()) {
                do {
                    double porcentaje_riesgo = Double.parseDouble(c.getString(8));
                    DecimalFormat df = new DecimalFormat("#.#");

                    int icIcon = 0;
                    String color = "", titulo = "";

                    if(porcentaje_riesgo<=20){
                        color = "verde";
                        icIcon = icverde;
                        titulo = "Nivel "+df.format(porcentaje_riesgo)+".%, bajo Grooming";
                        LIcono[contador] = icverde;
                    }else if(porcentaje_riesgo>20 && porcentaje_riesgo<=39){
                        color="azul";
                        icIcon = icAzul;
                        titulo = "Nivel "+df.format(porcentaje_riesgo)+".%, medio Grooming";
                        LIcono[contador] = icAzul;
                    }else if(porcentaje_riesgo>40){
                        color = "rojo";
                        icIcon = icrojo;
                        titulo = "Nivel "+df.format(porcentaje_riesgo)+".%, alto Grooming";
                        LIcono[contador] = icrojo;
                    }else{
                        color = "gris";
                        icIcon = icgris;
                        titulo = "Nivel "+df.format(porcentaje_riesgo)+".%, sin clasificar Grooming";
                        LIcono[contador] = icgris;
                    }

                    LTitulo[contador] = titulo + " - "+ c.getString(9);
                    LDescripcion[contador] = c.getString(2);

                    String [] dthora = c.getString(10).split("T");
                    String tmpHora = "";
                    if(dthora.length>1){
                        tmpHora = ", a las "+ dthora[1];
                    }

                    LFecha[contador] = c.getString(5) + " -- "+c.getString(6)+tmpHora;
                    Lid[contador] = c.getString(2); //hash
                    contador++;
                } while(c.moveToNext());

                lista_objeto = (ListView) findViewById(R.id.listview);
                listAdapter adapter=new listAdapter(this,LTitulo,LDescripcion,LFecha,LIcono,Lid);
                lista_objeto.removeAllViewsInLayout();
                lista_objeto.setAdapter(adapter);

                lista_objeto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected = ((EditText) view.findViewById(R.id.idnoti)).getText().toString();
                        listDelete(selected);
                    }
                });
            }
        }
    }

    public void listDelete(String selected){//Eliminar de la lista
        Toast.makeText(this, "" + selected, Toast.LENGTH_LONG).show();
    }
}
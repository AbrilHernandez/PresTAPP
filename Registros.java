package www.ittepic.edu.mx.prestapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import www.ittepic.edu.mx.prestapp.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Vector;


public class Registros extends ActionBarActivity {

    Vector vectorfull;
    int indiceSelected;
    ListView lista;
    ArrayAdapter adapter;
    DBManager manager;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        manager = new DBManager(this);
        crearLista();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ir(position);

            }
        });

  
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

package www.ittepic.edu.mx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.abril.prestapp.R;

/**
 * Created by abril on 25/05/16.
 */
public class Devoluciones extends ActionBarActivity {

    DBManager manager;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devoluciones);
        manager = new DBManager(this);
        lista = (ListView) findViewById(R.id.listView);
        crearLista();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(Devoluciones.this)

                        .setTitle("Borrar Registro?")
                        .setMessage("Desea borrar el valor seleccionado?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if (manager.eliminarDevolucion(getID(manager.getDevolucionesFull().get(position).toString()))) {
                                    Toast.makeText(Devoluciones.this, "Se elimino", Toast.LENGTH_SHORT).show();
                                    crearLista();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

    }//on create

    private int getID(String cad) {

        String id[] = cad.split(",");
        return Integer.parseInt(id[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_devoluciones, menu);
        return true;
    }

    private void crearLista() {

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, manager.getDevolucionesLista());
        //vectorfull = manager.getRegistrosFull();
        lista.setAdapter(adapter);


    }//crear lista

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void regresar(View v) {
        Intent a= new Intent(this,Main.class);
        startActivity(a);
        this.finish();


    }//guardar
}

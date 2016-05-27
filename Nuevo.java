package www.ittepic.edu.mx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abril.prestapp.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by abril on 25/05/16.
 */
public class Nuevo extends ActionBarActivity {


    GregorianCalendar calendario;
    DBManager manager;
    EditText campoArti, campoName, campoFechae, fechaPrestamo;
    Spinner combo;
    String categoria, articulo, nombre, fechae;
    int numCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        calendario = new GregorianCalendar();
        campoArti = (EditText) findViewById(R.id.campoEspe);
        campoName = (EditText) findViewById(R.id.campoNombre);
        campoFechae = (EditText) findViewById(R.id.campoFechaE);
        fechaPrestamo = (EditText) findViewById(R.id.campoFechaP);
        fechaPrestamo.setEnabled(false);
        fecha(); //ajustamos fecha actual

        //manejador de base de datos
        manager = new DBManager(this);

        crearSpinner();

    }//oncreate

    private void crearSpinner() {

        combo = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, manager.getCategorias());
        numCategorias = manager.getCategorias().size() - 1;
        combo.setAdapter(ada);
        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (combo.getSelectedItemPosition() == numCategorias) {
                    Intent sig = new Intent(Nuevo.this, Categorias.class);
                    startActivity(sig);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }//crear spinner

    private void fecha() {
        if (calendario.get(Calendar.DAY_OF_MONTH) < 10 && calendario.get(Calendar.MONTH) < 10) {

            fechaPrestamo.setText("" + calendario.get(Calendar.YEAR) + "-0" + (1 + calendario.get(Calendar.MONTH)) + "-0" + calendario.get(Calendar.DAY_OF_MONTH));
        } else if (calendario.get(Calendar.DAY_OF_MONTH) < 10) {
            fechaPrestamo.setText("" + calendario.get(Calendar.YEAR) + "-" + (1 + calendario.get(Calendar.MONTH)) + "-0" + calendario.get(Calendar.DAY_OF_MONTH));
        } else if (calendario.get(Calendar.MONTH) < 10) {
            fechaPrestamo.setText("" + calendario.get(Calendar.YEAR) + "-0" + (1 + calendario.get(Calendar.MONTH)) + "-" + calendario.get(Calendar.DAY_OF_MONTH));
        } else {
            fechaPrestamo.setText("" + calendario.get(Calendar.YEAR) + "-" + (1 + calendario.get(Calendar.MONTH)) + "-" + calendario.get(Calendar.DAY_OF_MONTH));
        }
    }//metodo para acomodar fecha

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo, menu);
        return true;
    }


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

    private boolean validarCampos() {
        int index = combo.getSelectedItemPosition();
        categoria = combo.getSelectedItem().toString();
        articulo = campoArti.getText().toString();
        nombre = campoName.getText().toString();

        fechae = campoFechae.getText().toString();

        if (index == 0 || index == numCategorias || articulo.isEmpty() || nombre.isEmpty() || fechae.isEmpty()) {
            return false;
        }
        if (fechaValida(fechaPrestamo.getText().toString(), fechae)) {

            return true;
        } else {
            Toast.makeText(this, "Fecha no valida", Toast.LENGTH_SHORT).show();
        }

        return false;
    }//validar campos

    public boolean fechaValida(String fecha1, String fecha2) {

        String f1[] = fecha1.split("-");
        String f2[] = fecha2.split("-");

        if (Integer.parseInt(f2[0]) >= Integer.parseInt(f1[0])) {
            if (Integer.parseInt(f2[1]) > 0 && Integer.parseInt(f2[1]) <= 12) {
                if (Integer.parseInt(f2[2]) > 0 && Integer.parseInt(f2[2]) <= 31) {
                    if (Integer.parseInt(f2[1]) >= Integer.parseInt(f1[1])) {

                        return true;
                    }

                }
            }
        }

        return false;

    }//

    private void clean() {
        combo.setSelection(0);
        campoArti.setText("");
        campoName.setText("");
        campoFechae.setText("");
    }//metodo limpiar campos

    public void guardar(View v) {
        try {


            if (validarCampos()) {
                manager.insertarPrestamo(categoria, articulo, nombre, fechaPrestamo.getText().toString(), fechae);
                Toast.makeText(this, "Se registro el prestamo", Toast.LENGTH_SHORT).show();
                clean();
            } else {
                Toast.makeText(this, "No se realizo el prestamo", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Capture todos los campos", Toast.LENGTH_SHORT).show();
        }


    }//guardar
    public void regresar(View v) {
        Intent a= new Intent(this,Main.class);
        startActivity(a);
        this.finish();


    }//guardar
}//class


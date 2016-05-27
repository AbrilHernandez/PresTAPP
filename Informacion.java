package www.ittepic.edu.mx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import www.ittepic.edu.mx.prestapp.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 * Created by abril on 25/05/16.
 */
public class Informacion extends ActionBarActivity {


    Vector vector;
    EditText campo1, campo2, campo3, campo4, campo5;
    CheckBox check;
    DBManager manager;
    int indice, id;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        manager = new DBManager(this);

        campo1 = (EditText) findViewById(R.id.campo1);
        campo2 = (EditText) findViewById(R.id.campo2);
        campo3 = (EditText) findViewById(R.id.campo3);
        campo4 = (EditText) findViewById(R.id.campo4);
        campo5 = (EditText) findViewById(R.id.campo5);

        check = (CheckBox) findViewById(R.id.check);
        bundle = getIntent().getExtras(); //para obtener el parametro
        obtenerDatos();

        generarCampos();

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    new AlertDialog.Builder(Informacion.this)

                            .setTitle("Se regreso?")
                            .setMessage("Desea efectuar la operacion?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    if (manager.insertarDevolucion(campo2.getText().toString(), campo1.getText().toString(), fechaActual())) {
                                        if (manager.eliminarPrestamo(id)) {
                                            Toast.makeText(Informacion.this, "Devolucion Efectuada", Toast.LENGTH_SHORT).show();

                                            retornar();
                                            finish();
                                        }//if eleiminar de registros

                                    }//if guardar en recuperado
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    check.setChecked(false);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }
            }
        });

        sePaso(fechaActual(), campo5.getText().toString());
    }//on create


    private String fechaActual() {
        GregorianCalendar calendario = new GregorianCalendar();
        String cad;
        if (calendario.get(Calendar.DAY_OF_MONTH) < 10 && calendario.get(Calendar.MONTH) < 10) {

            cad = "" + calendario.get(Calendar.YEAR) + "-0" + (1 + calendario.get(Calendar.MONTH)) + "-0" + calendario.get(Calendar.DAY_OF_MONTH);
        } else if (calendario.get(Calendar.DAY_OF_MONTH) < 10) {
            cad = "" + calendario.get(Calendar.YEAR) + "-" + (1 + calendario.get(Calendar.MONTH)) + "-0" + calendario.get(Calendar.DAY_OF_MONTH);
        } else if (calendario.get(Calendar.MONTH) < 10) {
            cad = "" + calendario.get(Calendar.YEAR) + "-0" + (1 + calendario.get(Calendar.MONTH)) + "-" + calendario.get(Calendar.DAY_OF_MONTH);
        } else {
            cad = "" + calendario.get(Calendar.YEAR) + "-" + (1 + calendario.get(Calendar.MONTH)) + "-" + calendario.get(Calendar.DAY_OF_MONTH);

        }
        return cad;
    }//metodo para acomodar fecha

    private void retornar() {
        Intent sig = new Intent(this, Registros.class);
        this.startActivity(sig);
        finish();
    }//retornamos a la lista si borramos un registro

    private void obtenerDatos() {
        vector = manager.getRegistrosFull();
        indice = bundle.getInt("indice");

    }

    private void generarCampos() {
        String registro[] = vector.get(indice).toString().split(",");
        campo1.setText(registro[3]);
        campo1.setEnabled(true);
        campo2.setText(registro[2]);
        campo2.setEnabled(true);
        campo3.setText(registro[1]);
        campo3.setEnabled(false);
        campo4.setText(registro[4]);
        campo4.setEnabled(false);
        campo5.setText(registro[5]);
        campo5.setEnabled(true);
        id = Integer.parseInt(registro[0]);


    }//nos llena los campos con los datos del prestatario

    private boolean validarCampos() {


        if (campo1.getText().toString().isEmpty() || campo2.getText().toString().isEmpty() || campo5.getText().toString().isEmpty()) {

            return false;
        }
        return true;


    }//nos llena los campos con los datos del prestatario

    public boolean fechaValida(String fecha1, String fecha2) {

        String f1[] = fecha1.split("-");
        String f2[] = fecha2.split("-");

        if (Integer.parseInt(f2[0]) >= Integer.parseInt(f1[0])) {
            if (Integer.parseInt(f2[1]) > 0 && Integer.parseInt(f2[1]) <= 12) {
                if (Integer.parseInt(f2[2]) > 0 && Integer.parseInt(f2[2]) <= 31) {
                    if (((Integer.parseInt(f2[1]) >= Integer.parseInt(f1[1])))) {
                        return true;
                    }

                }
            }
        }

        return false;

    }//


    public void sePaso(String fecha1, String fecha2) {

        //fecha 1 es la fecha de entrega acordada
        //fecha 2 es la fecha actual del sistema

        String f1[] = fecha1.split("-");
        String f2[] = fecha2.split("-");

        int diaEntrega = Integer.parseInt(f2[2]);
        int mesEntrega = Integer.parseInt(f2[1]);
        int diaActual = Integer.parseInt(f1[2]);
        int mesActual = Integer.parseInt(f1[1]);

        // Toast.makeText(this,"hoy es:"+diaActual+" dia Entrega= "+diaEntrega  +" Mes actual: "+mesActual +" Mes entrega: "+mesEntrega ,Toast.LENGTH_LONG).show();
        if (diaActual == diaEntrega && mesActual == mesEntrega) {
            Toast.makeText(this, "Hoy se vence el prestamo", Toast.LENGTH_LONG).show();
        } else if (diaActual > diaEntrega && mesActual == mesEntrega || mesActual > mesEntrega) {
            Toast.makeText(this, "Se vencio el prestamo", Toast.LENGTH_LONG).show();
        }

    }//metodo para comprobar si la fecha de entrega se paso


    public void modificar(View v) {

        String categoria = campo3.getText().toString();
        String articulo = campo2.getText().toString();
        String nombre = campo1.getText().toString();
        String fechap = campo4.getText().toString();
        String fechae = campo5.getText().toString();
        if (validarCampos()) {
            if (fechaValida(fechap, fechae)) {

                if (manager.modificarPrestamo("" + id, categoria, articulo, nombre, fechap, fechae)) {
                    Toast.makeText(this, "Se modifico el registro", Toast.LENGTH_SHORT).show();
                    obtenerDatos();
                    sePaso(fechaActual(), campo5.getText().toString());
                    generarCampos();
                } else {
                    Toast.makeText(this, "No se pudo modificar", Toast.LENGTH_SHORT).show();
                }//else

            } else {
                Toast.makeText(this, "Fecha no valida", Toast.LENGTH_SHORT).show();
            }//else

        } else {
            Toast.makeText(this, "No puedes dejar campos vacios", Toast.LENGTH_LONG).show();
        }//else

    }//boton modificiar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_informacion, menu);
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
            retornar();
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


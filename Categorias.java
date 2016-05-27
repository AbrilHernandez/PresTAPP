package www.ittepic.edu.mx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abril.prestapp.R;

/**
 * Created by abril on 25/05/16.
 */
public class Categorias extends ActionBarActivity {

    DBManager manager;
    Spinner combo;
    EditText campo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        campo = (EditText) findViewById(R.id.camp);
        manager = new DBManager(this);

        refresh();
    }

    public void guardar(View v) {
        String valor = campo.getText().toString();
        if (valor.isEmpty()) {
            Toast.makeText(this, "Inserte valor", Toast.LENGTH_SHORT).show();
        } else {
            try {
                manager.insertarTipo(valor);
                clean();
                refresh();
                Toast.makeText(this, "Se guardo categoria", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "No se guardo categoria", Toast.LENGTH_SHORT).show();
            }
        }
    }//guardar categoria

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categorias, menu);
        return true;
    }

    private void clean() {
        campo.setText("");
    }//clean campo

    private void refresh() {

        combo = (Spinner) findViewById(R.id.spinn);
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, manager.getCategoriasEliminar());
        //numCategorias = manager.getCategorias().size()-1;
        combo.setAdapter(ada);

    }//refrescar spinner

    public void borrar(View v) {
        int valor = combo.getSelectedItemPosition();
        if (valor == 0) {
            Toast.makeText(this, "Seleccione Categoria", Toast.LENGTH_SHORT).show();
        } else {
            try {
                manager.eliminarTipo(combo.getSelectedItem().toString());
                refresh();
                clean();
                Toast.makeText(this, "Se elimino categoria", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "No se elimino categoria", Toast.LENGTH_SHORT).show();
            }
        }

    }//borrar

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
}//class


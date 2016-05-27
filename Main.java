package www.ittepic.edu.mx.prestapp;


        import android.content.Intent;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Toast;


        import www.ittepic.edu.mx.prestapp.R;

public class Main extends ActionBarActivity {

    DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new DBManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void irNuevo(View v) {
        Intent sig = new Intent(this, Nuevo.class);
        this.startActivity(sig);

    }//ir al activity nuevo

    public void irCategorias(View v) {
        if (manager.devolucionesVacias()) {
            Toast.makeText(this, "No hay Devoluciones", Toast.LENGTH_SHORT).show();
        } else {
            Intent sig = new Intent(this, Devoluciones.class);
            this.startActivity(sig);
            finish();
        }
    }//ir al activity nuevo

    public void irRegistros(View v) {
        if (manager.registrosVacios()) {
            Toast.makeText(this, "No hay Prestamos", Toast.LENGTH_SHORT).show();
        } else {
            Intent sig = new Intent(this, Registros.class);
            this.startActivity(sig);
            this.finish();
        }
    }//ir al activity nuevo

    public void irCate(View v) {

        Intent sig = new Intent(this, Categorias.class);
        this.startActivity(sig);
        this.finish();
    }
    public void regresar(View v) {

        this.finish();


    }//guardar
}//class



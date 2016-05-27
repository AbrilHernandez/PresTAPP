package www.ittepic.edu.mx.prestapp;

import android.content.Intent;
import android.support.uv7.app.ActionBarActivity;
import android.support.uv7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import www.ittepic.edu.mx.prestapp.R;

public class Main extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle.saveInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


}//main

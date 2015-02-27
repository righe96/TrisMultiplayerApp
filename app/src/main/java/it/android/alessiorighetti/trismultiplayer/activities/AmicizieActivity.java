package it.android.alessiorighetti.trismultiplayer.activities;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

import it.android.alessiorighetti.trismultiplayer.R;
import it.android.alessiorighetti.trismultiplayer.service.FontFactory;

public class AmicizieActivity extends ActionBarActivity {
    private ListView lista_amici;
    private  Button aggiungi;
    private TextView amico;
    private EditText eTAmico;
    private LinkedList<String> amici;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amicizie);
        lista_amici= (ListView) findViewById(R.id.listView2);
        aggiungi= (Button) findViewById(R.id.aggiungi);
        amico= (TextView) findViewById(R.id.amico);
        eTAmico=(EditText) findViewById(R.id.editText);

        Typeface font = FontFactory.INSTANCE.getFont(this);

    
        amico.setTypeface(font);
        aggiungi.setTypeface(font);







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_amicizie, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

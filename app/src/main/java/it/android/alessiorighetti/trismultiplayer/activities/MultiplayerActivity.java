package it.android.alessiorighetti.trismultiplayer.activities;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.LinkedList;

import it.android.alessiorighetti.trismultiplayer.R;
import it.android.alessiorighetti.trismultiplayer.service.Enemy;
import it.android.alessiorighetti.trismultiplayer.service.UserModel;
import it.android.alessiorighetti.trismultiplayer.service.UsersOnlineService;


public class MultiplayerActivity extends ActionBarActivity {
    private String ipadress;
    private LinkedList<Enemy> giocatori;
    private ListView lista_giocatori;
    private TextView casuale,msg;
    private ListAdapter adapter;
    private Typeface font;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        getSupportActionBar().hide();
        final AssetManager assets=getResources().getAssets();
        font = Typeface.createFromAsset(assets,"font/FunSized.ttf");
        ipadress=getResources().getString(R.string.databas_server);
        lista_giocatori=(ListView) findViewById(R.id.listView);
        casuale=(TextView) findViewById(R.id.casuale);
        msg=(TextView) findViewById(R.id.msg);
        msg.setTypeface(font);
        casuale.setTypeface(font);
        userModel=UserModel.load(this);


        //prelevo gli utenti dal database
        TaskUsersOnline task= new TaskUsersOnline();
        giocatori= new LinkedList<Enemy>();
        task.execute(userModel.getUsername());

        //visualizzo gli utenti una listView
        adapter= new BaseAdapter() {

            @Override
            public int getCount() {
                return giocatori.size();

            }

            @Override
            public Object getItem(int i) {
                return giocatori.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if(view==null){
                    view=getLayoutInflater().inflate(R.layout.custom_list_item,null);
                }
                final TextView usernameTextView = (TextView) view.findViewById(R.id.list_username);
                usernameTextView.setTypeface(font);
                final TextView nomeTextView = (TextView) view.findViewById(R.id.list_nome);
                nomeTextView.setTypeface(font);
                final TextView cognomeTextView = (TextView) view.findViewById(R.id.list_cognome);
                cognomeTextView.setTypeface(font);
                final Enemy itemEnemy= (Enemy) getItem(i);
                usernameTextView.setText(itemEnemy.getUsername());
                nomeTextView.setText(itemEnemy.getNome());
                cognomeTextView.setText(itemEnemy.getCognome());
                return view;
            }
        };
        lista_giocatori.setAdapter(adapter);
        lista_giocatori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Indirizzo IP : "+giocatori.get(i).getIp(),Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplayer, menu);
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
    private class TaskUsersOnline extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            giocatori=UsersOnlineService.get().getUsers(getApplicationContext());
            return null;
        }
    }


}

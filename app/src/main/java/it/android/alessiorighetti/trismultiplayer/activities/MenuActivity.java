package it.android.alessiorighetti.trismultiplayer.activities;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

import it.android.alessiorighetti.trismultiplayer.R;
import it.android.alessiorighetti.trismultiplayer.service.FontFactory;
import it.android.alessiorighetti.trismultiplayer.service.TrisApplication;
import it.android.alessiorighetti.trismultiplayer.service.UserModel;


public class MenuActivity extends ActionBarActivity {
    private TextView multiplayer,duecontrodue,amici;
    private TextView titolo,username_info;
    private UserModel user;
    private String ipadress;
    private boolean salvato=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        user=UserModel.load(this);
        if(user.equals(null)){
            salvato=false;
        }
        TaskOnline task= new TaskOnline();
        task.execute(user.getUsername());
        ipadress=getResources().getString(R.string.databas_server);



        Typeface font = FontFactory.INSTANCE.getFont(this);

        //instanziare le view component
        multiplayer=(TextView) findViewById(R.id.multiplayer);
        duecontrodue=(TextView) findViewById(R.id.duecontrodue);
        amici=(TextView) findViewById(R.id.amici);
        titolo=(TextView) findViewById(R.id.titolo);
        username_info=(TextView) findViewById(R.id.usernameinfo);


        //settare font
        titolo.setTypeface(font);
        multiplayer.setTypeface(font);
        duecontrodue.setTypeface(font);
        amici.setTypeface(font);
        username_info.setTypeface(font);




        if(salvato==true) {

                username_info.append(user.getUsername() + "    Punteggio : " + user.getPunteggio());

        }




        duecontrodue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, SinglePlayerActivity.class);
                startActivity(i);


            }
        });
        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuActivity.this,MultiplayerActivity.class);
                startActivity(i);
            }
        });
        amici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuActivity.this,AmicizieActivity.class);
                startActivity(i);
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TaskOffline task= new TaskOffline();
        task.execute(user.getUsername());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            UserModel.logout(this);
            return true;
        }
        else if (id == R.id.registrazione) {
            Intent i=new Intent(MenuActivity.this,RegistrationActivity.class);
            startActivity(i);
            return true;
        }
        else if(id==R.id.login){
            Intent i=new Intent(MenuActivity.this,LoginActivity.class);
            startActivity(i);
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class TaskOnline extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... username) {


            String loginUrl = "http://"+ipadress+"/operazioni.php?id=3&username=" +username[0];

            HttpClient httpClient = TrisApplication.getThreadSafeHttpClient();
            HttpGet request = new HttpGet(loginUrl);
            try {
                httpClient.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }




    }
    private class TaskOffline extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... username) {

            String loginUrl = "http://"+ipadress+"/operazioni.php?id=2&username=" +username[0];

            HttpClient httpClient = TrisApplication.getThreadSafeHttpClient();
            HttpGet request = new HttpGet(loginUrl);
            try {
                httpClient.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }




    }




}

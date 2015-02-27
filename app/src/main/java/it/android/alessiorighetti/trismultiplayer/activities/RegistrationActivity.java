package it.android.alessiorighetti.trismultiplayer.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.net.UnknownHostException;

import it.android.alessiorighetti.trismultiplayer.R;
import it.android.alessiorighetti.trismultiplayer.service.FontFactory;
import it.android.alessiorighetti.trismultiplayer.service.RegistrationService;
import it.android.alessiorighetti.trismultiplayer.service.UserModel;
import it.android.alessiorighetti.trismultiplayer.view.ProgressAlertDialog;


public class RegistrationActivity extends ActionBarActivity {
    private EditText ETUsername,ETPassword,ETNome,ETCognome;
    private TextView tvUsername,tvPassword,tvNome,tvCognome;
    private UserModel user=null;
    private ProgressAlertDialog caricamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Typeface font = FontFactory.INSTANCE.getFont(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ETUsername=(EditText) findViewById(R.id.Username);
        ETPassword=(EditText) findViewById(R.id.Password);
        ETNome=(EditText) findViewById(R.id.Nome);
        ETCognome=(EditText) findViewById(R.id.Cognome);
        tvUsername=(TextView) findViewById(R.id.txtUsername);
        tvPassword=(TextView) findViewById(R.id.txtPassword);
        tvNome=(TextView) findViewById(R.id.txtNome);
        tvCognome=(TextView) findViewById(R.id.txtCognome);

        //settting font
        tvUsername.setTypeface(font);
        tvPassword.setTypeface(font);
        tvNome.setTypeface(font);
        tvCognome.setTypeface(font);








    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.registration_item) {
            String username=ETUsername.getText().toString();
            String password=ETPassword.getText().toString();

            String nome=upperCaseFirst(ETNome.getText().toString());
            String cognome=upperCaseFirst(ETCognome.getText().toString());
            if(username.equals("") ||password.equals("")  ||  nome.equals("") || cognome.equals("")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);
                alertDialogBuilder.setTitle("Uno o più campi vuoti");

                AlertDialog alertDialog = alertDialogBuilder.create();


                alertDialog.show();
                return true;

            }
            else {

                RegistrationTask task = new RegistrationTask();

                task.execute(nome, cognome, username, password);

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public String upperCaseFirst(String stringa){
        if(stringa.equals("")){
            return  null;
        }
        String stringa_s="";
        stringa_s=stringa.toLowerCase();
        return Character.toUpperCase(stringa.charAt(0))+stringa_s.substring(1);
    }
    private class RegistrationTask extends AsyncTask<String,Void,UserModel> {
        UserModel user;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           caricamento=new ProgressAlertDialog();
            caricamento.show(getSupportFragmentManager(),"progress_dialog_tag");





        }

        @Override
        protected UserModel doInBackground(String... strings) {


             user = RegistrationService.get().doRegistration(getApplicationContext(),strings[0],strings[1],strings[2],strings[3]);


            return user;

        }



        @Override
        protected void onPostExecute(final UserModel userModel) {
            super.onPostExecute(userModel);
            caricamento.dismiss();
            if(userModel.getErrore().equals("-1")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);
                alertDialogBuilder.setTitle("Username già esistente!");

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
            else if(userModel==null){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);
                alertDialogBuilder.setTitle("Errore nella connesione al server");



                alertDialogBuilder.setNeutralButton("Ritorna al Menu",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent in= new Intent(RegistrationActivity.this,MenuActivity.class);
                        startActivity(in);
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
            else  {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);
                alertDialogBuilder.setTitle("Registrazione Avvenuta");



                alertDialogBuilder.setNeutralButton("Ritorna al Menu",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userModel.save(getApplicationContext());
                        Intent in= new Intent(RegistrationActivity.this,MenuActivity.class);
                        startActivity(in);
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }



        }

        @Override
        protected void onProgressUpdate(Void... values) {


            super.onProgressUpdate(values);
        }

    }



}



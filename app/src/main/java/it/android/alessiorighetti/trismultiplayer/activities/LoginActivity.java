package it.android.alessiorighetti.trismultiplayer.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import it.android.alessiorighetti.trismultiplayer.R;
import it.android.alessiorighetti.trismultiplayer.service.FontFactory;
import it.android.alessiorighetti.trismultiplayer.service.LoginService;
import it.android.alessiorighetti.trismultiplayer.service.UserModel;
import it.android.alessiorighetti.trismultiplayer.view.ProgressAlertDialog;

public class LoginActivity extends ActionBarActivity {
    private EditText etUsername,etPassword;
    private TextView titolo;
    private Button login;
    private ProgressAlertDialog caricamento;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Typeface font = FontFactory.INSTANCE.getFont(this);

        etPassword = (EditText) findViewById(R.id.editTextPassword);
        etUsername = (EditText) findViewById(R.id.editTextUsername);
        titolo=(TextView) findViewById(R.id.txtLogin);
        login=(Button) findViewById(R.id.button_login);

        titolo.setTypeface(font);

        login.setTypeface(font);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();

                if(username.equals("") || password.equals("")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setTitle("Username e/o Password vuoti");

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();
                }
                else {
                    LoginTask task= new LoginTask();
                    task.execute(username, password);



                }
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    private class LoginTask extends AsyncTask<String,Void,UserModel> {
        UserModel user;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            caricamento = new ProgressAlertDialog();
            caricamento.show(getSupportFragmentManager(), "progress_dialog_tag");
        }

        @Override
        protected UserModel doInBackground(String... strings) {

            user= LoginService.get().doLogin(getApplicationContext(), strings[0], strings[1]);
            return user;
        }

        protected void onPostExecute(final UserModel usermodel) {
            super.onPostExecute(usermodel);
            caricamento.dismiss();
            if (usermodel.getErrore().equals("-1")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                alertDialogBuilder.setTitle("Username e/o Password sbagliati");

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            } else if (usermodel == null) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                alertDialogBuilder.setTitle("Errore nella connesione al server");


                alertDialogBuilder.setNeutralButton("Ritorna al Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent in = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(in);
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();


            }
            else{
                usermodel.save(getApplicationContext());

                Intent in = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(in);
                finish();
            }
        }
    }
    }


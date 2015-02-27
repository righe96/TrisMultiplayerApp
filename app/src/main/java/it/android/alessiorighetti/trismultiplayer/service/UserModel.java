package it.android.alessiorighetti.trismultiplayer.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alessio on 07/02/2015.
 */
public class UserModel {
    private String username,nome,cognome,password;
    private String errore;
    private int punteggio=0;
    private static String USERNAME_KEY = "username_key";
    private static String NOME_KEY = "nome_key";
    private static String COGNOME_KEY = "cognome_key";
    private static String PASSWORD_KEY = "password_key";
    private static String PUNTEGGIO_KEY="punteggio_key";


    public UserModel( String cognome, String nome, String username,String password) {

        this.cognome = cognome;
        this.nome = nome;
        this.username = username;
        this.password=password;
        errore="0";
    }
    public UserModel(){
        errore="";

    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setErrore(String errore) {
        this.errore = errore;
    }
    public void increment_punteggio(){
        punteggio+=1;

    }
    public void setPunteggio(int punteggio){
        this.punteggio=punteggio;
    }

    public String getErrore() {
        return errore;
    }
    public void save(final Context context){
        final SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(USERNAME_KEY,username);

        editor.putString(NOME_KEY,nome);
        editor.putString(COGNOME_KEY,cognome);
        editor.putInt(PUNTEGGIO_KEY,punteggio);
        editor.commit();
    }
    public static UserModel load(final Context ctx){
        final SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(ctx);
        UserModel user=new UserModel(prefs.getString(COGNOME_KEY,null),prefs.getString(NOME_KEY,null),prefs.getString(USERNAME_KEY,null),prefs.getString(PASSWORD_KEY,null));
        user.setPunteggio(prefs.getInt(PUNTEGGIO_KEY,0));

        return  user;

    }

    public int getPunteggio() {
        return punteggio;
    }
    public static  void logout(Context ctx){
        final SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed=prefs.edit();
        ed.clear().commit();
    }
}

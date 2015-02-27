package it.android.alessiorighetti.trismultiplayer.service;

/**
 * Created by Alessio on 19/02/2015.
 */
public class Enemy  {
    private String username,nome,cognome,ip;
    public Enemy(){

    }
    public Enemy(String username,String nome,String cognome,String ip){
        this.username=username;
        this.nome=nome;
        this.cognome=cognome;
        this.ip=ip;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
}

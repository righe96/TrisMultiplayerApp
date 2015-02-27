package it.android.alessiorighetti.trismultiplayer.service;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import it.android.alessiorighetti.trismultiplayer.R;

/**
 * Created by Alessio on 19/02/2015.
 */
public class UsersOnlineService {
    private static final String KO_RESULT = "KO";
    private static UsersOnlineService instance;
    private String ipadress;


    public synchronized static UsersOnlineService get() {
        if (instance == null) {
            instance = new UsersOnlineService();
        }
        return instance;
    }

    private ResponseHandler<LinkedList<Enemy>> mUserModelResponseHandler = new ResponseHandler<LinkedList<Enemy>>() {
        @Override
        public LinkedList<Enemy> handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
            LinkedList<Enemy> giocatori = new LinkedList<Enemy>();

            // Here we have to read the UserModel from the httpResponse
            InputStream content = httpResponse.getEntity().getContent();

            byte[] buffer = new byte[1024];
            int numRead = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((numRead = content.read(buffer)) != -1) {
                baos.write(buffer, 0, numRead);
            }
            String utenti = new String(baos.toByteArray());


            content.close();
            Log.d("utenti", utenti);
            String[] array= utenti.split(";");
            for(int i=0;i<array.length;i+=4){
                String ip = longToIp(Long.parseLong(array[i+3]));
                giocatori.add(new Enemy(array[i],array[i+1],array[i+2],ip));

            }




            return giocatori;

        }


    };

    public LinkedList<Enemy> getUsers(final Context context) {

        LinkedList<Enemy> giocatori = new LinkedList<Enemy>();
        ipadress = context.getResources().getString(R.string.databas_server);

        String loginUrl = "http://" + ipadress + "/operazioni.php?id=1&username=0";


        HttpClient httpClient = TrisApplication.getThreadSafeHttpClient();
        HttpGet request = new HttpGet(loginUrl);

        try {

            giocatori = httpClient.execute(request, mUserModelResponseHandler);


        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return giocatori;

    }
    public String longToIp(long ip) {
        StringBuilder result = new StringBuilder(15);

        for (int i = 0; i < 4; i++) {

            result.insert(0,Long.toString(ip & 0xff));

            if (i < 3) {
               result.insert(0,'.');
            }

            ip = ip >> 8;
        }
        return result.toString();
    }
}

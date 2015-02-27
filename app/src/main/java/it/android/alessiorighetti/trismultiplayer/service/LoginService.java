package it.android.alessiorighetti.trismultiplayer.service;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import it.android.alessiorighetti.trismultiplayer.R;

/**
 * Created by Alessio on 24/02/2015.
 */
public class LoginService {

    private static final String KO_RESULT = "KO";
    private static LoginService instance;
    private String ipadress;
    public synchronized static LoginService get() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }
    private ResponseHandler<UserModel> mUserModelResponseHandler = new ResponseHandler<UserModel>() {
        @Override
        public UserModel handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
            // The UserModel to return
            UserModel userModel = null;
            // Here we have to read the UserModel from the httpResponse
            InputStream content = httpResponse.getEntity().getContent();
            byte[] buffer = new byte[1024];
            int numRead = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((numRead = content.read(buffer)) != -1) {
                baos.write(buffer, 0, numRead);
            }


            content.close();
            Log.d("content", new String(baos.toByteArray()));
            baos.flush();
            if (new String(baos.toByteArray()).equals("0")) {
                userModel = new UserModel();
                userModel.setErrore("-1");

                return userModel;

            } else {
                // We create the JSONObject
                try {
                    JSONObject resultAsJson = new JSONObject(new String(baos.toByteArray()));
                    baos.flush();
                    // We check if the result is ok or no
                    final String result = resultAsJson.optString("result");


                    if (KO_RESULT.equals(result)) {
                        // In this case we had an error
                        userModel = null;
                    } else {

                        // Here the user is logged so we create the UserModel in the right way
                        userModel = new UserModel(resultAsJson.optString("cognome"), resultAsJson.optString("nome"), resultAsJson.optString("username"), resultAsJson.optString("password"));
                        userModel.setPunteggio(Integer.parseInt(resultAsJson.optString("punteggio")));



                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                Log.d("usermodel",userModel.getUsername());

                return userModel;
            }
        }

    };
    public UserModel doLogin(final Context context,String username,String password)  {

        ipadress=context.getResources().getString(R.string.databas_server);
        UserModel userModel=null;
        String loginUrl = "http://"+ipadress+"/login.php?username=" +username  + "&password=" + password;


        HttpClient httpClient = TrisApplication.getThreadSafeHttpClient();
        HttpGet request = new HttpGet(loginUrl);

        try {

            userModel= httpClient.execute(request,mUserModelResponseHandler);



        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return userModel;
    }
}

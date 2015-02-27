package it.android.alessiorighetti.trismultiplayer.service;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.lang.reflect.Type;

/**
 * Created by Alessio on 23/02/2015.
 */
public class TrisApplication extends Application {

    public static HttpClient getThreadSafeHttpClient(){
        return ThreadSafeHttpClientFactory.INSTANCE.getThreadSafeHttpClient();

    }
    public static HttpClient getHttpClient(){
        return new DefaultHttpClient();


    }
    public static void releaseThreadSafeHttpClient(){
        ThreadSafeHttpClientFactory.INSTANCE.release();
    }
    public static Typeface getFont(Context ctx){
        return FontFactory.INSTANCE.getFont(ctx);
    }

}

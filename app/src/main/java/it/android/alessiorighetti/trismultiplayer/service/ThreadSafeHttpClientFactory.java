package it.android.alessiorighetti.trismultiplayer.service;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * Created by Alessio on 23/02/2015.
 */
public enum ThreadSafeHttpClientFactory {
    INSTANCE;
    private static  final int TIMEOUT = 60000;
    private static final int HTTP_PORT =80;
    private static final int HTTPS_PORT=443;
    private static final String HTTP_SCHEMA="http";
    private static final String HTTPS_SCHEMA="https";
    private HttpClient httpClient;
    private ThreadSafeHttpClientFactory(){
        httpClient= createHttpClient();

    }
    public HttpClient getThreadSafeHttpClient(){
        if(httpClient==null){
            httpClient= createHttpClient();
        }
        return httpClient;
    }
    public void release(){
        httpClient=null;
    }
    private HttpClient createHttpClient(){
        HttpParams httpParams=new BasicHttpParams();
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, HTTP.DEFAULT_CONTENT_CHARSET);
        SchemeRegistry schemeRegistry= new SchemeRegistry();
        Scheme httpScheme= new Scheme(HTTP_SCHEMA, PlainSocketFactory.getSocketFactory(),HTTP_PORT);
        schemeRegistry.register(httpScheme);
        Scheme httpsScheme = new Scheme(HTTPS_SCHEMA, SSLSocketFactory.getSocketFactory(),HTTPS_PORT);
        schemeRegistry.register(httpsScheme);
        ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(httpParams,schemeRegistry);
        HttpClient tmpClient= new DefaultHttpClient(clientConnectionManager,httpParams);
        HttpConnectionParams.setSoTimeout(tmpClient.getParams(),TIMEOUT);
        HttpConnectionParams.setConnectionTimeout(tmpClient.getParams(), TIMEOUT);
        addUserAgent(tmpClient);
        return tmpClient;
    }
    private void addUserAgent(HttpClient client){
        String userAgent = System.getProperty("http.agent");
        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,userAgent);
    }

}

package it.android.alessiorighetti.trismultiplayer.service;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by Alessio on 24/02/2015.
 */
public enum FontFactory {
    INSTANCE;
    private static final String  PATH_FONT = "font/FunSized.ttf";
    private  Typeface font;

    private FontFactory(){

    }
    public Typeface getFont(Context ctx) {
        if (font == null) {
            font = createFont(ctx);
        }
        return font;

    }
    private Typeface createFont(Context ctx){
        final AssetManager assets=ctx.getResources().getAssets();
       return Typeface.createFromAsset(assets,"font/FunSized.ttf");
    }
}

package it.android.alessiorighetti.trismultiplayer.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import it.android.alessiorighetti.trismultiplayer.R;
import it.android.alessiorighetti.trismultiplayer.activities.MenuActivity;
import it.android.alessiorighetti.trismultiplayer.activities.SinglePlayerActivity;
import it.android.alessiorighetti.trismultiplayer.service.FontFactory;
import it.android.alessiorighetti.trismultiplayer.service.Game;

/**
 * Created by Alessio on 04/02/2015.
 */

public class BoardView extends View {
    Paint paint = new Paint();
    int colonne = 3;
    int righe = 3;
    boolean repaint = false;
    Rect daDisegnare;
    Rect[][] rettangoli = new Rect[colonne][righe];
    int[][] matrice_gioco = new int[colonne][righe];
    int turno = 1;
    int offset=10;


    public BoardView(Context context) {
        super(context);
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                matrice_gioco[j][i] = -1;

            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        for (int i = 0; i < rettangoli.length; i++) {
            for (int j = 0; j < rettangoli[0].length; j++) {

                    if (rettangoli[j][i].contains((int) event.getX(), (int) event.getY())) {
                        if (matrice_gioco[j][i] == -1) {

                            matrice_gioco[j][i] = turno;
                            if(Game.isFull(matrice_gioco)){
                                showDialog(-1);
                            }
                            if (Game.whoWin(matrice_gioco)==1){

                                showDialog(1);
                            }
                            else if (Game.whoWin(matrice_gioco)==0){
                                showDialog(0);
                            }
                            if (turno == 1) {
                                turno = 0;
                            } else {
                                turno = 1;
                            }



                            invalidate();
                        }
                    }

            }
        }


        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
      /*  Resources res = getContext().getResources();
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        BitmapDrawable TileMe = new BitmapDrawable(bm);
        TileMe.setTileModeX(Shader.TileMode.REPEAT);
        TileMe.setTileModeY(Shader.TileMode.REPEAT);
        canvas.drawBitmap(TileMe.getBitmap(),0,0,null);*/




        //inizializzazione matrice_gioco con -1


        paint.setStrokeWidth(5);

        //disegno colonne
        canvas.drawLine(width / 3, 0, width / 3, height, paint);
        canvas.drawLine(width * 2 / 3, 0, width * 2 / 3, height, paint);


        //disegno righe

        canvas.drawLine(0, height / 3, width, height / 3, paint);
        canvas.drawLine(0, height * 2 / 3, width, height * 2 / 3, paint);

        //divisione dei quadrati


            int x1 = 0, y1 = 0, x2 = width / 3, y2 = height / 3;
            for (int  i = 0; i < rettangoli.length; i++) {
                for (int j = 0; j < rettangoli[0].length; j++) {
                    rettangoli[j][i] = new Rect(x1, y1, x2, y2);
                    x1 += width / 3;
                    x2 = x1 + width / 3;
                }
                x1 = 0;
                x2 = width / 3;
                y1 += height / 3;
                y2 = y1 + height / 3;

            }




            //disegno cerchio o x se il la matrice_gioco è 0 o 1
            Paint pennaCerchio = new Paint();
            pennaCerchio.setStyle(Paint.Style.STROKE);
            pennaCerchio.setColor(Color.BLACK);
            pennaCerchio.setStrokeWidth(10);
            pennaCerchio.setTypeface(FontFactory.INSTANCE.getFont(getContext()));

            Resources res = getContext().getResources();


            for (int  i = 0; i < righe; i++) {
                for (int j = 0; j < colonne; j++) {

                    if (matrice_gioco[j][i]==0) {
                        canvas.drawCircle(rettangoli[j][i].centerX(), rettangoli[j][i].centerY(), dipToPixels(getContext(),50), pennaCerchio);



                    }
                    if(matrice_gioco[j][i]==1){

                        canvas.drawLine( rettangoli[j][i].left,rettangoli[j][i].top,rettangoli[j][i].right,rettangoli[j][i].bottom,pennaCerchio);
                        canvas.drawLine(rettangoli[j][i].left,rettangoli[j][i].bottom,rettangoli[j][i].right,rettangoli[j][i].top,pennaCerchio);

                    }
                }
            }





        }

    public void showDialog(int vittoria){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        if(vittoria==1){
            alertDialogBuilder.setTitle("Ha vinto il Giocatore X !  ");
        }
        if(vittoria==0){
            alertDialogBuilder.setTitle("Ha vinto il Giocatore O ");
        }
        if(vittoria==-1){
            alertDialogBuilder.setTitle("Pareggio");
        }
        alertDialogBuilder.setMessage("Volete rigiocare ?");


        alertDialogBuilder.setNeutralButton("Ritorna al Menu",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent in= new Intent(getContext(),MenuActivity.class);
                getContext().startActivity(in);
                Activity activity=(Activity) getContext();
                activity.finish();

            }
        });
        alertDialogBuilder.setPositiveButton("Rigioca",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getContext().startActivity(new Intent(getContext(), SinglePlayerActivity.class));
                Activity activity=(Activity) getContext();
                activity.finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

}


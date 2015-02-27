package it.android.alessiorighetti.trismultiplayer.service;

import android.util.Log;

/**
 * Created by Alessio on 06/02/2015.
 */
public class Game {
    public static int whoWin(int[][] matrice_gioco){
        int vincitore=-1;

        //detereminare le righe
        for(int i=0;i<matrice_gioco.length;i++){
            if(matrice_gioco[0][i]==0 && matrice_gioco[1][i]==0 && matrice_gioco[2][i]==0){
                return 0;
            }
            if(matrice_gioco[0][i]==1 && matrice_gioco[1][i]==1 && matrice_gioco[2][i]==1){
                return 1;

            }
        }

        //determinare colonne
        for(int i=0;i<matrice_gioco.length;i++){
            if(matrice_gioco[i][0]==0 && matrice_gioco[i][1]==0 && matrice_gioco[i][2]==0){
                return 0;
            }
            if(matrice_gioco[i][0]==1 && matrice_gioco[i][1]==1 && matrice_gioco[i][2]==1){
                return 1;

            }
        }

        //determinare diagonali

        if(matrice_gioco[0][0]==0 && matrice_gioco[1][1]==0 && matrice_gioco[2][2]==0){
            return 0;
        }
        else if(matrice_gioco[0][0]==1 && matrice_gioco[1][1]==1 && matrice_gioco[2][2]==1){
            return 1;
        }
        else if(matrice_gioco[2][0]==0 && matrice_gioco[1][1]==0 && matrice_gioco[0][2]==0){
            return 0;
        }
        else if(matrice_gioco[2][0]==1 && matrice_gioco[1][1]==1 && matrice_gioco[0][2]==1){
            return 1;
        }


        return vincitore;
    }
    public static  boolean isFull(int[][] matrice_gioco){
        boolean pieno=true;
        for(int i=0;i<matrice_gioco.length;i++){
            for(int j=0;j<matrice_gioco[0].length;j++){
                if(matrice_gioco[j][i]==-1){
                    return false;
                }
            }
        }
        return pieno;
    }
    public static void stampa_matrice(int[][] matrice_gioco){
        for(int i=0;i<matrice_gioco.length;i++){
            for(int j=0;j<matrice_gioco[0].length;j++){
                Log.d("matrice",matrice_gioco[j][i]+" ");
                Log.d("matrice","\n");
            }
        }
    }

}

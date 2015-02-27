package it.android.alessiorighetti.trismultiplayer.view;



import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

import it.android.alessiorighetti.trismultiplayer.R;

/**
 * Created by Alessio on 09/02/2015.
 */

public class ProgressAlertDialog extends android.support.v4.app.DialogFragment {

    /**
     * Callback interface that the listening activity should implement to receive information
     * from the ProgressDialog. In our case we want to stop the task when the user press
     * back.
     */
    public interface OnProgressDialogListener {

        /**
         * The task was cancelled
         */
        void taskCancelled();

    }

    /*
     * The Listener of the  ProgressDialog about the back key
     */
    private DialogInterface.OnKeyListener mOnKeyListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // if the Activity implements the listener interface we save its reference
        if (activity instanceof DialogInterface) {
            final OnProgressDialogListener listener = (OnProgressDialogListener) activity;
            mOnKeyListener = new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if( keyCode == KeyEvent.KEYCODE_BACK){
                        // The key is back so we return true to tells we intercept it
                        // and notify the listener
                        listener.taskCancelled();
                        dialog.dismiss();
                        return true;
                    }
                    return false;
                }
            };
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // We create a ProgressDialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        // We DON'T close the Dialog if we touch outside
        progressDialog.setCanceledOnTouchOutside(false);
        // We manage the back button
        progressDialog.setOnKeyListener(mOnKeyListener);
        return progressDialog;
    }

}


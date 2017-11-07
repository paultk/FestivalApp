package com.example.paulthomaskorsvold.festivalappclean.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by paulthomaskorsvold on 11/2/17.
 */

public class Utils {

    /**
     * method showing a dialog with yes no buttons
     * @param title
     * @param message
     * @param context
     */
    public static void showConfirmDialog(String title, String message, Context context) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    /**
     * Displays modal with content, used for showing list items.
     * @param message
     * @param title
     * @param context
     */
    public static void showModal(String message, String title, Context context) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle(title);
// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

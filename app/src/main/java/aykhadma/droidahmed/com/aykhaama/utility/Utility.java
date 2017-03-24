package aykhadma.droidahmed.com.aykhaama.utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.Bidi;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.profile.Login;

/**
 * Created by ahmed on 8/9/2016.
 */
public class Utility {
    public static int searchVal = 0;

    public void sendMail() {

    }

    public static void showValidateDialog(String message, Context mContext) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        alertDialogBuilder.setMessage(message).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void showFailureDialog(Context mContext, final boolean back) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        alertDialogBuilder
                .setMessage(
                        mContext.getResources().getString(R.string.failure_ws))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertDialogBuilder.setCancelable(true);
                        if (back) {
                            //      Utility.deleteFormBackStack();
                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void setDevice(Activity context) {
//        if(new StoreData(context).getDeviceId().equals("1")){
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("pppp", token);
        new StoreData(context).setDeviceId(token);
//        }


    }

    public static int heightScrren(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return (displaymetrics.heightPixels / 4);
    }

    public static int widthScreens(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return ((displaymetrics.widthPixels)) - 30;
    }

    public static int widthScrren(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return ((displaymetrics.widthPixels) * 2) - 30;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    public static void callNum() {

    }

    public static void confirmDialog(final Context context, final String number) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(context.getResources().getString(R.string.confirm_call));

        // set the custom dialog components - text, image and button


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);

        // if button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();


    }
    public void loginDialog(final Context context, final String number){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.login_dialog);
        dialog.setTitle(context.getResources().getString(R.string.login_first_title));

        // set the custom dialog components - text, image and button


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);

        // if button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Login.class);
                intent.putExtra("login","buy");
                 context.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();


    }
    public static int[] widthScreen(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x-100;
        int height = size.y-180;
        return new int[]{width,height};
    }
    public static int[] widthScrens(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
         int width = size.x;
         int height = (size.x/3);
        return new int[]{width,height};
    }
    public void langChoosen(Activity activity,String lang){
        Locale myLocale = new Locale(lang);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        Bidi bidi = new Bidi(lang,
                Bidi.DIRECTION_DEFAULT_RIGHT_TO_LEFT);
        bidi.isLeftToRight();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    public static void openBrowser(Activity activity,String url){
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(browserIntent);
        }catch (Exception e){

        }
    }
    public static void sendEmail(Activity activity,String email){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        activity. startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}

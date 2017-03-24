package aykhadma.droidahmed.com.aykhaama.custom_service;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import aykhadma.droidahmed.com.aykhaama.R;

/**
 * Created by ahmed on 8/4/2016.
 */
public class CustomerServiceMain extends Fragment {
    ImageView imgCall,imgChat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.customer_service,container,false);
        declare(view);
        action();
        return view;
    }

    private void declare(View view){
        imgCall = (ImageView) view.findViewById(R.id.imgCall);
        imgChat = (ImageView)view.findViewById(R.id.imgChat);

    }
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;

    private void action(){
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentapiVersion >= 22) {
                    onCall();
                }else {
                    startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:67771992")));

                }
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "info@ai5adma.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "support");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Send email"));
            }
        });
    }
//    public void onChat() {
//        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);
//
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    getActivity(),
//                    new String[]{Manifest.permission.SEND_SMS},
//                    124);
//        } else {
//            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:12345678901")));
//        }
//    }
    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                   123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:12345678901")));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
}

package aykhadma.droidahmed.com.aykhaama.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.ChangeProfileMain;
import aykhadma.droidahmed.com.aykhaama.profile_api.ChangePassApi;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 8/10/2016.
 */
public class ForgetPass extends AppCompatActivity {
    EditText edForgetPass;
     Button btnForgetPass;
    ImageView imgBack,imgCancel;
    OnLoadingComplete onLoadingComplete;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass);
        declare();
    }

    private void declare(){
        edForgetPass = (EditText) findViewById(R.id.edForgetPass);
        btnForgetPass = (Button) findViewById(R.id.btnForgetPass);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        addListener();
    }
    private void addListener(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionForgetPass();
            }
        });
    }

    private void actionForgetPass(){

        if (Utility.isNetworkConnected(ForgetPass.this)) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    final ChangeProfileMain userRegisterResponse = (ChangeProfileMain) result;
                    if(userRegisterResponse.isSuccess()) {
                        Toast.makeText(ForgetPass.this,userRegisterResponse.getData(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgetPass.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(ForgetPass.this,userRegisterResponse.getData(),Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(ForgetPass.this, false);
                }
            };

            ChangePassApi task = new ChangePassApi(ForgetPass.this, onLoadingComplete);
            task.execute(edForgetPass.getText().toString());

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    ForgetPass.this);
        }

    }
}

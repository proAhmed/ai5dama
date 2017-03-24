package aykhadma.droidahmed.com.aykhaama.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.ChangeProfileMain;
import aykhadma.droidahmed.com.aykhaama.model.changePassMain;
import aykhadma.droidahmed.com.aykhaama.profile_api.ActivateCodeApi;
import aykhadma.droidahmed.com.aykhaama.profile_api.ChangePassApi;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 8/10/2016.
 */
public class ActivateAccount extends AppCompatActivity {
    EditText edUserName,edCode;
     Button btnActivateCode;
    ImageView imgBack,imgCancel;
    OnLoadingComplete onLoadingComplete;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Utility utility = new Utility();
        utility.langChoosen(this,new StoreData(this).getLang());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate_acount);
        declare();
    }

    private void declare(){
        edUserName = (EditText) findViewById(R.id.edUserName);
        edCode = (EditText) findViewById(R.id.edCode);
        btnActivateCode = (Button) findViewById(R.id.btnActivateCode);
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
        btnActivateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edCode.getText().toString().equals("")&&edUserName.getText().toString().equals("")){
                    Toast.makeText(ActivateAccount.this,"Please Insert data",Toast.LENGTH_LONG).show();
                }else {
                    actionForgetPass();

                }
            }
        });
    }

    private void actionForgetPass(){

        if (Utility.isNetworkConnected(ActivateAccount.this)) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    final changePassMain userRegisterResponse = (changePassMain) result;
                    if(userRegisterResponse.isSuccess()) {
                        Toast.makeText(ActivateAccount.this,userRegisterResponse.getData(),Toast.LENGTH_LONG).show();
                     new StoreData(ActivateAccount.this).setActivate(2);
                        Intent intent = new Intent(ActivateAccount.this, Login.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(ActivateAccount.this,userRegisterResponse.getData(),Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(ActivateAccount.this, false);
                }
            };

            ActivateCodeApi task = new ActivateCodeApi(ActivateAccount.this, onLoadingComplete);
            task.execute(edUserName.getText().toString(),edCode.getText().toString());

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    ActivateAccount.this);
        }

    }
}

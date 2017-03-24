package aykhadma.droidahmed.com.aykhaama.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.controller.AddSignInCartService;
import aykhadma.droidahmed.com.aykhaama.controller.DatabaseHelper;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.UserLogin;
import aykhadma.droidahmed.com.aykhaama.model.UserLoginResponse;
import aykhadma.droidahmed.com.aykhaama.profile_api.SignInApi;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 8/10/2016.
 */
public class Login extends AppCompatActivity {
    EditText edUserName,edPass;
    TextView tvForgetPass,tvSignUp;
    Button btnSignIn;
    ImageView imgBack,imgCancel;
    OnLoadingComplete onLoadingComplete;
    DatabaseHelper databaseHelper;
    public void onCreate(Bundle savedInstanceState) {
        Utility utility = new Utility();
        utility.langChoosen(this,new StoreData(this).getLang());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        declare();
        databaseHelper = new DatabaseHelper(this);
        addListener();

    }

    private void declare(){
        edUserName = (EditText) findViewById(R.id.edUserName);
        edPass = (EditText) findViewById(R.id.edPass);
        tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
    }
    private void addListener(){
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgetPass.class);
                startActivity(intent);
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getExtras()!=null) {

                    Intent intent = new Intent(Login.this, Register.class);
                    intent.putExtra("cart","cart");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionLogin();
            }
        });

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
    }

    private void actionLogin(){


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Utility.isNetworkConnected(Login.this)) {

                    onLoadingComplete = new OnLoadingComplete() {

                        @Override
                        public void onSuccess(Object result) {
                            final UserLoginResponse userRegisterResponse = (UserLoginResponse) result;
                            UserLogin userRegister = userRegisterResponse.getData();
                            if(userRegisterResponse.isSuccess()) {
                                new StoreData(Login.this).setActivate(3);

                                String token = userRegister.getToken();
                                String picture = userRegister.getPicture();
                                String email = userRegister.getEmail();
                                String city = userRegister.getCity();
                                String name = userRegister.getFullName();
                                String country = userRegister.getCity();
                                userRegister.getUserName();
                                String mobile = userRegister.getMobile();
                                new StoreData(Login.this).setToken(token);
                                new StoreData(Login.this).setPic(picture);
                                new StoreData(Login.this).setEmail(email);
                                new StoreData(Login.this).setFullName(name);
                                new StoreData(Login.this).setMobile(mobile);
                                new StoreData(Login.this).setMyId(userRegister.getID());
                                new StoreData(Login.this).setCity(city);
                                new StoreData(Login.this).setCountry(country);

                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        Login.this);

                                alertDialogBuilder
                                        .setMessage(getResources().getString(R.string.success_login))
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int id) {
                                                        alertDialogBuilder
                                                                .setCancelable(true);



                                                        if (userRegisterResponse.isSuccess()) {
                                                            if((databaseHelper.getWholeSaleCart()!=null|databaseHelper.getCart()!=null)){
                                                                    Log.d("iii1", "add");
                                                                Log.d("iii1","add");
                                                                Intent intent = new Intent(Login.this,AddSignInCartService.class);
                                                                startService(new Intent(intent));
                                                                finish();
                                                                edPass.setText("");
                                                                edUserName.setText("");

                                                            }else{
                                                                Intent intent = new Intent(Login.this, MainActivity.class);
                                                                startActivity(intent);
                                                                edPass.setText("");
                                                                edUserName.setText("");
                                                                finish();
                                                            }

                                                        }

                                                    }
                                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }else{
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        Login.this);

                                alertDialogBuilder
                                        .setMessage(userRegisterResponse.getError().toString())
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int id) {
                                                        alertDialogBuilder
                                                                .setCancelable(true);
                                                      finish();



                                                    }
                                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                            }
                        }

                        @Override
                        public void onFailure() {
                            Utility.showFailureDialog(Login.this, false);
                        }
                    };
                    if(getIntent().getExtras()!=null) {

                        SignInApi task = new SignInApi(Login.this, onLoadingComplete);
                        ArrayList<CartQuantity> cartQuantities = databaseHelper.getCart();
                        ArrayList<CartQuantity> cartQuantitiess = databaseHelper.getWholeSaleCart();

                        ArrayList<CartQuantity> cartQuantityArrayList = new ArrayList<>();
                        if (cartQuantities != null) {
                            cartQuantityArrayList.addAll(cartQuantities);
                        }
                        if (cartQuantitiess != null) {
                            cartQuantityArrayList.addAll(cartQuantitiess);
                        }
                        task.execute(edUserName.getText().toString(), edPass.getText().toString(), cartQuantityArrayList);

                    }else {
                        SignInApi task = new SignInApi(Login.this, onLoadingComplete);
                        ArrayList<CartQuantity> cartQuantityArrayList = new ArrayList<>();
                        task.execute(edUserName.getText().toString(), edPass.getText().toString(), cartQuantityArrayList);
                    }
                } else {
                    Utility.showValidateDialog(
                            getResources().getString(R.string.failure_ws),
                            Login.this);
                }
            }
        });
    }
}

package aykhadma.droidahmed.com.aykhaama.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.controller.AddSignInCartService;
import aykhadma.droidahmed.com.aykhaama.controller.DatabaseHelper;
import aykhadma.droidahmed.com.aykhaama.controller.RoundedImageView;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.Service;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.User;
import aykhadma.droidahmed.com.aykhaama.model.UserRegister;
import aykhadma.droidahmed.com.aykhaama.model.UserRegisterResponse;
import aykhadma.droidahmed.com.aykhaama.profile_api.SignUpApi;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ahmed on 8/10/2016.
 */
public class Register extends AppCompatActivity {
    private static final int SELECT_PHOTO = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    EditText edUserName, edPass, edEmail, edMobile, edCountry, edCity;
    Button btnSignUp;
    ImageView imgBack, imgCancel;
    RoundedImageView imgUser;
    String data;
    Service service;
    String pic = "pic";
    User user;
    OnLoadingComplete onLoadingComplete;
    DatabaseHelper databaseHelper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public void onCreate(Bundle savedInstanceState) {
        Utility utility = new Utility();
        utility.langChoosen(this,new StoreData(this).getLang());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        declare();
        addListener();
        databaseHelper = new DatabaseHelper(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
     }

    private void declare() {
        edUserName = (EditText) findViewById(R.id.edUserName);
        edPass = (EditText) findViewById(R.id.edPass);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edMobile = (EditText) findViewById(R.id.edMobile);
        edCountry = (EditText) findViewById(R.id.edCountry);
        edCity = (EditText) findViewById(R.id.edCity);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        imgUser = (RoundedImageView) findViewById(R.id.imgUser);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(80, TimeUnit.SECONDS)
                .readTimeout(80, TimeUnit.SECONDS)
                .writeTimeout(80, TimeUnit.SECONDS).build();

        // Change base URL to your upload server URL.

        service = new Retrofit.Builder().baseUrl("http://ai5adma.com/API/ar/").client(client).build().create(Service.class);

    }

    private void addListener() {
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
        actionRegister();

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per();
            }
        });
    }
    private void per() {
        if (Build.VERSION.SDK_INT >= 23) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(Register.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(Register.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(Register.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                ActivityCompat.requestPermissions(Register.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay! Do the
            // contacts-related task you need to do.
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        } else {

            // permission denied, boo! Disable the
            // functionality that depends on this permission.
        }
        return;
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imgUser.setImageBitmap(selectedImage);
//                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                        byte[] byteArray = byteArrayOutputStream .toByteArray();
//                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                        Log.d("pppp",encoded);
                        //   uploadImagess(encoded);

                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP

                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        //  File finalFile = new File(getRealPathFromURI(tempUri));
                        //  uploadImagess(getRealPathFromURI(imageUri));
//                        if (reqCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

                        //   android.net.Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        android.database.Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                        if (cursor == null)
                            return;

                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();

                        File file = new File(filePath);

                        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("Image", file.getName(), reqFile);
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), "Image");

//            Log.d("THIS", data.getData().getPath());

                        retrofit2.Call<okhttp3.ResponseBody> req = service.postImage(body, name);
                        req.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.d("pppuuu",response.toString());
                                BufferedReader reader = null;
                                StringBuilder sb = new StringBuilder();
                                try {

                                    reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));

                                    String line;

                                    try {
                                        while ((line = reader.readLine()) != null) {
                                            sb.append(line);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                String result = sb.toString();
                                Log.d("mmm",result);
                                if(result.toLowerCase().contains("media/")){
                                    new StoreData(Register.this).setPic(result);
                                }
//                                    try {
//                                         Log.d("pppuuu3", response.body().string());
//                                        Log.d("pppuuu4", response.message());
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                        Log.d("pppeee",e.toString());
//                                    }
//
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    pic =  jsonObject.getString("data");
                                    new StoreData(Register.this).setPic(pic);
                                    Log.d("mmm22",pic);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
//                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    private void actionRegister(){


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User();
                user.setFullName(edUserName.getText().toString());
                user.setUserName(edUserName.getText().toString());
                user.setPassword(edPass.getText().toString());
                user.setEmail(edEmail.getText().toString());
                user.setMobile(edMobile.getText().toString());
                user.setCity(edCity.getText().toString());
                user.setCountry(edCountry.getText().toString());
if(pic!=null) {
    if (!pic.equals("pic")) {
        user.setPicture(pic);
    }
}
                Log.d("pppvvv1", user.toString());
                if (Utility.isNetworkConnected(Register.this)) {

                    onLoadingComplete = new OnLoadingComplete() {

                        @Override
                        public void onSuccess(Object result) {
                            UserRegisterResponse userRegisterResponse = (UserRegisterResponse) result;
                            UserRegister userRegister = userRegisterResponse.getData();
                            new StoreData(Register.this).setCity(edCity.getText().toString());
                            new StoreData(Register.this).setCountry(edCountry.getText().toString());

                            if (userRegisterResponse.isSuccess()) {
                                new StoreData(Register.this).setActivate(2);
                                if((databaseHelper.getWholeSaleCart()!=null|databaseHelper.getCart()!=null)){
                                    Log.d("iii1","add");
                                    Intent intent = new Intent(Register.this,AddSignInCartService.class);
                                    startService(new Intent(intent));
                                }else{
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                finish();
                            }else{
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        Register.this);

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




                                                    }
                                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                            }

                        }

                        @Override
                        public void onFailure() {
                            Utility.showFailureDialog(Register.this, false);
                        }
                    };
                if(getIntent().getExtras()!=null){
                    SignUpApi task = new SignUpApi(Register.this, onLoadingComplete);
                    ArrayList<CartQuantity> cartQuantities = databaseHelper.getCart();
                    ArrayList<CartQuantity> cartQuantitiess = databaseHelper.getWholeSaleCart();
                    ArrayList<CartQuantity> cartQuantityArrayList = new ArrayList<>();
                    if(cartQuantities!=null){
                        cartQuantityArrayList.addAll(cartQuantities);
                    }
                    if(cartQuantitiess!=null){
                        cartQuantityArrayList.addAll(cartQuantitiess);
                    }
                    task.execute(user,cartQuantityArrayList);
                    }  else{
                    SignUpApi task = new SignUpApi(Register.this, onLoadingComplete);
                    ArrayList<CartQuantity> cartQuantityArrayList = new ArrayList<>();
                    task.execute(user,cartQuantityArrayList);
                }


                } else {
                    Utility.showValidateDialog(
                            getResources().getString(R.string.failure_ws),
                            Register.this);
                }
            }
        });
    }
}

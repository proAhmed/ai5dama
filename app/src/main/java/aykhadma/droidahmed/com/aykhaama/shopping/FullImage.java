package aykhadma.droidahmed.com.aykhaama.shopping;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.utility.ZoomImageView;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;

public class FullImage extends AppCompatActivity {

    ZoomImageView imgZoom;
    String img;
    ImageView imgBack,imgCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        img = getIntent().getExtras().getString("img");
        imgZoom = (ZoomImageView) findViewById(R.id.imgZoom);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgCart = (ImageView) findViewById(R.id.imgCart);
        Picasso.with(this).load("http://ai5adma.com/API/ar/general/thumb?url=" + img + "&width="+ Utility.widthScreen(this)[0]+ "&height="+ Utility.widthScreen(this)[1]).into(imgZoom);
        cartAdd();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void cartAdd(){
        assert imgCart != null;
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!new StoreData(FullImage.this).getToken().equals("")) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.main, new ShopCartMain()).addToBackStack("").commit();
                }else{
                    Toast.makeText(FullImage.this,"you need to login first",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

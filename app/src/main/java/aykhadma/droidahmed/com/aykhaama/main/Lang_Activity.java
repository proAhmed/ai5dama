package aykhadma.droidahmed.com.aykhaama.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

public class Lang_Activity extends AppCompatActivity {
  RelativeLayout reArabic,reEnglish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility utility = new Utility();
        utility.langChoosen(Lang_Activity.this,new StoreData(this).getLang());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_);

        reArabic = (RelativeLayout) findViewById(R.id.reArabic);
        reEnglish = (RelativeLayout) findViewById(R.id.reEnglish);

        reArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StoreData(Lang_Activity.this).setLang("ar");
                Intent intent = new Intent(Lang_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StoreData(Lang_Activity.this).setLang("en");
                Intent intent = new Intent(Lang_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

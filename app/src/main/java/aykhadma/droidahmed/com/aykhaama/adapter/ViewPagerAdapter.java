package aykhadma.droidahmed.com.aykhaama.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnViewPagerClick;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;

/**
 * Created by ahmed on 11/19/2016.
 */
public class ViewPagerAdapter  extends PagerAdapter {
    Context context;
    ArrayList<String> productList;
    OnViewPagerClick onViewPagerClick;
    public ViewPagerAdapter(Context context, ArrayList<String> productList,OnViewPagerClick onViewPagerClick) {
        this.context = context;
        this.productList = productList;
        this.onViewPagerClick = onViewPagerClick;
    }

    @Override
    public int getCount() {
        return productList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.images_items, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imgItems);


        Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" + productList.get(position) + "&width=" + 30 + "&height=" + (30)).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewPagerClick.onClick(position);
            }
        });


        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}

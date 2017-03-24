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
import aykhadma.droidahmed.com.aykhaama.interfaces.OnImageClick;
import aykhadma.droidahmed.com.aykhaama.model.OfferItems;


/**
 * Created by tesark on 18/8/16.
 */
public class OfferViewPagerAdapter
        extends PagerAdapter
{
    Context context;
    ArrayList<OfferItems> offerList;
    OnImageClick onImageClick;
    public OfferViewPagerAdapter(Context context, ArrayList<OfferItems> offerList, OnImageClick onImageClick) {
        this.context = context;
        this.offerList = offerList;
        this.onImageClick = onImageClick;

    }

    @Override
    public int getCount() {
        return offerList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.offers_pager_item, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
         Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" + offerList.get(position).getPicture()+"&width=400&height=400").into(imageView);
         imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   onImageClick.onImageClick(position);
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

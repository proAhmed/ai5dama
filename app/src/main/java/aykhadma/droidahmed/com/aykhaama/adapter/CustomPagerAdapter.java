package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
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
import aykhadma.droidahmed.com.aykhaama.model.SlideShow;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 7/26/2016.
 */
public class CustomPagerAdapter extends PagerAdapter {

    Activity mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<SlideShow>imageArray;
    public CustomPagerAdapter(Activity context,ArrayList<SlideShow>imageArray) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageArray = imageArray;
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Picasso.with(mContext).load("http://ai5adma.com/API/ar/general/thumb?url="
                + imageArray.get(position).getPicture() + "&width=" + Utility.widthScrren(mContext) + "&height="+Utility.heightScrren(mContext)*2.7).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

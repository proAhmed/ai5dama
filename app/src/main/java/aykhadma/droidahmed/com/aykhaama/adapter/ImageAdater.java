package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.controller.TouchImageView;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


/**
 * Created by ahmed on 10/6/2016.
 */
public class ImageAdater extends PagerAdapter {

    private Activity activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public ImageAdater(Activity activity,
                       ArrayList<String> imagePaths) {
        this.activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final TouchImageView imgDisplay;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.image_item, container,
                false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        final ProgressBar progressBar =(ProgressBar)viewLayout.findViewById(R.id.item_spinner);
        progressBar.setIndeterminate(true);

        //imgDisplay.setBackground(_activity.getResources().getDrawable(R.drawable.alert_2));
//        Picasso.with(_activity).load(Keys.IMAGE_URL +_imagePaths.get(position)).placeholder(_activity.getResources().getDrawable(R.drawable.rotate_pro)).into(imgDisplay);
        //        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
//        imgDisplay.setImageBitmap(bitmap);
//
        // close button click event
//       final Target target = new Target() {
//
//
//
//			@Override
//			public void onBitmapFailed() {
//				// TODO Auto-generated method stub
//
//			}
//
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onBitmapLoaded(Bitmap arg0, LoadedFrom arg1) {
//				// TODO Auto-generated method stub
//				imgDisplay.setBackgroundDrawable(new BitmapDrawable(arg0));
//				progressBar.setVisibility(View.GONE);
//			}
//        };
        Picasso.with(activity)
                .load("http://vente1paris.com/API/fr/general/thumb?url=" +_imagePaths.get(position)+
                        "&width="+ Utility.widthScreen(activity)[0]+"&height="+ Utility.widthScreen(activity)[1])
                .into(imgDisplay, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub

                    }
                });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}

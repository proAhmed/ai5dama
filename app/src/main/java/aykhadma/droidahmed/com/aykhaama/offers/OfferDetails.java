package aykhadma.droidahmed.com.aykhaama.offers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.controller.ShareImage;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnImageClick;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnViewPagerClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.OfferItems;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


/**
 * Created by ahmed on 9/2/2016.
 */
public class OfferDetails extends Fragment
      //  implements ViewPager.OnPageChangeListener,OnViewPagerClick
{
//    ArrayList<String> images;
//
//    ImageView imgOfferDetails;
//
//    OnLoadingComplete ProductListener;
//    SingleProductMain offerMain;
//     ProductListModel  offerItems;
//    OnImageClick onImageClick;
//    TextView tvStartDate,tvExpireDate,tvCall,tvOffersViews,tvTitle;
//    int id,offerId;
//    OnViewPagerClick onViewPagerClick;
//    ImageView imgCall,imgMessage,imgWhats,imgFace,imgTwitter,imgSnap;
//    ShareImage shareImage;
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.offers_fragment_details, container, false);
//        Bundle bundle = getArguments();
//        id = bundle.getInt("id");
//        Log.d("iii",id+"");
//        imgOfferDetails = (ImageView)view.findViewById(R.id.imgOfferDetails);
//        tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);
//        tvExpireDate = (TextView) view.findViewById(R.id.tvExpireDate);
//        tvCall = (TextView) view.findViewById(R.id.tvCall);
//        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        tvOffersViews = (TextView) view.findViewById(R.id.tvOffersViews);
//        declare(view);
//        shareImage = new ShareImage();
//        offers();
//        onViewPagerClick = this;
//        images = new ArrayList<>();
//        return view;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        MainActivity mainActivity = (MainActivity) getActivity();
//
//        ImageView imgSearchAction = (ImageView) mainActivity. findViewById(R.id.imageSearchAction);
//        imgSearchAction.setVisibility(View.GONE);
//        ImageView imageBack = (ImageView) mainActivity. findViewById(R.id.imageBack);
//        imageBack.setVisibility(View.VISIBLE);
//
//    }
//
//    private void declare(View view){
//        imgCall = (ImageView) view.findViewById(R.id.imgCall);
//        imgMessage = (ImageView) view.findViewById(R.id.imgMessage);
//        imgWhats = (ImageView) view.findViewById(R.id.imgWhats);
//        imgFace = (ImageView) view.findViewById(R.id.imgFace);
//        imgTwitter = (ImageView) view.findViewById(R.id.imgTwitter);
//        imgSnap = (ImageView) view.findViewById(R.id.imgSnap);
//     }
//    private void action(final OfferItems offerItems){
//        imgOfferDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),ImageDetails.class);
//                intent.putExtra("pos",0);
//                intent.putExtra("list",images);
//                startActivity(intent);
//            }
//        });
//        imgCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String number = offerItems.getMobile();
////                Utility utility = new Utility();
////                utility.confirmDialog(getContext(),number);
//
//            }
//        });
//
//        imgMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
////                        "mailto",offerItems.getAlias(), null));
////                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
////                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
////                startActivity(Intent.createChooser(emailIntent, "Send email..."));
//            }
//        });
//
//        imgWhats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//                whatsappIntent.setType("text/plain");
//                whatsappIntent.setPackage("com.whatsapp");
//                whatsappIntent.putExtra(Intent.EXTRA_TEXT,offerItems.getTitle());
//                try {
//                    getActivity().startActivity(whatsappIntent);
//                } catch (android.content.ActivityNotFoundException ex) {
//                  }
//            }
//        });
//
//        imgFace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareOnFacebook(offerItems.getTitle());
//            }
//        });
//
//
//        imgTwitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent tweetIntent = new Intent(Intent.ACTION_SEND);
//                    tweetIntent.putExtra(Intent.EXTRA_TEXT, offerItems.getTitle());
//                    tweetIntent.setType("text/plain");
//
//                    PackageManager packManager = getActivity().getPackageManager();
//                    List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);
//
//                    boolean resolved = false;
//                    for (ResolveInfo resolveInfo : resolvedInfoList) {
//                        if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
//                            tweetIntent.setClassName(
//                                    resolveInfo.activityInfo.packageName,
//                                    resolveInfo.activityInfo.name);
//                            resolved = true;
//                            break;
//                        }
//                    }
//                    if (resolved) {
//                        startActivity(tweetIntent);
//                    } else {
//                        Intent i = new Intent();
//                        i.putExtra(Intent.EXTRA_TEXT, offerItems.getTitle());
//                        i.setAction(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(offerItems.getTitle())));
//                        startActivity(i);
//                        Toast.makeText(getActivity(), "Twitter app isn't found", Toast.LENGTH_LONG).show();
//                    }
//                }catch (Exception e){
//
//                }
//            }
//        });
//        imgSnap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insta();
//            }
//        });
//      }
//    private void shareOnFacebook(String content) {
//        try {
//            // TODO: This part does not work properly based on my test
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_TEXT, content);
//            intent.setType("text/plain");
//
//            List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
//            for (ResolveInfo info : matches) {
//                if (info.activityInfo.packageName.toLowerCase().contains("facebook")) {
//                    intent.setPackage(info.activityInfo.packageName);
//                }
//            }
//
//            startActivity(intent);
//
//        } catch (Exception e) {
//            // User doesn't have Facebook app installed. Try sharing through browser.
//        }
//
//        // If we failed (not native FB app installed), try share through SEND
//    }
//    private void insta(){
//        Uri bmpUri = shareImage.getLocalBitmapUri(imgOfferDetails);
//
//        if (bmpUri != null) {
//            // Construct a ShareIntent with link to image
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//            shareIntent.setType("image/*");
//            // Launch sharing dialog for image
//            shareIntent.setPackage("com.instagram.android");
//            startActivity(Intent.createChooser(shareIntent, "Share Image"));
//        } else {
//            // ...sharing failed, handle error
//        }
//    }
//
//
//     @Override
//    public void onPause() {
//        super.onPause();
//        MainActivity mainActivity = (MainActivity) getActivity();
//        ImageView imgBack = (ImageView) mainActivity. findViewById(R.id.imageBack);
//        imgBack.setVisibility(View.GONE);
//    }
//
//    private void addLike(){
//        if (Utility.isNetworkConnected(getActivity())) {
//
//            ProductListener = new OnProcessCompleteListener() {
//
//                @Override
//                public void onSuccess(Object result) {
//                    FavoriteResponse favoriteResponse = (FavoriteResponse) result;
//                    Toast.makeText(getActivity(),favoriteResponse.getIsResult(),Toast.LENGTH_LONG).show();
//                 }
//
//                @Override
//                public void onFailure() {
//                    Utility.showFailureDialog(getActivity(), false);
//                }
//            };
//
//            AddToFavorite task = new AddToFavorite(getActivity(), ProductListener);
//            task.execute(offerId);
//
//        } else {
//            Utility.showValidateDialog(
//                    getResources().getString(R.string.failure_ws),
//                    getActivity());
//        }
//    }
//     @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//    private void offers(){
//        if (Utility.isNetworkConnected(getActivity())) {
//
//            ProductListener = new OnProcessCompleteListener() {
//
//                @Override
//                public void onSuccess(Object result) {
//                    offerMain = (SingleProductMain) result;
//                    offerItems =   offerMain.getData();
//                    offerId = offerItems.getID();
//                    action(offerItems);
//
//                    int height = new StoreData(getActivity()).getDeviceHeight();
//                    int width = new StoreData(getActivity()).getDeviceWidth();
//                    Picasso.with(getActivity()).load("http://vente1paris.com/API/fr/general/thumb?url=" + offerItems.getPicture()+"&width="+width+"&height="+(height/3)).into(imgOfferDetails);
//                    tvCall.setText(offerItems.getMobile());
//                    tvOffersViews.setText(offerItems.getViewed()+"");
//                    tvTitle.setText(offerItems.getTitle());
//                    //                    setUiPageViewController();
//                    images.add(offerItems.getPicture());
//                    try {
//                        String dateStr = offerItems.getCreatedDate();
//                        SimpleDateFormat toFullDate = new SimpleDateFormat("yyyy-MM-dd ");
//                        Date fullDate = toFullDate.parse(dateStr);
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        String shortTimeStr = sdf.format(fullDate);
//                        tvStartDate.setText(shortTimeStr);
//
//                    } catch (Exception e) {
//                        // To change body of catch statement use File | Settings | File Templates.
//                        e.printStackTrace();
//                    }
//                    try {
//                        String dateStr = offerItems.getFinishedDate();
//                        SimpleDateFormat toFullDate = new SimpleDateFormat("yyyy-MM-dd ");
//                        Date fullDate = toFullDate.parse(dateStr);
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        String shortTimeStr = sdf.format(fullDate);
//                        tvExpireDate.setText(shortTimeStr);
//                    } catch (Exception e) {
//                        // To change body of catch statement use File | Settings | File Templates.
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onFailure() {
//                    Utility.showFailureDialog(getActivity(), false);
//                }
//            };
//
//            GetSingleOffer task = new GetSingleOffer(getActivity(), ProductListener,id,""+1);
//            task.execute();
//
//        } else {
//            Utility.showValidateDialog(
//                    getResources().getString(R.string.failure_ws),
//                    getActivity());
//        }
//    }
//
//    private String urlEncode(String s) {
//        try {
//            return URLEncoder.encode(s, "UTF-8");
//        }catch (UnsupportedEncodingException e) {
//            Log.wtf("mm", "UTF-8 should always be supported", e);
//            return "";
//        }
//    }
//
//
//    @Override
//    public void onClick(int pos) {
//
//    }
}

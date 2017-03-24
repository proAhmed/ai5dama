package aykhadma.droidahmed.com.aykhaama.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.airlines.AirLineScreen;
import aykhadma.droidahmed.com.aykhaama.companies.Companies;
import aykhadma.droidahmed.com.aykhaama.custom_service.CustomerServiceMain;
import aykhadma.droidahmed.com.aykhaama.favorite.FavoriteMain;
import aykhadma.droidahmed.com.aykhaama.home.HomeFragment;
import aykhadma.droidahmed.com.aykhaama.hotels.HotelMain;
import aykhadma.droidahmed.com.aykhaama.model.SlidingMenuItem;
import aykhadma.droidahmed.com.aykhaama.my_account.MyProfile;
import aykhadma.droidahmed.com.aykhaama.open_market.OpenMarket;
import aykhadma.droidahmed.com.aykhaama.profile.EditProfile;
import aykhadma.droidahmed.com.aykhaama.profile.Login;
import aykhadma.droidahmed.com.aykhaama.shopping.ShopCategory;
import aykhadma.droidahmed.com.aykhaama.social.Social;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.videos.VideoMain;
import aykhadma.droidahmed.com.aykhaama.whosale.WholeSaleMain;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;


/**
 * Created by Hong Thai.
 */
public class MenuFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private ArrayList<SlidingMenuItem> listMenuItems;
    private final static String TAG = "MenuFragment";
    public static Fragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Utility utility = new Utility();
        utility.langChoosen(getActivity(),new StoreData(getActivity()).getLang());
        super.onCreate(savedInstanceState);

        //create menu items
        listMenuItems = new ArrayList<>();
         listMenuItems.add(new SlidingMenuItem(R.drawable.home,getResources().getString(R.string.home), 1));
        listMenuItems.add(new SlidingMenuItem(R.drawable.profile,getResources().getString(R.string.my_profile),2));
        listMenuItems.add(new SlidingMenuItem(R.drawable.fav,getResources().getString(R.string.my_favourite),3));
        listMenuItems.add(new SlidingMenuItem(R.drawable.shoppingcar,getResources().getString(R.string.my_shop_cart),4));
        listMenuItems.add(new SlidingMenuItem(R.drawable.settings,getResources().getString(R.string.settings),5));
        listMenuItems.add(new SlidingMenuItem(0,getResources().getString(R.string.filter_list),6));
        listMenuItems.add(new SlidingMenuItem(R.drawable.shopping_icon,getResources().getString(R.string.shopping),7));
        listMenuItems.add(new SlidingMenuItem(R.drawable.open_market_icon,getResources().getString(R.string.open_market),8));
        listMenuItems.add(new SlidingMenuItem(R.drawable.wholesale_icon,getResources().getString(R.string.wholesale),9));
//
        listMenuItems.add(new SlidingMenuItem(R.drawable.companies,getResources().getString(R.string.companies),10));
        listMenuItems.add(new SlidingMenuItem(R.drawable.videos,getResources().getString(R.string.videos),11));
        listMenuItems.add(new SlidingMenuItem(R.drawable.airlines, getResources().getString(R.string.airlines),12));
        listMenuItems.add(new SlidingMenuItem(R.drawable.hotels, getResources().getString(R.string.hotels),13));
        listMenuItems.add(new SlidingMenuItem(0, getResources().getString(R.string.social_media),14));
        listMenuItems.add(new SlidingMenuItem(R.drawable.facebook, getResources().getString(R.string.facebook),15));
        listMenuItems.add(new SlidingMenuItem(R.drawable.twitter, getResources().getString(R.string.twitter),16));
        listMenuItems.add(new SlidingMenuItem(R.drawable.instagrame, getResources().getString(R.string.instagram),17));
        listMenuItems.add(new SlidingMenuItem(R.drawable.youtube, getResources().getString(R.string.youtube),18));
        listMenuItems.add(new SlidingMenuItem( getResources().getString(R.string.contact_us),19));
        listMenuItems.add(new SlidingMenuItem( getResources().getString(R.string.customer_services),20));

//for (int i=0;i<listMenuItems.size();i++){
 //}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);

        View header = inflater.inflate(R.layout.view_head,  listView, false);
        header.setMinimumHeight(40);
        listView.addHeaderView(header);
         return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListViewAdapter();
    }

    private void setListViewAdapter() {
        SlidingMenuAdapter adapter = new SlidingMenuAdapter(getActivity(), R.layout.item_menu, listMenuItems);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        listView.setOnItemClickListener(onItemClickListener());
        Log.i(TAG, "create adapter " + listMenuItems.size());
    }

    /**
     * Go to fragment when menu item clicked!
     *
     * @return
     */
    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                MainActivity mainActivity = (MainActivity) getActivity();

switch (position){

    case 1:
        ft.add(R.id.main, new HomeFragment()).addToBackStack("");
        ft.commit();

        mainActivity. toggle();
        break;
    case 2:
        if(new StoreData(getActivity()).getToken().equals("")){
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        }else  if(new StoreData(getActivity()).getToken().equals("")){
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        }else  if(!new StoreData(getActivity()).getToken().equals("")){
          ft.add(R.id.main, new MyProfile());
          ft.commit();
        }else{
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        }



        mainActivity. toggle();
         break;
    case 3:
        if(!new StoreData(getActivity()).getToken().equals("")) {
            ft.add(R.id.main, new FavoriteMain()).addToBackStack("");
            ft.commit();
        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.need_login),Toast.LENGTH_LONG).show();
        }
        mainActivity. toggle();
        break;
    case 4:
//        if(!new StoreData(getActivity()).getToken().equals("")) {
            ft.add(R.id.main, new ShopCartMain()).addToBackStack("");
            ft.commit();
//        }else{
//                Toast.makeText(getActivity(),getResources().getString(R.string.need_login),Toast.LENGTH_LONG).show();
//            }
        mainActivity. toggle();
        break;


    case 5:

//        ft.add(R.id.main, new Settings()).addToBackStack("");
//        ft.commit();
//        mainActivity. toggle();
        if(!new StoreData(getActivity()).getToken().equals("")) {

            Intent intent = new Intent(getActivity(), EditProfile.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        }
//        Fragment fragmentProduct = new MyShoppingCart();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("link", "https://www.facebook.com/jm3eia");
//
//        fragmentProduct.setArguments(bundle);
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .add(R.id.mainFragment, fragmentProduct).addToBackStack("")
//                .commitAllowingStateLoss();
//        mainActivity. toggle();

//        ft.add(R.id.mainFragment, new FragmentProductCart());
//        ft.commit();
//        MainActivity mainActivity4 = (MainActivity) getActivity();
//
        mainActivity. toggle();
        break;

    case 6:
//        Fragment webFragment = new WebFragment();
//        Bundle bundles = new Bundle();
//        bundles.putSerializable("link", "https://twitter.com/@jm3eia");
//
//        webFragment.setArguments(bundles);
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .add(R.id.mainFragment, webFragment).addToBackStack("")
//                .commitAllowingStateLoss();
////        ft.add(R.id.mainFragment, new FragmentProductCart());
////        ft.commit();
////        MainActivity mainActivity4 = (MainActivity) getActivity();
////
//        mainActivity. toggle();
//        break;
    case 7:

        ft.add(R.id.main, new ShopCategory()).addToBackStack("");
        ft.commitAllowingStateLoss();

        mainActivity. toggle();
        break;
//        Bundle bundless = new Bundle();
//        bundless.putSerializable("link", "https://www.instagram.com/jm3eia/");
//
//        webFragments.setArguments(bundless);
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .add(R.id.mainFragment, webFragments).addToBackStack("")
//                .commitAllowingStateLoss();
//
//        mainActivity. toggle();
//        ft.add(R.id.mainFragment, new FragmentProductCart());
//        ft.commit();
//        MainActivity mainActivity4 = (MainActivity) getActivity();
//
//        mainActivity4. toggle();
    case 8:
        ft.add(R.id.main, new OpenMarket()).addToBackStack("");
        ft.commitAllowingStateLoss();

        mainActivity. toggle();

        break;
    case 9:
        ft.add(R.id.main, new WholeSaleMain()).addToBackStack("");
        ft.commitAllowingStateLoss();

        mainActivity. toggle();

         break;
    case 10:
        ft.add(R.id.main, new Companies()).addToBackStack("");
        ft.commitAllowingStateLoss();

        mainActivity. toggle();

        break;
    case 11:
        ft.add(R.id.main, new VideoMain()).addToBackStack("");
        ft.commitAllowingStateLoss();

        mainActivity. toggle();

           break;

    case 12:
        ft.add(R.id.main, new AirLineScreen()).addToBackStack("");
        ft.commitAllowingStateLoss();

        mainActivity. toggle();

        break;

    case 13:
        ft.add(R.id.main, new HotelMain()).addToBackStack("");
        ft.commitAllowingStateLoss();

        mainActivity. toggle();

        break;
    case 14:

        break;
    case 15:
        Fragment face = new Social();
        Bundle bundles = new Bundle();
        bundles.putSerializable("link", "https://www.facebook.com/Ai5adma/");

        face.setArguments(bundles);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.main, face).addToBackStack("")
                .commitAllowingStateLoss();

         mainActivity. toggle();

        break;

    case 16:
        Fragment twitter = new Social();
        Bundle bundless = new Bundle();
        bundless.putSerializable("link", "https://twitter.com/Ai5admaq8");

        twitter.setArguments(bundless);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.main, twitter).addToBackStack("")
                .commitAllowingStateLoss();

        mainActivity. toggle();
         break;

    case 17:
        Fragment instagram = new Social();
        Bundle bundlesss = new Bundle();
        bundlesss.putSerializable("link", "https://www.instagram.com/ai5adma");

        instagram.setArguments(bundlesss);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.main, instagram).addToBackStack("")
                .commitAllowingStateLoss();

        mainActivity. toggle();
        break;

    case 18:
        Fragment youTube = new Social();
        Bundle bundlessss = new Bundle();
        bundlessss.putSerializable("link", "https://www.youtube.com/playlist?list=PLI0vm74Zj0JnONdDZenUBafdiEII55FsE");

        youTube.setArguments(bundlessss);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.main, youTube).addToBackStack("")
                .commitAllowingStateLoss();

        mainActivity. toggle();
        break;

    case 19:


    case 20:
        ft.add(R.id.main, new CustomerServiceMain()).addToBackStack("");
        ft.commitAllowingStateLoss();

        mainActivity. toggle();

        break;
}
//                SlidingMenuItem item = (SlidingMenuItem) parent.getItemAtPosition(position);
//                ((MainActivity) getActivity()).transactionFragments(ContactFragment.newInstance(item.getMenuItemName()),
//                        R.id.mainFragment);
            }
        };
    }

    /**
     * private class to make a listview adapter based on ArrayAdapter
     */
    private class SlidingMenuAdapter extends ArrayAdapter<SlidingMenuItem> {

        private FragmentActivity activity;
        private ArrayList<SlidingMenuItem> items;

        public SlidingMenuAdapter(FragmentActivity activity, int resource, ArrayList<SlidingMenuItem> objects) {
            super(activity, resource, objects);
            this.activity = activity;
            this.items = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            // If holder not exist then locate all view from UI file.
        //    if (convertView == null) {
                // inflate UI from XML file

                if (items.get(position).getPositions() == 6||items.get(position).getPositions() == 14||items.get(position).getPositions() == 19
                      ) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_menu_two, null);
                } else  {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_menu, null);

                }
                              // get all UI view
                holder = new ViewHolder(convertView);
                // set tag for holder
             //   convertView.setTag(holder);
//            } else {
//                // if holder created, get tag from view
//                holder = (ViewHolder) convertView.getTag();
//            }

            holder.itemName.setText(getItem(position).getMenuItemName());
            if(getItem(position).getImageResource()!=0)
           holder.img.setImageResource(getItem(position).getImageResource());

            return convertView;
        }

        private class ViewHolder {
            private TextView itemName;
            private ImageView img;

            public ViewHolder(View v) {
                itemName = (TextView) v.findViewById(R.id.name);
                img = (ImageView) v.findViewById(R.id.img);
             }
        }
    }

//    private  void dialog(){
//          final Dialog dialog = new Dialog(getActivity());
//        dialog.setContentView(R.layout.sign_dialog);
//        dialog.setTitle(getResources().getString(R.string.dialog_sure));
//
//        // set the custom dialog components - text, image and button
//        Button yes = (Button) dialog.findViewById(R.id.yes);
//         Button no = (Button) dialog.findViewById(R.id.no);
//
//         // if button is clicked, close the custom dialog
//        yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new StoreData(getActivity()).savLogin("");
//                setListViewAdapter();
//                listMenuItems.remove(11);
//                listMenuItems.add(new SlidingMenuItem(0, getResources().getString(R.string.login), 12));
//
//                dialog.dismiss();
//            }
//        });
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

}
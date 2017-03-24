package aykhadma.droidahmed.com.aykhaama.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItem;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnCancelOrder;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnCartListener;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


/**
 * Created by ahmed on 1/19/2016.
 */
public class CartGridAdapters extends BaseAdapter {

    ArrayList<CartQuantity>  _choices;
    private Context context;
     OnCartListener onCartListener;
    OnCancelOrder onCancelOrder;
    OnAddItem onAddItem;
    Utility utility;

    public CartGridAdapters(Context context, ArrayList<CartQuantity> _choices, OnCartListener onCartListener, OnCancelOrder onCancelOrder, OnAddItem onAddItem) {
        this.context = context;
        this._choices = _choices;
         this.onCartListener = onCartListener;
        this.onCancelOrder = onCancelOrder;
        this.onAddItem  =onAddItem;
        utility = new Utility();
    }

    @Override
    public int getCount() {
        return _choices.size();
    }

    @Override
    public Object getItem(int position) {
        return _choices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int[] cartItem = {1};
        final double[] price = {0};

        final boolean[] cartWatch = {false};
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final TextView tvName,tvPrice,item_change,tvTotalPrice;
        ImageView imgProduct,imgAdd,imgDelete;
         final EditText edNumber;
        ImageView cancel_order;
         if (convertView == null) {
           convertView = inflater.inflate(R.layout.item_cart_mod, parent, false);
        }
//        item_change = (TextView) convertView.findViewById(R.id.item_change);

        imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
        imgAdd = (ImageView) convertView.findViewById(R.id.imgAdd);
        imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
         edNumber = (EditText)  convertView.findViewById(R.id.edNumber);
     cancel_order  = (ImageView) convertView.findViewById(R.id.imgCancel);
//        if( Locale.getDefault().getDisplayLanguage().equals("العربية")) {
//
//            edNumber.setText("" + utility.arabicNumaricCode(String.valueOf(_choices.get(position).getcQuantity())));
//        }else{
            edNumber.setText("" + _choices.get(position).getcQuantity());

//        }


        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelOrder.cancel(_choices.get(position));
            }
        });
        tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        tvTotalPrice = (TextView) convertView.findViewById(R.id.tvTotalPrice);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(edNumber.getText().toString())==1){
                    ++cartItem[0];

                }else{
                    cartItem[0]= Integer.parseInt(edNumber.getText().toString())   ;
                    ++cartItem[0];
                }
//                if( Locale.getDefault().getDisplayLanguage().equals("العربية")){
//                    edNumber.setText("" + utility.arabicNumaricCode(String.valueOf(cartItem[0])));
//                }
//                else{
                    edNumber.setText("" + cartItem[0]);

//                }                price[0] = _choices.get(position).getPrice()*cartItem[0];

                onAddItem.add(Integer.parseInt(edNumber.getText().toString()), _choices.get(position));


                 tvTotalPrice.setText(String.format("%.3f", (_choices.get(position).getPrice() * cartItem[0])) + " " + context.getResources().getString(R.string.dr));

            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("uuuooo",cartItem[0]+"");
if (Integer.parseInt(edNumber.getText().toString()) >1){
    if(Integer.parseInt(edNumber.getText().toString())==1){
        --cartItem[0];

    }else{
        cartItem[0]= Integer.parseInt(edNumber.getText().toString())   ;
        --cartItem[0];
    }
//    if( Locale.getDefault().getDisplayLanguage().equals("العربية")){
//        edNumber.setText("" + utility.arabicNumaricCode(String.valueOf(cartItem[0])));
//    }
//    else{
        edNumber.setText("" + cartItem[0]);

//    }
     price[0] = _choices.get(position).getPrice()*cartItem[0];
    onAddItem.add(Integer.parseInt(edNumber.getText().toString()), _choices.get(position));
     tvTotalPrice.setText(String.format("%.3f", (_choices.get(position).getPrice() * cartItem[0])) + " " + context.getResources().getString(R.string.dr));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        tvPrice.setTextLocale(Locale.ENGLISH);
        tvTotalPrice.setTextLocale(Locale.ENGLISH);
    }
}
            }
        });
        tvName = (TextView) convertView.findViewById(R.id.tvName);

        tvName.setText(_choices.get(position).getName());
        tvPrice.setText(String.format("%.3f", _choices.get(position).getPrice()) + " " + context.getResources().getString(R.string.dr));

         tvTotalPrice.setText(String.format("%.3f", (_choices.get(position).getPrice() * _choices.get(position).getcQuantity())) + " " + context.getResources().getString(R.string.dr));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tvPrice.setTextLocale(Locale.ENGLISH);
            tvTotalPrice.setTextLocale(Locale.ENGLISH);
        }
             Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" + _choices.get(position).getPicture() + "&width=160&height=120").into(imgProduct);




        return convertView;
    }
}

package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddCart;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItems;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.FeaturedHome;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;

/**
 * Created by ahmed on 7/19/2016.
 */
public class HomeFeaturedtListAdapter extends RecyclerView
        .Adapter<HomeFeaturedtListAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<CartQuantity> mDataset;
     Activity context;
    OnShopMainClick onShopMainClick;
    final int[] cartItem = {1};
    final double[] price = {0};
    OnAddItems onAddItem;
    OnAddCart onAddCart;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView imgProduct,imgFav,imgDelete,imgAdd;
        LinearLayout imgCart;
        TextView tvProductName,tvPrice;
        Button btnBuyNow;
        EditText edNumber;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            imgFav = (ImageView) itemView.findViewById(R.id.imgFav);
            imgAdd = (ImageView) itemView.findViewById(R.id.imgAdd);
            btnBuyNow = (Button) itemView.findViewById(R.id.btnBuyNow);
            imgCart = (LinearLayout) itemView.findViewById(R.id.imgCart);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            tvProductName = (TextView) itemView.findViewById(R.id.tvName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            edNumber = (EditText) itemView.findViewById(R.id.edNumber);
            edNumber.setText("1");
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public HomeFeaturedtListAdapter(ArrayList<CartQuantity> myDataset, Activity context,
                                    OnShopMainClick onShopMainClick, OnAddCart onAddCart, OnAddItems onAddItem) {
        this.mDataset = myDataset;
        this.context = context;
        this.onShopMainClick = onShopMainClick;
        this.onAddCart = onAddCart;
        this.onAddItem = onAddItem;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_items, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.tvProductName.setText(mDataset.get(position).getAlias());
        Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" + mDataset.get(position).getPicture() + "&width=160&height=120").into(holder.imgProduct);
        holder.tvPrice.setText(mDataset.get(position).getPrice() + "  KD");

        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShopMainClick.onShopClick(position);
            }
        });

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.edNumber.getText().toString()) == 1) {
                    ++cartItem[0];

                } else {
                    cartItem[0] = Integer.parseInt(holder.edNumber.getText().toString());
                    ++cartItem[0];
                }

                holder.edNumber.setText("" + cartItem[0]);


                price[0] = mDataset.get(position).getPrice() * cartItem[0];
                onAddItem.add(Integer.parseInt(holder.edNumber.getText().toString()), mDataset.get(position));

            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItem[0] > 1) {
                    if (Integer.parseInt(holder.edNumber.getText().toString()) == 1) {
                        --cartItem[0];

                    } else {
                        cartItem[0] = Integer.parseInt(holder.edNumber.getText().toString());
                        --cartItem[0];
                    }

                    holder.edNumber.setText("" + cartItem[0]);


                    price[0] = mDataset.get(position).getPrice() * cartItem[0];
                //    onAddItem.add(Integer.parseInt(holder.edNumber.getText().toString()), mDataset.get(position));

                }
            }
        });
        holder.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCart.onAddCart(position,cartItem[0]);
                holder.imgCart.setBackgroundColor(context.getResources().getColor(R.color.orange));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}

package aykhadma.droidahmed.com.aykhaama.interfaces;


import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;

/**
 * Created by ahmed on 3/1/2016.
 */
public interface OnCartListener {
    void onAddCart(CartQuantity cartQuantity, int num, boolean watch, double price);

}

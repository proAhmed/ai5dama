package aykhadma.droidahmed.com.aykhaama.interfaces;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ahmed on 9/4/2016.
 */
public interface Service {
    @Multipart
    @POST("general/uploadimage")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("Image") RequestBody name);
}

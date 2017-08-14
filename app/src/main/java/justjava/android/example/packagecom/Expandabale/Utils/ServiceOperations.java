package justjava.android.example.packagecom.Expandabale.Utils;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.GET;


public interface ServiceOperations {


    @GET("/BikeFullServiceSubcategories")
    public abstract void bikeFullServiceapi(Callback<JsonObject> jsonObjectCallback);



}


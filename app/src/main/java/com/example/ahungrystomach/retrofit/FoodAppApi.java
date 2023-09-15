package com.example.ahungrystomach.retrofit;

import com.example.ahungrystomach.models.Cart;
import com.example.ahungrystomach.models.CategoryModel;
import com.example.ahungrystomach.models.MealDetailModel;
import com.example.ahungrystomach.models.MealModel;
import com.example.ahungrystomach.models.MessModel;
import com.example.ahungrystomach.models.OrderModel;
import com.example.ahungrystomach.models.SearchModel;
import com.example.ahungrystomach.models.UserModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FoodAppApi {
    @GET("category.php")
    Call<CategoryModel> getCategory();

    @POST("meal.php")
    @FormUrlEncoded
    Call<MealModel> getMeals(@Field("idcate") int idcate);

    @POST("mealdetail.php")
    @FormUrlEncoded
    Call<MealDetailModel> getMealDetail(@Field("id") int id);

    @POST("cart.php")
    @FormUrlEncoded
    Call<MessModel> postCart(@Field("iduser") int id,
                             @Field("amount") int amount,
                             @Field("total") double total,
                             @Field("detail") String detail,
                             @Field("name") String name,
                             @Field("phone") String phone,
                             @Field("address") String address);

    @POST("search.php")
    @FormUrlEncoded
    Call<SearchModel> getSearch(@Field("search") String search);

    @POST("signup.php")
    @FormUrlEncoded
    Call<UserModel> toSignup(@Field("name") String name, @Field("phone") String phone, @Field("pass") String pass);

    @POST("login.php")
    @FormUrlEncoded
    Call<UserModel> toLogin(@Field("phone") String phone, @Field("pass") String pass);
    @POST("order.php")
    @FormUrlEncoded
    Call<OrderModel> orderUser(@Field("iduser") int iduser);
}

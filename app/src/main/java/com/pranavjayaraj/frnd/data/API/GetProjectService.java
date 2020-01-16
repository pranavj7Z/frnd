package com.pranavjayaraj.frnd.data.API;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface GetProjectService
{
    @Multipart
    @POST("/api/uploadMedia/")
    Call<Void> Upload(@Part MultipartBody.Part audio);
}
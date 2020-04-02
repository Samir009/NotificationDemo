package com.samir.luniva.apiservices;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadPrescriptionApiSercvice {
    @Multipart
    @POST("lunivacarelims/UploadReport")
    Call<ResponseBody> uploadPrescription(
            @Part("userId") RequestBody requestUserId,
            @Part("title") RequestBody title,
            @Part MultipartBody.Part image
    );
}

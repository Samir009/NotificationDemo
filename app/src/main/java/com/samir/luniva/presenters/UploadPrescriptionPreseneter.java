package com.samir.luniva.presenters;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.gson.GsonBuilder;
import com.samir.luniva.apiservices.ApiClient;
import com.samir.luniva.apiservices.UploadPrescriptionApiSercvice;

import java.io.File;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPrescriptionPreseneter {

    //    to store image
    File file;
    String filePath;

    private WeakReference<View> view;

    public UploadPrescriptionPreseneter(UploadPrescriptionPreseneter.View view) {
        this.view = new WeakReference<>(view);

    }

    private UploadPrescriptionPreseneter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View {

        void onClinicResponseSuccess(ResponseBody body);

        void onClinicResponseFailure(String something_went_wrong);
    }

    public void uploadPrescription(String userId, String title, File selectedFile){

        Log.e( "uploadPrescription: ", userId + "\t" + title + "\t" + selectedFile.getName());


//
//        if(file == null){
//            final String[] split = uriData.getPath().split(":");//split the path.
//            filePath = split[1];//assign it to a string(your choice).
//            file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(filePath)));
//        }

//                Log.e("uploadPrescription: ", Environment.getExternalStoragePublicDirectory(filePath)  + "\n" + file.getPath());
        RequestBody requestUserId = RequestBody.create(MediaType.parse("text/plain"), userId);

        RequestBody requestUserTitle = RequestBody.create(MediaType.parse("text/plain") ,title);

        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), selectedFile);

        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", selectedFile.getName(), requestFile);

        UploadPrescriptionApiSercvice uploadPrescriptionApiSercvice = ApiClient.getClient().create(UploadPrescriptionApiSercvice.class);
        uploadPrescriptionApiSercvice.uploadPrescription(requestUserId, requestUserTitle, parts).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Log.e( "onResponse: ","patient perscription uploaded successfully" +new GsonBuilder().create().toJson(response.body()));
                        getView().onClinicResponseSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure: ", "Prescription upload failed" );
            }
        });

    }
}

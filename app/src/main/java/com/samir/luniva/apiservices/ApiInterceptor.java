package com.samir.luniva.apiservices;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by SAMIR SHRESTHA on 2/9/2020  at 10:33 AM
 */

class ApiInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request origignalRequest = chain.request();

        Request request =origignalRequest.newBuilder()
//                .addHeader("Authorization", Utilities.getLoginResponse().getUserDetails().getToken())
                .addHeader("Accept", "Accept: application/json")
//                .header("Cache-Control", String.format("max-age=%d", 50000))
                .build();

        return chain.proceed(request);

    }

}

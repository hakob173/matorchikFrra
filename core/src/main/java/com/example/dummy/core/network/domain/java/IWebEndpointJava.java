package com.example.dummy.core.network.domain.java;

import com.example.dummy.core.network.IWebEndpoint;
import com.example.dummy.core.network.domain.java.request.rotate.RotateRequest;
import com.example.dummy.core.network.domain.java.response._ResultEnvelope;
import com.example.dummy.core.network.spec.ApiSpec;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface IWebEndpointJava {

    String address = IWebEndpoint.address + ":8080/";

    @POST("turn")
    Observable<_ResultEnvelope> rotate(@Body RotateRequest rotateRequest);

}

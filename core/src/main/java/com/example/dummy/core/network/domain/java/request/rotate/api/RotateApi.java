package com.example.dummy.core.network.domain.java.request.rotate.api;

import com.example.dummy.core.network.WebEndpoint;
import com.example.dummy.core.network.domain.java.request.rotate.RotateRequest;
import com.example.dummy.core.network.domain.java.request.rotate.RotationDirection;
import com.example.dummy.core.network.domain.java.response._ResultEnvelope;

import io.reactivex.Observable;

public class RotateApi {

    public static Observable<Boolean> rotate(RotationDirection direction,Long duration) {
        return WebEndpoint
                .j()
                .rotate(new RotateRequest(direction,duration))
                .map(_ResultEnvelope::isOK);
    }
}

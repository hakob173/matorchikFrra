package com.example.dummy.core.network.domain.java.request.rotate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RotateRequest {

    @JsonProperty("direction")
    private RotationDirection rotationDirection;

    @JsonProperty("duration")
    private Long duration;

    public RotateRequest() {
    }

    public RotateRequest(RotationDirection rt,Long dr) {
        rotationDirection = rt;
        duration = dr;
    }
}

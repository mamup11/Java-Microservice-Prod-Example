package com.companion.api.commons.external.authfast;

import retrofit2.Call;
import retrofit2.http.POST;

public interface AuthFast {
    @POST("token/validate")
    Call<Void> validate();
}

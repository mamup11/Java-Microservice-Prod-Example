package com.companion.api.commons.interceptors.retrofit;

import com.companion.api.commons.error.model.exceptions.UnauthorizedException;
import com.companion.api.commons.utils.Context;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class RetrofitUserAuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Optional<String> token = Context.getAuthorization();
        if (!token.isPresent()) {
            throw new UnauthorizedException();
        }

        // Build new request with auth header
        request = request.newBuilder()
                .headers(request.headers())
                .addHeader("Authorization", token.get())
                .method(request.method(), request.body())
                .build();

        return chain.proceed(request);
    }
}

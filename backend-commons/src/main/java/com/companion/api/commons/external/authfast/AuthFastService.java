package com.companion.api.commons.external.authfast;

import com.companion.api.commons.interceptors.retrofit.RetrofitLoggingInterceptor;
import com.companion.api.commons.interceptors.retrofit.RetrofitUserAuthInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthFastService {

    private static final String NETWORK_ERROR_MESSAGE = "A Network error occur calling Auth-Fast service";

    private final AuthFast authFastRetrofit;

    public AuthFastService(@Value("${auth-fast.service.baseUrl}") String baseUrl,
                           @Value("${auth-fast.service.connectionTimeoutMillis}") long connectionTimeoutMillis,
                           @Value("${auth-fast.service.readTimeoutMillis}") long readTimeoutMillis,
                           @Value("${auth-fast.service.poolMaxIdleConnections}") int poolMaxIdleConnections,
                           @Value("${auth-fast.service.poolKeepAliveSeconds}") long poolKeepAliveSeconds,
                           RetrofitUserAuthInterceptor retrofitUserAuthInterceptor,
                           RetrofitLoggingInterceptor retrofitLoggingInterceptor,
                           ObjectMapper objectMapper) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectionPool(new ConnectionPool(poolMaxIdleConnections, poolKeepAliveSeconds, TimeUnit.SECONDS))
                .readTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS)
                .connectTimeout(connectionTimeoutMillis, TimeUnit.MILLISECONDS)
                .addInterceptor(retrofitUserAuthInterceptor)
                .addInterceptor(retrofitLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .validateEagerly(true)
                .build();

        authFastRetrofit = retrofit.create(AuthFast.class);
    }

    public boolean validateToken() {
        try {
            Response<Void> response = authFastRetrofit.validate()
                    .execute();
            return response.isSuccessful();
        } catch (IOException ioe) {
            log.error(NETWORK_ERROR_MESSAGE, ioe);
            throw new UncheckedIOException(NETWORK_ERROR_MESSAGE, ioe);
        }
    }
}
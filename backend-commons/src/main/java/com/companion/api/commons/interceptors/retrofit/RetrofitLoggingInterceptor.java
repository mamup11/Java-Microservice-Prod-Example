package com.companion.api.commons.interceptors.retrofit;

import com.companion.api.commons.utils.Context;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static com.companion.api.commons.utils.Context.X_CONVERSATION_ID;
import static com.companion.api.commons.utils.Context.X_CORRELATION_ID;

@Component
public class RetrofitLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Optional<String> correlationIdOpt = Context.getCorrelationId();
        Optional<String> conversationIdOpt = Context.getConversationId();

        if (correlationIdOpt.isPresent() && conversationIdOpt.isPresent()) {
            // Build new request with tracking headers
            request = request.newBuilder()
                    .headers(request.headers())
                    .addHeader(X_CORRELATION_ID, correlationIdOpt.orElse(null))
                    .addHeader(X_CONVERSATION_ID, conversationIdOpt.orElse(null))
                    .method(request.method(), request.body())
                    .build();
        }

        // TODO: Logging
        
        return chain.proceed(request);
    }
}

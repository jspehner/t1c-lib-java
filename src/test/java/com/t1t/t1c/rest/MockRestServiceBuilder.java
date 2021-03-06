package com.t1t.t1c.rest;

import com.t1t.t1c.exceptions.ExceptionFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockRestServiceBuilder {

    private static final Logger log = LoggerFactory.getLogger(MockRestServiceBuilder.class);

    private static final String APIKEY_HEADER_NAME = "apikey";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";

    public static Retrofit getRetrofit(String uri, String apiKey, String jwt) {
        try {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .client(gethttpClient(apiKey, jwt))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(uri);
            return retrofitBuilder.build();
        } catch (Exception ex) {
            log.error("Error creating client: ", ex);
            throw ExceptionFactory.gclClientException("Error creating client: " + ex.getMessage());
        }
    }

    private static OkHttpClient gethttpClient(final String apikey, final String jwt) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        final boolean apikeyPresent = StringUtils.isNotBlank(apikey);
        final boolean jwtPresent = StringUtils.isNotBlank(jwt);

        if (apikeyPresent || jwtPresent) {
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    if (apikeyPresent) {
                        requestBuilder.addHeader(APIKEY_HEADER_NAME, apikey);
                    }
                    if (jwtPresent) {
                        if (jwt.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
                            requestBuilder.addHeader(AUTHORIZATION_HEADER_NAME, jwt);
                        } else {
                            requestBuilder.addHeader(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE_PREFIX + jwt);
                        }
                    }
                    return chain.proceed(requestBuilder.build());
                }
            });
        }
        return okHttpBuilder.build();
    }
}
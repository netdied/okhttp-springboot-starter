package com.okhttp.start.configuration;

import com.okhttp.start.cookiejar.SimpleCookieJar;
import com.okhttp.start.properties.OkHttpProperties;
import okhttp3.ConnectionSpec;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author xingzi
 * @date 2019 07 04  10:50
 */
@Configuration
@ConditionalOnClass(OkHttpClient.class)
@EnableConfigurationProperties(OkHttpProperties.class)
public class OkHttpConfiguration {
    private final OkHttpProperties properties;

    public OkHttpConfiguration(OkHttpProperties okHttpProperties) {
        this.properties = okHttpProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(properties.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(properties.getReadTimeout(),TimeUnit.SECONDS)
                .followRedirects(properties.getRedirect())
                .followSslRedirects(properties.getSslRedirect())
                .build();

        if(properties.getCookieJar()){
            SimpleCookieJar simpleCookieJar = (SimpleCookieJar) Class.forName(properties.getCookieJarImpl()).newInstance();
            okHttpClient = okHttpClient.newBuilder()
                    .cookieJar(simpleCookieJar)
                    .build();
        }
        if (properties.getSsl()){
            okHttpClient = okHttpClient.newBuilder()
                    .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS,ConnectionSpec.COMPATIBLE_TLS))
                    .build();
        }
        return okHttpClient;
    }
}

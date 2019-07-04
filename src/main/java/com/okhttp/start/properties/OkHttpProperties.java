package com.okhttp.start.properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xingzi
 * @date 2019 07 03  21:37
 */
@Component
@Getter
@Setter
@Builder
@ConfigurationProperties(prefix = "okhttp")
public class OkHttpProperties {
    private Boolean ssl = false;
    private long connectTimeout = 30;
    private long readTimeout = 30;
    private Boolean redirect = false;
    private Boolean sslRedirect = false;
    private Boolean cookieJar = true;
    private String cookieJarImpl = "com.okhttp.start.cookiejar.SimpleCookieJar";


}

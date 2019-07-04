package com.okhttp.start.cookiejar;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;



/**
 * @author xingzi
 * @date 2019 07 04  11:07
 */
public class SimpleCookieJar implements CookieJar {
    private  Map<String,List<Cookie>> cookieMap = new ConcurrentHashMap<>();
    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        return cookieMap.get(httpUrl.host());
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
            String host = httpUrl.host();
                if(cookieMap.isEmpty()){
                    cookieMap.put(httpUrl.host(),list);
                }
                else {
                    List<Cookie> cookies = cookieMap.get(host);

                    List<Cookie>  cookieList = Stream.of(cookies,list)
                          .flatMap(Collection::stream)
                          .collect(Collectors.toList());
                    cookieMap.put(host,cookieList);
                }
    }

    public Map<String, List<Cookie>> getCookieMap() {
        return cookieMap;
    }

    public void setCookieMap(Map<String, List<Cookie>> cookieMap) {
        this.cookieMap = cookieMap;
    }
}

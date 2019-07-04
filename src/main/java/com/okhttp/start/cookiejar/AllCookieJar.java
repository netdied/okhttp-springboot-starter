package com.okhttp.start.cookiejar;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xingzi
 * @date 2019 07 04  14:05
 */
public class AllCookieJar implements CookieJar {

    private final ReentrantLock lock = new ReentrantLock();
    private List<Cookie> cookies = new ArrayList<>();
    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        return cookies;
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        lock.lock();
        if (cookies.isEmpty()){
            cookies = list;
        }
        else {
            cookies = Stream.of(list,cookies)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        lock.unlock();
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }
}

# okhttp-springboot-starter
okhttp简单封装
# Config 
## application.yml
* okhttp.ssl=true
* okhttp.connect-timeout=30 #S
* okhttp.cookiejar=com.okhttp.start.cookiejar.SimpleCookieJar
# Use
@Autowired 
OkhttpClient okhttpclient;

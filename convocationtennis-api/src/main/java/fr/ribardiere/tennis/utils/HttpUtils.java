package fr.ribardiere.tennis.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.repackaged.com.google.common.base.Strings;

/**
 * Mais pourquoi diable n'utilise-t-il pas Apache Http Client ? Parce que App Engine ne l'accepte pas :-). Les exemples avec Google Http Client étant tellement pauvres, j'ai préféré revenir au JDK de base
 * @author Olivier
 *
 */
public class HttpUtils {
    
    private static final String ERR_MSG = "Erreur lors de l'appel à l'url ";
    
    private HttpUtils() {
        
    }

    public static String callGet(String url) {
        return callGet(url, null);
    }
    
    public static String callGet(String url, String cookie) {
        String result = "";
        try {
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory.buildGetRequest(
                    new GenericUrl(url));
            if (!Strings.isNullOrEmpty(cookie)) {
                request.getHeaders().setCookie(cookie);
            }
            result = request.execute().parseAsString();
        } catch (IOException ioex) {
            throw new RuntimeException(
                    ERR_MSG + url, ioex);
        }
        return result;
    }

    public static String callToGetCookie(String url) {
        
        
        
        String result = "";
        try {
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory.buildGetRequest(
                    new GenericUrl(url));
            request.execute();
            List<String> cookies = request.getResponseHeaders().getHeaderStringValues("Set-Cookie");
            for(String cookie : cookies) {
                if (cookie.contains("JSESSIONID")) {
                    if (cookie.contains(";")) {
                        result = cookie.substring(0, cookie.indexOf(';'));
                    } else {
                        result = cookie;
                    }
                }
            }
        } catch (IOException ioex) {
            throw new RuntimeException(
                    ERR_MSG + url, ioex);
        }
        return result;
    }
    
    public static String callPost(String url, List<NameValuePair> params) {
        String result = "";

        
        try {
            String urlParameters = "";
            for(NameValuePair param : params) {
                urlParameters+=URLEncoder.encode(param.getName(), StandardCharsets.UTF_8.name()) + "=" + URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8.name()) + "&";
            }
            urlParameters = urlParameters.substring(0, urlParameters.length() - 1);

            
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory.buildPostRequest(
                new GenericUrl(url+"?"+urlParameters), null);
            result = request.execute().parseAsString();
        } catch (IOException ioex) {
            throw new RuntimeException(
                    ERR_MSG + url, ioex);
        }
        return result;
    }

}

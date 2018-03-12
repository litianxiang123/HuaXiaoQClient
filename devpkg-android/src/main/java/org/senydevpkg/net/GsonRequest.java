package org.senydevpkg.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.senydevpkg.utils.ALog;
import org.senydevpkg.utils.MD5Utils;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Request，通过GSON解析json格式的response。带缓存请求功能。
 */
public class GsonRequest<T> extends Request<T> {

    public final Gson gson = new Gson();
    private final Class<? extends T> mClazz;
    private final Map<String, String> mParams;
    private final Response.Listener<T> mListener;
    private boolean mIsCache;
    private Context mContext;
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private SharedPreferences _preferences;

    /**
     * 初始化
     *
     * @param method        请求方式
     * @param url           请求地址
     * @param params        请求参数，可以为null
     * @param clazz         Clazz类型，用于GSON解析json字符串封装数据
     * @param listener      处理响应的监听器
     * @param errorListener 处理错误信息的监听器
     */
    public GsonRequest(int method, String url, Map<String, String> params, Class<? extends T> clazz,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, boolean isCache, Context context) {
        super(method, url, errorListener);
        mClazz = clazz;
        mParams = params;
        mListener = listener;
        mIsCache = isCache;
        mContext = context;
        Assert.notNull(mContext);
        _preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }


    /**
     * 获取GsonRequest所要解析Class类型
     *
     * @return GSON解析的对象字节码
     */
    public Class<? extends T> getClazz() {
        return mClazz;
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
       if (response.headers.containsKey(SET_COOKIE_KEY)) {
            String cookie = response.headers.get(SET_COOKIE_KEY);
         /*  if (cookie.length() > 1 && cookie.endsWith(";")) {
               cookie = cookie.substring(0, cookie.length() - 1);
           }*/
        Log.v("susaw",cookie);
               /* if(cookie.length() > 1 && cookie.endsWith(";")) {
                    cookie = cookie.substring(0, cookie.length() - 1);
                    String[] splitCookie = cookie.split(";");*/

               /* String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];*/
                    // _preferences = mContext.getSharedPreferences("comfig",Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefEditor = _preferences.edit();
                    prefEditor.putString(SESSION_COOKIE, cookie);
                    prefEditor.commit();

       }
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            ALog.d(json);
            T result = gson.fromJson(json, mClazz);//按正常响应解析
            if (mIsCache) {
                //如果解析成功，并且需要有缓存则将json字符串缓存到本地
                ALog.i("Save response to local!");
                FileCopyUtils.copy(response.data, new File(mContext.getCacheDir(), "" + MD5Utils.encode(getUrl())));
            }
            return Response.success(
                    result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>(); }
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        Log.d("session",sessionId);
        if (sessionId.length() >  0) {
          /*  StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }*/
           // headers.put(COOKIE_KEY, builder.toString());
            headers.put(COOKIE_KEY,sessionId);
        }
        return headers;
    }


}
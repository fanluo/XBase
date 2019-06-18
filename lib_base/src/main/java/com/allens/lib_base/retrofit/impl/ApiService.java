package com.allens.lib_base.retrofit.impl;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by allens on 2017/11/29.
 */

public interface ApiService {


    @GET
    Observable<ResponseBody> doGet(@Url String url);


    @FormUrlEncoded
    @POST("{path}")
    Observable<ResponseBody> doPost(@Path(value = "path", encoded = true) String urlPath, @FieldMap Map<String, Object> map);


    @POST("{path}")
    Observable<ResponseBody> doBody(@Path(value = "path", encoded = true) String urlPath, @Body RequestBody body);

    @DELETE()
    Observable<ResponseBody> doDelete(@Url String url, @QueryMap Map<String, Object> maps);

    @PUT()
    Observable<ResponseBody> doPut(@Url String url, @FieldMap Map<String, Object> maps);

    @GET
    Observable<ResponseBody> downloadSmallFile(@Url String fileUrl);

}

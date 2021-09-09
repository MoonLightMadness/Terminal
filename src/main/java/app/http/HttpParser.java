package app.http;

import app.domain.HttpEntity;


/**
 * @ClassName : app.dsm.server.http.HttpParser
 * @Description :
 * @Date 2021-08-16 08:11:24
 * @Author ZhangHL
 */
public class HttpParser {


    public static HttpEntity parse(String data){
        //Data sample:
//        POST /server/gettime HTTP/1.1
//        Content-Type: application/json
//        User-Agent: PostmanRuntime/7.28.3
//        Accept: */*
//        Postman-Token: 8c915b42-544e-4359-a6a9-b12596dd2509
//        Host: 127.0.0.1:10005
//        Accept-Encoding: gzip, deflate, br
//        Connection: keep-alive
//        Content-Length: 10
//
//        {
//
//        }
        HttpEntity entity = new HttpEntity();
        String mode = data.substring(0,data.indexOf(" "));
        String path = data.substring(data.indexOf(" ")+1,data.indexOf(" ",mode.length()+1));
        String body = null;
        //存在无数据的情况
        try {
            body = data.substring(data.indexOf("{"),data.lastIndexOf("}")+1);
        }catch (Exception e) {
            //e.printStackTrace();
        }
        entity.setMode(mode);
        entity.setRequestPath(path);
        entity.setBody(body);
        return entity;
    }


}

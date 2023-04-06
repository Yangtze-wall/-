//package com.retail.user.service.impl;
//
//
//import com.aliyun.auth.credentials.Credential;
//import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
//import com.aliyun.core.http.HttpClient;
//import com.aliyun.core.http.HttpMethod;
//import com.aliyun.core.http.ProxyOptions;
//import com.aliyun.httpcomponent.httpclient.ApacheAsyncHttpClientBuilder;
//import com.aliyun.sdk.service.ocr_api20210707.models.*;
//import com.aliyun.sdk.service.ocr_api20210707.*;
//import com.google.gson.Gson;
//import darabonba.core.RequestConfiguration;
//import darabonba.core.client.ClientOverrideConfiguration;
//import darabonba.core.utils.CommonUtil;
//import darabonba.core.TeaPair;
//
////import javax.net.ssl.KeyManager;
////import javax.net.ssl.X509TrustManager;
//import java.net.InetSocketAddress;
//import java.time.Duration;
//import java.util.*;
//import java.util.concurrent.CompletableFuture;
//
//public class RecognizeIdcard {
//    public static void main(String[] args) throws Exception {
//
//        // HttpClient Configuration
//        /*HttpClient httpClient = new ApacheAsyncHttpClientBuilder()
//                .connectionTimeout(Duration.ofSeconds(10)) // Set the connection timeout time, the default is 10 seconds
//                .responseTimeout(Duration.ofSeconds(10)) // Set the response timeout time, the default is 20 seconds
//                .maxConnections(128) // Set the connection pool size
//                .maxIdleTimeOut(Duration.ofSeconds(50)) // Set the connection pool timeout, the default is 30 seconds
//                // Configure the proxy
//                .proxy(new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("<your-proxy-hostname>", 9001))
//                        .setCredentials("<your-proxy-username>", "<your-proxy-password>"))
//                // If it is an https connection, you need to configure the certificate, or ignore the certificate(.ignoreSSL(true))
//                .x509TrustManagers(new X509TrustManager[]{})
//                .keyManagers(new KeyManager[]{})
//                .ignoreSSL(false)
//                .build();*/
//
//        // Configure Credentials authentication information, including ak, secret, token
//        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
//                .accessKeyId("<your-accessKeyId>")
//                .accessKeySecret("<your-accessKeySecret>")
//                //.securityToken("<your-token>") // use STS token
//                .build());
//
//        // Configure the Client
//        AsyncClient client = AsyncClient.builder()
//                .region("cn-hangzhou") // Region ID
//                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
//                .credentialsProvider(provider)
//                //.serviceConfiguration(Configuration.create()) // Service-level configuration
//                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
//                .overrideConfiguration(
//                        ClientOverrideConfiguration.create()
//                                .setEndpointOverride("ocr-api.cn-hangzhou.aliyuncs.com")
//                        //.setConnectTimeout(Duration.ofSeconds(30))
//                )
//                .build();
//
//        // Parameter settings for API request
//        RecognizeIdcardRequest recognizeIdcardRequest = RecognizeIdcardRequest.builder()
//                // Request-level configuration rewrite, can set Http request parameters, etc.
//                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
//                .build();
//
//        // Asynchronously get the return value of the API request
//        CompletableFuture<RecognizeIdcardResponse> response = client.recognizeIdcard(recognizeIdcardRequest);
//        // Synchronously get the return value of the API request
//        RecognizeIdcardResponse resp = response.get();
//        System.out.println(new Gson().toJson(resp));
//        // Asynchronous processing of return values
//        /*response.thenAccept(resp -> {
//            System.out.println(new Gson().toJson(resp));
//        }).exceptionally(throwable -> { // Handling exceptions
//            System.out.println(throwable.getMessage());
//            return null;
//        });*/
//
//        // Finally, close the client
//        client.close();
//    }
//
//}
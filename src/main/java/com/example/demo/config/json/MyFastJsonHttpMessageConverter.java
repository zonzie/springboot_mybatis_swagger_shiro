package com.example.demo.config.json;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by xuyf22 on 2017/6/30.
 * 为了打印原始请求体替换FastJsonHttpMessageConverter的某些方法
 * 非心理变态请忽略
 */
public class MyFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
    private FastJsonConfig fastJsonConfig = new FastJsonConfig();

    @Override
    public Object read(Type type, //
                       Class<?> contextClass, //
                       HttpInputMessage inputMessage //
    ) throws IOException, HttpMessageNotReadableException {

        InputStream in = inputMessage.getBody();
        return MyJSON.myParseObject(in, fastJsonConfig.getCharset(), type, fastJsonConfig.getFeatures());
    }

    public void write(Object t, //
                      Type type, //
                      MediaType contentType, //
                      HttpOutputMessage outputMessage //
    ) throws IOException, HttpMessageNotWritableException {

        HttpHeaders headers = outputMessage.getHeaders();
        if (headers.getContentType() == null) {
            if (contentType == null || contentType.isWildcardType() || contentType.isWildcardSubtype()) {
                contentType = getDefaultContentType(t);
            }
            if (contentType != null) {
                headers.setContentType(contentType);
            }
        }
        if (headers.getContentLength() == -1) {
            Long contentLength = getContentLength(t, headers.getContentType());
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        writeInternal(t, outputMessage);
        stopWatch.stop();
//        logger.info("压测日志---序列化输出耗时:{}" + stopWatch.getLastTaskTimeMillis());
        outputMessage.getBody().flush();
    }

    @Override
    public FastJsonConfig getFastJsonConfig() {
        return fastJsonConfig;
    }

    @Override
    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }
}

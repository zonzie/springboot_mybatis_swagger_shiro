package com.example.demo.config.json;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyf22 on 2017/3/10.
 */
@Configuration
public class JSONConfig {

    @Bean
    public HttpMessageConverters httpMessageConvertersWithFastJson() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new MyFastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverters defaultConverters = new HttpMessageConverters();
        List<HttpMessageConverter<?>> converterList = new ArrayList<>();
        for(HttpMessageConverter<?> converter : defaultConverters) {
            if(converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("utf-8"));
            }
            if(converter instanceof MappingJackson2HttpMessageConverter) {
                converterList.add(fastJsonHttpMessageConverter);
            }
            converterList.add(converter);
        }
        return new HttpMessageConverters(false, converterList);
    }

}

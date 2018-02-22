package com.example.demo.config.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by xuyf22 on 2017/6/30.
 * 为了打印原始请求体替换JSON的某些方法
 * 非心理变态请忽略
 */
public class MyJSON extends JSON {
    private static final Logger logger = LoggerFactory.getLogger(MyJSON.class);

    public static <T> T myParseObject(InputStream is, //
                                    Charset charset, //
                                    Type type, //
                                    Feature... features) throws IOException {
        if (charset == null) {
            charset = IOUtils.UTF8;
        }

        byte[] bytes = allocateBytes(1024 * 64);
        int offset = 0;
        for (;;) {
            int readCount = is.read(bytes, offset, bytes.length - offset);
            if (readCount == -1) {
                break;
            }
            offset += readCount;
            if (offset == bytes.length) {
                byte[] newBytes = new byte[bytes.length * 3 / 2];
                System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                bytes = newBytes;
            }
        }

        return (T) myParseObject(bytes, 0, offset, charset, type, features);
    }

    public static <T> T myParseObject(byte[] bytes, int offset, int len, Charset charset, Type clazz, Feature... features) {
        if (charset == null) {
            charset = IOUtils.UTF8;
        }
        String strVal;
        if (charset == IOUtils.UTF8) {
            char[] chars = allocateChars(bytes.length);
            int chars_len = IOUtils.decodeUTF8(bytes, offset, len, chars);
            strVal = new String(chars, 0, chars_len);
        } else {
            strVal = new String(bytes, offset, len, charset);
        }
        logger.info("\r\n请求体:{}", strVal.substring(0, strVal.length() > 1000 ? 1000 : strVal.length()));
        return (T) parseObject(strVal, clazz, features);
    }

    private final static ThreadLocal<char[]> charsLocal = new ThreadLocal<char[]>();
    private static char[] allocateChars(int length) {
        char[] chars = charsLocal.get();

        if (chars == null) {
            if (length <= 1024 * 64) {
                chars = new char[1024 * 64];
                charsLocal.set(chars);
            } else {
                chars = new char[length];
            }
        } else if (chars.length < length) {
            chars = new char[length];
        }

        return chars;
    }

    private final static ThreadLocal<byte[]> bytesLocal = new ThreadLocal<byte[]>();
    private static byte[] allocateBytes(int length) {
        byte[] chars = bytesLocal.get();

        if (chars == null) {
            if (length <= 1024 * 64) {
                chars = new byte[1024 * 64];
                bytesLocal.set(chars);
            } else {
                chars = new byte[length];
            }
        } else if (chars.length < length) {
            chars = new byte[length];
        }

        return chars;
    }

}

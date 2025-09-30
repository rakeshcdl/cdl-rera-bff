/**
 * DecryptionAdvice.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 30/07/25
 *//*



package com.cdl.escrow.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

//@RestControllerAdvice
//@RequiredArgsConstructor
public abstract class DecryptionAdvice implements RequestBodyAdvice {


   // private final AESEncryptionUtil aesUtil;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        String encryptedJson = new String(inputMessage.getBody().readAllBytes(), StandardCharsets.UTF_8);
        try {
            String decryptedJson = aesUtil.decrypt(encryptedJson);
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return new ByteArrayInputStream(decryptedJson.getBytes());
                }
                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception ex) {
            throw new HttpMessageNotReadableException("Invalid encrypted payload", ex, inputMessage);
        }
    }
}
*/

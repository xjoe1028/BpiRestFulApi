package com.bpi.configuration;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.ibatis.javassist.NotFoundException;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BadRequestException();
            case 404:
                return new NotFoundException("404 exception");
            case 500:
                return new Exception("500 exception");
            default:
                return new Exception("Generic error");
        }
    }

}

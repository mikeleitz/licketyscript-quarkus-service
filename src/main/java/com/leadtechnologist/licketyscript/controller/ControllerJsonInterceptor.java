package com.leadtechnologist.licketyscript.controller;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import com.leadtechnologist.licketyscript.utils.JsonThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * @author leitz@mikeleitz.com
 */
@Provider
@Slf4j
public class ControllerJsonInterceptor implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        log.info("Intercepting the request before the controller.");

        InputStream entityStream = containerRequestContext.getEntityStream();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        entityStream.transferTo(baos);
        InputStream firstClone = new ByteArrayInputStream(baos.toByteArray());
        InputStream secondClone = new ByteArrayInputStream(baos.toByteArray());

        String jsonBody = IOUtils.toString(firstClone, Charset.defaultCharset());
        firstClone.close();

        containerRequestContext.setEntityStream(secondClone);

        JsonThreadLocal.set(jsonBody);
        log.info("Parsed json payload: [{}]", jsonBody);
    }
}

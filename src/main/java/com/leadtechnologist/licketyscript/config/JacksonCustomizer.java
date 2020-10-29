package com.leadtechnologist.licketyscript.config;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadtechnologist.licketyscript.bash.domain.BashOption;
import com.leadtechnologist.licketyscript.bash.domain.BashValidation;
import io.quarkus.jackson.ObjectMapperCustomizer;

/**
 * @author leitz@mikeleitz.com
 */
@Singleton
public class JacksonCustomizer implements ObjectMapperCustomizer {
    public void customize(ObjectMapper mapper) {
        mapper.findAndRegisterModules(); //Registers all modules on classpath

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JavaType bashValidationList = mapper.getTypeFactory().constructCollectionType(List.class, BashValidation.class);
        JavaType bashOptionSet = mapper.getTypeFactory().constructCollectionType(HashSet.class, BashOption.class);
    }
}

package com.leadtechnologist.licketyscript;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@ApplicationScoped
public class SnippetTemplateFactory {
    Map<String, String> templateLocationToContents = new HashMap<>();

    @PostConstruct
    public void loadAllSnippetTemplates() {
        List<String> templateLocations = new ArrayList<>();

        templateLocations.add("META-INF/resources/bash/readme-file/readme-instructions.stg");
        templateLocations.add("META-INF/resources/bash/manifest/manifest-body.stg");
        templateLocations.add("META-INF/resources/bash/manifest/manifest-instructions.stg");
        templateLocations.add("META-INF/resources/bash/user-script/user-script-instructions.stg");
        templateLocations.add("META-INF/resources/bash/user-script/user-script-body.stg");
        templateLocations.add("META-INF/resources/bash/installer-script/installer-script-instructions.stg");
        templateLocations.add("META-INF/resources/bash/installer-script/installer-script-body.stg");
        templateLocations.add("META-INF/resources/bash/common/bash-validation-regex-template.stg");
        templateLocations.add("META-INF/resources/bash/common/bash-validation-trim-template.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/bash-help-template.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/bash-input-template.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/bash-shebang-template.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/bash-processing-template.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-alphanumeric-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-integer-range-logic.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-ipv4-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-integer-signed-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-integer-greater-than-equal-logic.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-timestamp-iso-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-email-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-integer-unsigned-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-real-unsigned-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-url-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-custom-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-options-validation.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-integer-less-than-equal-logic.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-ipv6-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-boolean-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-timestamp-one-true-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-integer-greater-than-logic.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-real-signed-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-date-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-integer-less-than-logic.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/validation/bash-validation-required-regex.stg");
        templateLocations.add("META-INF/resources/bash/lickety-script/bash-logging-template.stg");

        templateLocationToContents = templateLocations.stream()
            .collect(Collectors.toMap(String::valueOf, SnippetTemplateFactory::loadTemplate));

        log.info("Started up the template factory!!");
    }

    protected static String loadTemplate(String location) {
        String templateContents = null;
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
            if (resourceAsStream != null) {
                templateContents = IOUtils.toString(resourceAsStream, Charset.defaultCharset());
            } else {
                log.error("Can't find input stream for template: [{}].", location);
            }
        } catch (IOException e) {
            log.error("Unable to load all of the required template: [{}].", location, e);
        }

        return templateContents;
    }

    public String getTemplate(String templateLocation) {
        return templateLocationToContents.get(templateLocation);
    }
}

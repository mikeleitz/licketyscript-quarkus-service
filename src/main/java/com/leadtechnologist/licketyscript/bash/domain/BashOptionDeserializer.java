/*
 *  Copyright (c) 2020, Michael Leitz
 *  <p/>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.leadtechnologist.licketyscript.bash.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Deserializes BashOptions.
 */
@Slf4j
public class BashOptionDeserializer extends StdDeserializer<BashOption> {
    private static final String ARG_KEY_NAME = "key";
    private static final String ARG_KEY_VALUE = "value";

    public BashOptionDeserializer() {
        this(BashOption.class);
    }

    public BashOptionDeserializer(Class<?> vc) {
        super(BashOption.class);
    }

    @Override
    public BashOption deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        BashOption returnValue = null;

        log.debug("Starting deserializing json payload for creating the bash script.");

        JsonNode node = jp.getCodec().readTree(jp);

        Integer id = (Integer) ((IntNode) node.get("id")).numberValue();
        String longName = node.get("longName").asText();
        String shortName = node.get("shortName").asText();
        String type = node.get("type").asText();
        String defaultValue = node.get("defaultValue").asText();
        String helpText = node.get("helpText").asText();
        Boolean optionHasValue = node.get("optionHasValue").asBoolean();

        Character shortNameChar = null;
        if (StringUtils.isNotBlank(shortName)) {
            shortNameChar = shortName.charAt(0);
        }

        List<BashValidation> allValidations = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode validations = (ArrayNode) node.get("validations");

        Boolean isOptionValueRequired = false;

        if (validations != null && !validations.isNull()) {
            for (JsonNode validation : validations) {
                BashValidation val = mapper.readValue(validation.toString(), BashValidation.class);

                // If the option doesn't have the value required, mark the object
                // as input not required. This helps with rendering the script
                // templates. There isn't an option to figure this out
                // dynamically.
                if (val.getValidationEnum() == ValidationEnum.VALUE_REQUIRED) {
                    isOptionValueRequired = true;
                }
                // TODO modify when we support more validations.
                allValidations.add(val);
            }

            allValidations.sort(Comparator.comparing(v -> v.getValidationEnum().getOrder()));
        }

        returnValue = BashOption.builder()
                .id(id)
                .longName(longName)
                .longNameBashFriendly(BashOption.makeVariableNameAcceptableToBash(longName))
                .shortName(shortNameChar)
                .defaultValue(defaultValue)
                .helpText(helpText)
                .optionHasValue(optionHasValue)
                .bashValidations(allValidations)
                .inputRequired(isOptionValueRequired)
                .build();

        log.debug("Completed deserializing json payload for creating the bash script.");

        return returnValue;
    }
}

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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Since we have different classes for different types of validations, this
 * class is responsible for taking the json input from the frontend and
 * creating the appropriate validation domain object.
 *
 * These domain objects are then converted into the relevant snippets
 * to create the actual script.
 */
public class BashValidationDeserializer extends StdDeserializer<BashValidation> {
    private static final String ARG_KEY_NAME = "key";
    private static final String ARG_KEY_VALUE = "value";
    private static final String SPECIFIED_VALUE = "value";

    private BashValidationFactory bashValidationFactory = new BashValidationFactory();

    public BashValidationDeserializer() {
        this(BashValidation.class);
    }

    public BashValidationDeserializer(Class<?> vc) {
        super(BashValidation.class);
    }

    @Override
    public BashValidation deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        BashValidation returnValue = null;

        JsonNode node = jp.getCodec().readTree(jp);

        Integer id = (Integer) ((IntNode) node.get("id")).numberValue();
        List<Pair<String, String>> args = Collections.emptyList();

        // We ignore the name from the JS side and take the name specified on the Java side.
        String name = node.get("name").asText();
        String value = "";

        ArrayNode argsArrayNode = (ArrayNode) node.get("args");
        if (argsArrayNode != null && !argsArrayNode.isNull()) {
            // Stream the nodes and create a list of key/value arg pairs.
            args = IntStream.range(0, argsArrayNode.size())
                    .mapToObj(argsArrayNode::get)
                    .map(n -> new ImmutablePair<>(n.get(ARG_KEY_NAME).asText(),
                            n.get(ARG_KEY_VALUE).asText()))
                    .collect(Collectors.toList());

            Optional<Pair<String, String>> valueOption = args.stream()
                    .filter(p -> StringUtils.equalsIgnoreCase(SPECIFIED_VALUE, p.getKey()))
                    .findFirst();

            if (valueOption.isPresent()) {
                value = valueOption.get().getValue();
            }
        }

        try {
            returnValue = bashValidationFactory.createBashValidation(id, args);
            returnValue.setValue(value);
        }
        catch (ValidationNotFoundException e) {
            throw new InvalidTypeIdException(jp, e.getMessage(), ctxt.getContextualType(), String.format("Validation id: [%d]", id));
        }

        return returnValue;
    }
}

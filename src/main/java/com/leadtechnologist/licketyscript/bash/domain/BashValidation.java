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

import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The regex values are mostly taken from the community driven: https://regexr.com
 */
@Data
@SuperBuilder
@AllArgsConstructor
@JsonDeserialize(using = BashValidationDeserializer.class)
public class BashValidation {
    private @NonNull Integer id;
    private @NonNull String name;
    // This is a single value for validations that accept a user defined value.
    // It currently supports one value.
    private String value;
    private @NonNull List<Pair<String, String>> args;
    private @NonNull ValidationEnum validationEnum;

    public Optional<Pair<String, String>> getPairForKey(String key) {
        Optional<Pair<String, String>> returnValue = Optional.empty();

        if (CollectionUtils.isNotEmpty(args)) {
            returnValue = args.stream()
                    .filter(a -> StringUtils.equalsIgnoreCase(a.getKey(), key))
                    .findFirst();
        }

        return returnValue;
    }

    public Boolean isSameValidationType(BashValidation bashValidation) {
        Boolean returnValue = null;

        if (this.getId().equals(bashValidation.getId())) {
            returnValue = true;
        }
        else {
            returnValue = false;
        }

        return returnValue;
    }
}

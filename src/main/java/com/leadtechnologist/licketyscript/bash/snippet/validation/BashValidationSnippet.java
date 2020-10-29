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

package com.leadtechnologist.licketyscript.bash.snippet.validation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.leadtechnologist.licketyscript.base.CompositeSnippet;
import com.leadtechnologist.licketyscript.base.GenericSnippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import com.leadtechnologist.licketyscript.bash.domain.BashOption;
import com.leadtechnologist.licketyscript.bash.domain.ValidationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * This class creates all the common validations used in the bash script. It loops through all the validation enums and
 * creates a super-snippet of all of the validations.
 *
 * @author leitz@mikeleitz.com
 */
@Slf4j
public class BashValidationSnippet extends CompositeSnippet {
    public BashValidationSnippet(SnippetContext context) throws IOException {
        super(context);

        // Handle generic validations. These are most of our framework's validations and doesn't require anything
        // special from this snippet to render.
        Arrays.stream(ValidationEnum.values())
                .filter(t -> StringUtils.isNotBlank(t.getStringTemplate()))
                .filter(t -> !t.requiresSpecialTemplateHandling())
                .forEach(t -> this
                        .addSnippet(new GenericSnippet(context, t.getStringTemplate(), t.getValidationName())));

        // Handle the custom regex validation. It has special handling.

        Set<BashOption> allBashOptions = (Set<BashOption>) context.getAllValues().get("bashOptions");
        if (CollectionUtils.isNotEmpty(allBashOptions)) {
            List<BashOption> bashOptionsWithCustomRegex = allBashOptions.stream()
                    .filter(o -> o.optionUsesValidation(ValidationEnum.CUSTOM_REGEX))
                    .collect(Collectors.toList());

            GenericSnippet customRegexenSnippet = createCustomBashRegexenSnippet(context, bashOptionsWithCustomRegex);
            this.addSnippet(customRegexenSnippet);
        }

        log.info("Have a total of [{}] validation snippets.", this.totalSnippets());
    }

    protected GenericSnippet createCustomBashRegexenSnippet(SnippetContext context,
            List<BashOption> allCustomRegexenSpecified) {
        GenericSnippet returnValue;

        List<CustomRegex> allCustomRegex = allCustomRegexenSpecified.stream()
                .filter(o -> o.optionUsesValidation(ValidationEnum.CUSTOM_REGEX))
                .filter(o -> o.getValidation(ValidationEnum.CUSTOM_REGEX).get().getPairForKey("value").isPresent())
                .map(o -> new CustomRegex(o.getLongNameBashFriendly(),
                        o.getValidation(ValidationEnum.CUSTOM_REGEX).get().getPairForKey("value").get().getRight()))
                .collect(Collectors.toList());

        context.addValue("bashCustomRegexValidations", allCustomRegex);
        returnValue = new GenericSnippet(context, ValidationEnum.CUSTOM_REGEX.getStringTemplate(),
                ValidationEnum.CUSTOM_REGEX.name());

        return returnValue;
    }

    @Data
    @AllArgsConstructor
    public class CustomRegex {
        private String optionName;
        private String regex;
    }
}

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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import com.leadtechnologist.licketyscript.bash.domain.BashOption;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class BashOptionsValidationSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/lickety-script/validation/bash-options-validation.stg";

    public BashOptionsValidationSnippet(Set<BashOption> bashOptions, SnippetContext context) throws IOException {
        super(context);
        setSnippetTemplate(TEMPLATE_LOCATION);

        List<BashOption> optionsWithValidations = bashOptions.stream()
                .filter(o -> CollectionUtils.isNotEmpty(o.getBashValidations()))
                .collect(Collectors.toList());

        context.addValue("bashOptionsWithValidations", optionsWithValidations);
    }
}

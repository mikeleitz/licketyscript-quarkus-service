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
package com.leadtechnologist.licketyscript.bash.snippet.common;

import java.io.IOException;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;

/**
 * This snippet creates a single common, reusable function that will take two arguments.
 *  arg 1. A value
 *  arg 2. A regex that test the value.
 *
 * The function returns a 0 if the value matches the specified regex and any other number if it's not a match/
 *
 * The return value is read via the bash $? mechanism.
 *
 * Individual validations from the engine are created using specific functions in the /validation directory.
 *
 * @author leitz@mikeleitz.com
 */
public class RegexValidationFunctionBashSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/common/bash-validation-regex-template.stg";

    public RegexValidationFunctionBashSnippet(SnippetContext context) throws IOException {
        super(context);
        setSnippetTemplate(TEMPLATE_LOCATION);

    }
}

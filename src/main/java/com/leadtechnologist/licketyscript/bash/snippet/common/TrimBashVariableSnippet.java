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
import org.stringtemplate.v4.ST;

/**
 * Generic bash function that takes a value and trims off all leading and trailing whitespace.
 *
 * @author leitz@mikeleitz.com
 */
public class TrimBashVariableSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/common/bash-validation-trim-template.stg";

    private String variableName;        // The name of the variable.
    private String isVariableSpecified; // Each variable has a sister variable that indicates if it was passed in or not.  If the variable was name there's a second variable called name_option_chosen=1 or 0.

    public TrimBashVariableSnippet(SnippetContext context, String variableName, String isVariableSpecified) throws
            IOException {
        super(context);
        setSnippetTemplate(TEMPLATE_LOCATION);

        this.variableName = variableName;
        this.isVariableSpecified = isVariableSpecified;
    }

    @Override
    public String buildTemplate() {
        String returnValue = null;

        ST snippetTemplate = new ST(template);
        snippetTemplate.add("variable", variableName);
        snippetTemplate.add("isVariableSpecified", isVariableSpecified);

        returnValue = snippetTemplate.render();

        return returnValue;
    }
}

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
package com.leadtechnologist.licketyscript.bash.snippet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import com.leadtechnologist.licketyscript.bash.domain.BashOption;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class InputBashSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/lickety-script/bash-input-template.stg";
    private static final String VERBOSE_SWITCH_STATEMENT = "  -v | --verbose)\n"
            + "    __VERBOSE=7 # Verbose indicates logging at debug.\n"
            + "    shift\n"
            + "    ;;";

    private static final String HELP_SWITCH_STATEMENT = "  -h | --help)\n"
            + "    .printHelp # Show help\n"
            + "    ;;";

    private Set<BashOption> allInputOptions;
    private List<Snippet> allValidationSnippets = new ArrayList<>();

    public InputBashSnippet(SnippetContext context, @NonNull Set<BashOption> allInputOptions) throws IOException {
        super(context);
        setSnippetTemplate(TEMPLATE_LOCATION);
        this.allInputOptions = allInputOptions;

        context.addValue("bashOptions", allInputOptions);
        addDomainValuesToSnippetContext(context);
    }

    protected void addDomainValuesToSnippetContext(SnippetContext context) {
        List<String> allShortOpts = new ArrayList<>();
        List<String> allLongOpts = new ArrayList<>();
        List<String> allSwitchStatements = new ArrayList<>();
        List<String> allVariables = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(allInputOptions)) {
            for (BashOption bashOption : allInputOptions) {
                if (bashOption.getShortName() != null) {
                    String shortOptArgsValue = BooleanUtils.isTrue(bashOption.optionHasValue()) ? bashOption.getShortName() + ":" : bashOption.getShortName() + "";
                    allShortOpts.add(shortOptArgsValue);
                }

                if (StringUtils.isNotBlank(bashOption.getLongName())) {
                    String longOptArgsValue = BooleanUtils.isTrue(bashOption.optionHasValue()) ? bashOption.getLongName() + ":" : bashOption.getLongName() + "";
                    allLongOpts.add(longOptArgsValue);

                    allVariables.add(bashOption.getIsSetVariableName());

                    if (BooleanUtils.isTrue(bashOption.optionHasValue())) {
                        allVariables.add(bashOption.getVariableName());
                    }
                }

                String switchStatement;

                if (bashOption.equals(BashOption.HELP)) {
                    switchStatement = HELP_SWITCH_STATEMENT;
                } else if (StringUtils.equalsIgnoreCase(bashOption.getLongNameBashFriendly(), BashOption.VERBOSE.getLongNameBashFriendly())) {
                    switchStatement = VERBOSE_SWITCH_STATEMENT;
                } else {
                    switchStatement = createSwitchStatement(bashOption);
                }

                if (StringUtils.isNotBlank(switchStatement)) {
                    allSwitchStatements.add(switchStatement);
                }
            }
        }

        context.addValue("allShortOpts", allShortOpts);
        context.addValue("allLongOpts", allLongOpts);
        context.addValue("allSwitchStatements", allSwitchStatements);
        context.addValue("allVariables", allVariables);
    }

    protected String createSwitchStatement(BashOption bashOption) {
        String returnValue = null;
        returnValue = "";

        returnValue += bashOption.getShortName() != null ?
                "-" + bashOption.getShortName() + " | " + "--" + bashOption.getLongName() + ")" :
                "-" + bashOption.getLongName() + ")";
        returnValue += "\n";

        returnValue += bashOption.getIsSetVariableName() + "=1";
        returnValue += "\n";

        if (bashOption.optionHasValue()) {
            returnValue += bashOption.getVariableName() + "=\"$2\"";
            returnValue += "\n";
            returnValue += "shift";
            returnValue += "\n";
        }
        else {
            returnValue += "";
            returnValue += "\n";
        }

        returnValue += ";;";

        return returnValue;
    }

    @Override
    protected String buildTemplate() {
        String returnValue = null;

        returnValue = super.buildTemplate();

        // Create all validation snippets.
        for (Snippet validationSnippet : allValidationSnippets) {
            returnValue += "\n";
            returnValue += validationSnippet.getSnippet();
        }

        return returnValue;
    }
}

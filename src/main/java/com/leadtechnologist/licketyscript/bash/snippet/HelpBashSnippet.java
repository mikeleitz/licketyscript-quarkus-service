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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Set;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import com.leadtechnologist.licketyscript.bash.domain.BashOption;
import com.leadtechnologist.licketyscript.bash.domain.ShellOptionEnum;
import io.micrometer.core.instrument.util.StringUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class HelpBashSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/lickety-script/bash-help-template.stg";

    private Set<BashOption> allInputOptions;
    private String year;
    private String date;
    private String scriptExtension;
    private String author;
    private String scriptName;
    private String scriptVersion;
    private String scriptPurpose;

    public HelpBashSnippet(SnippetContext context,
                           Set<BashOption> allInputOptions,
                           String author,
                           ShellOptionEnum shellOptionEnum,
                           String scriptName,
                           String scriptVersion,
                           String scriptPurpose) throws IOException {
        super(context);
        setSnippetTemplate(TEMPLATE_LOCATION);

        this.allInputOptions = allInputOptions;
        this.author = author;
        this.scriptName = scriptName;
        this.scriptVersion = scriptVersion;
        this.scriptPurpose = scriptPurpose;

        scriptExtension = shellOptionEnum.getExtension();

        LocalDate localDate = LocalDate.now();
        year = localDate.format(DateTimeFormatter.ofPattern("yyyy"));

        date = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(localDate);

        String helpBody = generateHelpBody();

        context.addValue("year", year);
        context.addValue("author", author);
        context.addValue("scriptName", scriptName);
        context.addValue("scriptExtension", scriptExtension);
        context.addValue("scriptVersion", scriptVersion);
        context.addValue("date", date);
        context.addValue("scriptPurpose", scriptPurpose);
        context.addValue("helpBody", helpBody);
    }

    protected String generateHelpBody() {
        String returnValue = "";

        Integer maxLength = findLongestHelpCommands();
        // 6 + maxLength + 2 + 3
        // 5 spaces
        // The rest
        Integer columnOneSpace = maxLength + 11;
        String formatString = "echo \"%-" + columnOneSpace + "s     %s\"";

        for (BashOption inputOption : allInputOptions) {
            if (inputOption.getShortName() != null && StringUtils.isNotBlank(inputOption.getShortName() + "")) {
                returnValue += String.format(formatString, "      -" + inputOption.getShortName() + ", --" + inputOption.getLongName(), StringUtils.isNotBlank(inputOption.getHelpText()) ? inputOption.getHelpText() : "");
            } else {
                returnValue += String.format(formatString, "          --" + inputOption.getLongName(), StringUtils.isNotBlank(inputOption.getHelpText()) ? inputOption.getHelpText() : "");

            }
            returnValue += "\n";
        }

        return returnValue;
    }

    protected Integer findLongestHelpCommands() {
        Integer returnValue = null;

        Integer maxLength = 0;
        for (BashOption bashOption : allInputOptions) {
            Integer totalLength = 0;

            if (bashOption.getShortName() != null) {
                totalLength += 1;
            }

            if (StringUtils.isNotBlank(bashOption.getLongName())) {
                totalLength += bashOption.getLongName().length();
            }

            if (totalLength > maxLength) {
                maxLength = totalLength;
            }
        }

        returnValue = maxLength;

        return returnValue;
    }
}

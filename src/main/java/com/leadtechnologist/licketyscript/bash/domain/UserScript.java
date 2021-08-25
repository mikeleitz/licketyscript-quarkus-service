package com.leadtechnologist.licketyscript.bash.domain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import com.leadtechnologist.licketyscript.base.application.ApplicationFile;
import com.leadtechnologist.licketyscript.bash.snippet.ShebangBashSnippet;
import com.leadtechnologist.licketyscript.bash.snippet.userscript.UserScriptBody;
import com.leadtechnologist.licketyscript.bash.snippet.userscript.UserScriptInstructions;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class UserScript extends ApplicationFile {
    protected ShellOptionEnum shellOptionEnum = ShellOptionEnum.BASH;
    protected SnippetContext snippetContext = new SnippetContext();
    protected BashScript bashScriptConfiguration;

    @SneakyThrows
    public UserScript(BashScriptConfiguration bashScriptConfiguration) {
        this.fileRole = "user script file";
        this.fileName = bashScriptConfiguration.getScriptName() + bashScriptConfiguration.getShellType().extension;

        ShebangBashSnippet shebangBashSnippet = new ShebangBashSnippet(snippetContext, shellOptionEnum);
        preambleList.add(shebangBashSnippet);

        String scriptName = bashScriptConfiguration.getScriptName();
        String extension = bashScriptConfiguration.getShellType().getExtension();
        snippetContext.addValue("scriptFile", scriptName + extension);
        snippetContext.addValue("scriptName", scriptName);

        List<String> allVariables = createAllVariablesList(bashScriptConfiguration);

        UserScriptInstructions userScriptInstructions = new UserScriptInstructions(snippetContext, allVariables);
        preambleList.add(userScriptInstructions);

        UserScriptBody userScriptBody = new UserScriptBody(snippetContext, allVariables);
        processingList.add(userScriptBody);
    }

    @Override
    public String getFileContents() {
        String returnValue;

        StringBuilder fileContents = new StringBuilder();
        for (Snippet snippet : preambleList) {
            fileContents.append(snippet.getSnippet());
            fileContents.append("\n");
        }

       for (Snippet snippet : processingList) {
            fileContents.append(snippet.getSnippet());
            fileContents.append("\n");
        }

        returnValue = fileContents.toString();

        return returnValue;
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public File toFile(String fullPath) throws IOException {
        File returnValue = new File(fullPath + fileName + shellOptionEnum.extension);

        FileUtils.writeStringToFile(returnValue, getFileContents(), Charset.defaultCharset());

        returnValue.setWritable(true);
        returnValue.setReadable(true);
        returnValue.setExecutable(true);

        return returnValue;
    }

    protected List<String> createAllVariablesList(BashScriptConfiguration bashScriptConfiguration) {
        List<String> returnValue = new ArrayList<>();

        for (BashOption bashOption : bashScriptConfiguration.getBashOptions()) {
            if (StringUtils.isNotBlank(bashOption.getLongName())) {
                returnValue.add(bashOption.getIsSetVariableName());

                if (BooleanUtils.isTrue(bashOption.optionHasValue())) {
                    returnValue.add(String.format("%s", bashOption.getVariableName()));
                }
            }
        }

        return returnValue;
    }
}

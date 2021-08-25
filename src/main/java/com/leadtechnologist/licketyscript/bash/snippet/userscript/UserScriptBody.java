package com.leadtechnologist.licketyscript.bash.snippet.userscript;

import java.util.List;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class UserScriptBody extends Snippet {
    private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/user-script/user-script-body.stg";

    @SneakyThrows
    public UserScriptBody(SnippetContext context, List<String> optionList) {
        super(context);

        setSnippetTemplate(TEMPLATE_LOCATION);

        context.addValue("allVariables", optionList);
    }

}

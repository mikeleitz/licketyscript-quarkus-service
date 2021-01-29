package com.leadtechnologist.licketyscript.bash.snippet.license;

import java.io.IOException;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;

/**
 * @author leitz@mikeleitz.com
 */
public class LicenseSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/license/license-apache.stg";

    public LicenseSnippet(SnippetContext context) throws IOException {
        super(context);
        setSnippetTemplate(TEMPLATE_LOCATION);
    }
}

package com.leadtechnologist.licketyscript.bash.snippet.manifest;

import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class ManifestBody extends Snippet {
    private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/manifest/manifest-body.stg";

    @SneakyThrows
    public ManifestBody(SnippetContext context) {
        super(context);

        setSnippetTemplate(TEMPLATE_LOCATION);
    }

}

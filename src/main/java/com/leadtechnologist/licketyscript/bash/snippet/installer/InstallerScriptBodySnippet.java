package com.leadtechnologist.licketyscript.bash.snippet.installer;

import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class InstallerScriptBodySnippet extends Snippet {
  private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/installer-script/installer-script-body.stg";

  @SneakyThrows
  public InstallerScriptBodySnippet(SnippetContext context) {
    super(context);

    setSnippetTemplate(TEMPLATE_LOCATION);
  }
}

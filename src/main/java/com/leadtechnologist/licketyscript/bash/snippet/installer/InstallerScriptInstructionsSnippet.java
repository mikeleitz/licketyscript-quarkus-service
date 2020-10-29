package com.leadtechnologist.licketyscript.bash.snippet.installer;

import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class InstallerScriptInstructionsSnippet extends Snippet {
  private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/installer-script/installer-script-instructions.stg";

  @SneakyThrows
  public InstallerScriptInstructionsSnippet(SnippetContext context) {
    super(context);

    setSnippetTemplate(TEMPLATE_LOCATION);
  }
}

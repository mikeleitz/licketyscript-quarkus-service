package com.leadtechnologist.licketyscript.bash.snippet.readme;

import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class ReadmeSnippet extends Snippet {
  private static final String TEMPLATE_LOCATION = "META-INF/resources/bash/readme-file/readme-instructions.stg";

  @SneakyThrows
  public ReadmeSnippet(SnippetContext context) {
    super(context);

    setSnippetTemplate(TEMPLATE_LOCATION);
  }
}

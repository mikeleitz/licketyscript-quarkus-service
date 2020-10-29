package com.leadtechnologist.licketyscript;

/**
 * @author leitz@mikeleitz.com
 */
public class SnippetTemplateFactoryThreadLocal {
    private static final ThreadLocal<SnippetTemplateFactory> context = new ThreadLocal<SnippetTemplateFactory>();

    public static SnippetTemplateFactory getSnippetTemplateFactory() {
        return context.get();
    }

    public static void setSnippetTemplateFactory(SnippetTemplateFactory snippetTemplateFactory) {
        context.set(snippetTemplateFactory);
    }
}

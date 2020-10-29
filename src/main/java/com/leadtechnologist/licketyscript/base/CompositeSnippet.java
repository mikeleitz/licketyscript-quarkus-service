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

package com.leadtechnologist.licketyscript.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
public class CompositeSnippet extends Snippet {
    private List<Snippet> snippets;

    public CompositeSnippet(SnippetContext context) throws IOException {
        super(context);
        this.snippets = new ArrayList<>();
    }

    public CompositeSnippet(SnippetContext context, List<Snippet> snippets) {
        super(context);
        this.snippets = snippets;
    }

    public void addSnippet(Snippet snippet) {
        snippets.add(snippet);
    }

    public Integer totalSnippets() {
        return CollectionUtils.size(this.snippets);
    }

    @Override
    protected String buildTemplate() {
        String returnValue;

        returnValue = snippets.stream()
                .peek(s -> log.debug("Building template for snippet [{}]", s.getName()))
                .map(s -> s.buildTemplate())
                .collect(Collectors.joining("\n\n"));

        return returnValue;
    }

}

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

package com.leadtechnologist.licketyscript.bash.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/**
 * @author leitz@mikeleitz.com
 */
@Data
public class BashScriptConfiguration {
    private Long id;
    private String scriptName;
    private ShellOptionEnum shellType;
    private Set<BashOption> bashOptions = new HashSet<>();
    private String author = "";
    private String version = "";
    private String purpose = "";

    public void addScriptInput(BashOption bashOption) {
        if (bashOptions == null) {
            bashOptions = new HashSet<>();
        }

        bashOptions.add(bashOption);
    }
}

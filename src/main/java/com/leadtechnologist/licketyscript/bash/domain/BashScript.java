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

import lombok.Builder;

/**
 * @author leitz@mikeleitz.com
 */
@Builder
public class BashScript {
//    @NonNull private BashPreamble bashPreamble;
//    @NonNull private BashInput bashInput;

    public String buildScript() {
        String returnValue = null;

/*        Object preambleResult = bashPreamble.buildResult().getResult();
        Object inputResult = bashInput.buildResult().getResult();

        StringBuilder stringBuilder = new StringBuilder(preambleResult.toString())
                .append("\n")
                .append(inputResult.toString());

        returnValue = stringBuilder.toString();*/

        return returnValue;
    }
}

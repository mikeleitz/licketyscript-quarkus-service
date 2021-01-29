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

package com.leadtechnologist.licketyscript.bash.service;

import java.util.List;
import com.leadtechnologist.licketyscript.base.application.ApplicationFile;
import com.leadtechnologist.licketyscript.base.application.LicketyScriptFile;
import com.leadtechnologist.licketyscript.bash.domain.BashScriptConfiguration;

/**
 * @author leitz@mikeleitz.com
 */
public interface BashService {
    ApplicationFile createDelegateBashScriptContents(BashScriptConfiguration bashScriptConfiguration);

    ApplicationFile createReadmeContents(BashScriptConfiguration bashScriptConfiguration);

    ApplicationFile createInstallerContents(BashScriptConfiguration bashScriptConfiguration);

    ApplicationFile createUserBashScriptContents(BashScriptConfiguration bashScriptConfiguration);

    ApplicationFile createJsonUsedToCreateScript(String json);

    ApplicationFile createLicense(String year, String copyrightName);

    ApplicationFile createManifestContents(BashScriptConfiguration bashScriptConfiguration, List<LicketyScriptFile> applicationFiles);

}

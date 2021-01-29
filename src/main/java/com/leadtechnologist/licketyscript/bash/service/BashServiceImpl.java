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

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import com.leadtechnologist.licketyscript.base.application.ApplicationFile;
import com.leadtechnologist.licketyscript.base.application.LicketyScriptFile;
import com.leadtechnologist.licketyscript.bash.domain.BashFile;
import com.leadtechnologist.licketyscript.bash.domain.BashScriptConfiguration;
import com.leadtechnologist.licketyscript.bash.domain.InstallerScript;
import com.leadtechnologist.licketyscript.bash.domain.LicenseFile;
import com.leadtechnologist.licketyscript.bash.domain.ManifestFile;
import com.leadtechnologist.licketyscript.bash.domain.ReadmeFile;
import com.leadtechnologist.licketyscript.bash.domain.UserScript;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@ApplicationScoped
public class BashServiceImpl implements BashService {

    @Override
    @SneakyThrows
    public ApplicationFile createDelegateBashScriptContents(BashScriptConfiguration bashScriptConfiguration) {
        ApplicationFile returnValue = null;

        log.debug("Started creating bash script [{}].", bashScriptConfiguration.getScriptName());

        returnValue = new BashFile(bashScriptConfiguration);

        log.debug("Completed creating bash script [{}].  It has content length [{}].", bashScriptConfiguration.getScriptName(), returnValue.getFileContents().length());

        return returnValue;
    }

    @Override
    public ApplicationFile createReadmeContents(BashScriptConfiguration bashScriptConfiguration) {
        ApplicationFile returnValue;

        log.debug("Started creating readme [{}].", bashScriptConfiguration.getScriptName());

        returnValue = new ReadmeFile(bashScriptConfiguration);

        log.debug("Completed creating readme [{}].  It has content length [{}].", bashScriptConfiguration.getScriptName(), returnValue.getFileContents().length());

        return returnValue;
    }

    @Override
    public ApplicationFile createInstallerContents(BashScriptConfiguration bashScriptConfiguration) {
        ApplicationFile returnValue;

        log.debug("Started creating installer script for [{}].", bashScriptConfiguration.getScriptName());

        returnValue = new InstallerScript(bashScriptConfiguration);

        log.debug("Completed creating installer script for [{}].  It has content length [{}].", bashScriptConfiguration.getScriptName(), returnValue.getFileContents().length());

        return returnValue;
    }

    @Override
    public ApplicationFile createUserBashScriptContents(BashScriptConfiguration bashScriptConfiguration) {
        ApplicationFile returnValue;

        log.debug("Started creating user script for [{}].", bashScriptConfiguration.getScriptName());

        returnValue = new UserScript(bashScriptConfiguration);

        log.debug("Completed creating user script for [{}].  It has content length [{}].", bashScriptConfiguration.getScriptName(), returnValue.getFileContents().length());

        return returnValue;
    }

    @Override
    public ApplicationFile createJsonUsedToCreateScript(String json) {
        ApplicationFile returnValue = null;

        log.debug("Started creating file with the script creation json payload.");

        log.debug("Completed creating file with the script creation json payload.");

        return returnValue;
    }

    @Override
    public ApplicationFile createLicense(String year, String copyrightName) {
        ApplicationFile returnValue;

        log.debug("Started creating license file.");

        returnValue = new LicenseFile(year, copyrightName);

        log.debug("Completed creating license file.");

        return returnValue;
    }

    @Override
    public ApplicationFile createManifestContents(BashScriptConfiguration bashScriptConfiguration, List<LicketyScriptFile> applicationFiles) {
        ApplicationFile returnValue;

        log.debug("Started creating manifestFile file for [{}].", bashScriptConfiguration.getScriptName());

        returnValue = new ManifestFile(bashScriptConfiguration, applicationFiles);

        log.debug("Completed creating manifestFile file for [{}].  It has content length [{}].", bashScriptConfiguration.getScriptName(), returnValue.getFileContents().length());

        return returnValue;
    }
}

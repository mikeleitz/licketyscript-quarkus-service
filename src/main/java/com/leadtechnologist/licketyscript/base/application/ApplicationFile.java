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

package com.leadtechnologist.licketyscript.base.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.leadtechnologist.licketyscript.base.Snippet;

/**
 * @author leitz@mikeleitz.com
 */
public abstract class ApplicationFile implements LicketyScriptFile {
    protected String fileName;
    protected String fileRole;

    protected List<Snippet> preambleList = new ArrayList<>();
    protected List<Snippet> functionsAndSupplementList = new ArrayList<>();
    protected List<Snippet> applicationInputList = new ArrayList<>();
    protected List<Snippet> processingList = new ArrayList<>();

    protected String relativePath;  // relative to the repo's root.

    public String getFileName() {
        return fileName;
    }

    public String getFileRole() {
        return fileRole;
    }

    /**
     * Returns a JSON string containing the raw file contents plus any relevant metadata.
     *
     * Metadata is things like this file's path relative to the repo root, character encoding, etc.
     *
     * @return the raw file contents plus metadata
     */
    public abstract String toJson();

    /**
     * Saves the file directly to disk.  The file will be readable/writable/executable.
     *
     * @return the actual file with its contents populated
     */
    public abstract File toFile(String fullPath) throws IOException;
}

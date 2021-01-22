package com.leadtechnologist.licketyscript.base.application;

import lombok.AllArgsConstructor;

/**
 * @author leitz@mikeleitz.com
 */
@AllArgsConstructor
public class TextFile implements LicketyScriptFile {
    private String fileContents;
    private String fileName;
    private String fileRole;

    @Override
    public String getFileContents() {
        return fileContents;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getFileRole() {
        return fileRole;
    }
}

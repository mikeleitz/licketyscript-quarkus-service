package com.leadtechnologist.licketyscript.base.application;

/**
 * @author leitz@mikeleitz.com
 */
public interface LicketyScriptFile {
    /**
     * @return the raw file contents
     */
    String getFileContents();

    String getFileName();

    String getFileRole();
}

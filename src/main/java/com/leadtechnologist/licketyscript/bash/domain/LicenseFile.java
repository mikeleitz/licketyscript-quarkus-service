package com.leadtechnologist.licketyscript.bash.domain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import com.leadtechnologist.licketyscript.base.application.ApplicationFile;
import com.leadtechnologist.licketyscript.bash.snippet.license.LicenseSnippet;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class LicenseFile extends ApplicationFile {
    private static final String FILE_NAME = "license";
    private static final String FILE_ROLE = "script's license";

    protected SnippetContext snippetContext = new SnippetContext();

    @SneakyThrows
    public LicenseFile(String year, String copyrightName) {
        this.fileName = FILE_NAME;
        this.fileRole = FILE_ROLE;

        snippetContext.addValue("year", year);
        snippetContext.addValue("copyrightName", copyrightName);

        LicenseSnippet licenseSnippet = new LicenseSnippet(snippetContext);
        preambleList.add(licenseSnippet);
    }

    @Override
    public File toFile(String fullPath) throws IOException {
        File returnValue = new File(fullPath + this.fileName);

        FileUtils.writeStringToFile(returnValue, getFileContents(), Charset.defaultCharset());

        returnValue.setWritable(true);
        returnValue.setReadable(true);
        returnValue.setExecutable(true);

        return returnValue;
    }

    @Override
    public String getFileContents() {
        String returnValue;

        StringBuilder fileContents = new StringBuilder();
        for (Snippet snippet : preambleList) {
            fileContents.append(snippet.getSnippet());
            fileContents.append("\n");
        }

        returnValue = fileContents.toString();

        return returnValue;
    }

    @Override
    public String toJson() {
        return null;
    }

}

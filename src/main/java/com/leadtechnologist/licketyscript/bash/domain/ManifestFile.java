package com.leadtechnologist.licketyscript.bash.domain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import com.leadtechnologist.licketyscript.base.Snippet;
import com.leadtechnologist.licketyscript.base.SnippetContext;
import com.leadtechnologist.licketyscript.base.application.ApplicationFile;
import com.leadtechnologist.licketyscript.bash.snippet.manifest.ManifestBody;
import com.leadtechnologist.licketyscript.bash.snippet.manifest.ManifestInstructions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * @author leitz@mikeleitz.com
 */
public class ManifestFile extends ApplicationFile {
    protected ShellOptionEnum shellOptionEnum = ShellOptionEnum.BASH;

    protected SnippetContext snippetContext = new SnippetContext();
    protected List<ApplicationFile> allApplicationFiles;

    public ManifestFile(BashScriptConfiguration bashScriptConfiguration, List<ApplicationFile> allApplicationFiles) {
        this.fileRole = "manifest file";
        this.fileName = "manifest";

        this.allApplicationFiles = allApplicationFiles;

        ManifestInstructions manifestInstructions = new ManifestInstructions(snippetContext);
        preambleList.add(manifestInstructions);

        ManifestBody manifestBody = new ManifestBody(snippetContext);
        processingList.add(manifestBody);

        List<ImmutablePair> nameAndRoleList = allApplicationFiles.stream().map(f -> new ImmutablePair(f.getFileName(), f.getFileRole())).collect(Collectors.toList());
        nameAndRoleList.add(new ImmutablePair(this.getFileName(), this.getFileRole()));

        snippetContext.addValue("nameAndRoleList", nameAndRoleList);
    }

    @Override
    public String getFileContents() {
        String returnValue;

        StringBuilder fileContents = new StringBuilder();
        for (Snippet snippet : preambleList) {
            fileContents.append(snippet.getSnippet());
            fileContents.append("\n");
        }

        for (Snippet snippet : processingList) {
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

    @Override
    public File toFile(String fullPath) throws IOException {
        File returnValue = new File(fullPath + fileName + shellOptionEnum.extension);

        FileUtils.writeStringToFile(returnValue, getFileContents(), Charset.defaultCharset());

        returnValue.setWritable(true);
        returnValue.setReadable(true);
        returnValue.setExecutable(true);

        return returnValue;
    }
}

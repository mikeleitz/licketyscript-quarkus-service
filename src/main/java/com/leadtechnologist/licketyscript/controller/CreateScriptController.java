package com.leadtechnologist.licketyscript.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.leadtechnologist.licketyscript.SnippetTemplateFactory;
import com.leadtechnologist.licketyscript.SnippetTemplateFactoryThreadLocal;
import com.leadtechnologist.licketyscript.base.application.ApplicationFile;
import com.leadtechnologist.licketyscript.base.application.LicketyScriptFile;
import com.leadtechnologist.licketyscript.base.application.TextFile;
import com.leadtechnologist.licketyscript.bash.domain.BashOption;
import com.leadtechnologist.licketyscript.bash.domain.BashScriptConfiguration;
import com.leadtechnologist.licketyscript.bash.service.BashService;
import com.leadtechnologist.licketyscript.utils.JsonThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@Path("/scripts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreateScriptController {
    SnippetTemplateFactory snippetTemplateFactory;
    BashService bashService;
    String year = "2021";
    String copyrightName = "Lead Technologist LLC";

    public CreateScriptController(SnippetTemplateFactory snippetTemplateFactory, BashService bashService) {
        this.snippetTemplateFactory = snippetTemplateFactory;
        SnippetTemplateFactoryThreadLocal.setSnippetTemplateFactory(this.snippetTemplateFactory);
        this.bashService = bashService;
    }

    @POST
    public Response createScript(BashScriptConfiguration configuration) {
        log.info("Received create script request for data [{}].", configuration);

        String jsonStringValue = JsonThreadLocal.get();
        log.info("Json thread local value : [{}].", jsonStringValue);
        JsonThreadLocal.unset();

        String userBashFileName = configuration.getScriptName();

        configuration.addScriptInput(BashOption.HELP);
        configuration.addScriptInput(BashOption.VERBOSE);

        ApplicationFile bashScriptFile = bashService.createDelegateBashScriptContents(configuration);
        ApplicationFile installerScriptFile = bashService.createInstallerContents(configuration);
        ApplicationFile readmeFile = bashService.createReadmeContents(configuration);
        ApplicationFile userBashScriptFile = bashService.createUserBashScriptContents(configuration);
        ApplicationFile licenseFile = bashService.createLicense(year, copyrightName);

        TextFile jsonConfiguration = new TextFile(jsonStringValue, "script-configuration.json", "api payload");

        ApplicationFile manifestFile = bashService.createManifestContents(configuration, List.of(bashScriptFile, installerScriptFile, readmeFile, userBashScriptFile, licenseFile));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        createZipFileStream(baos, userBashFileName, List.of(bashScriptFile, installerScriptFile, readmeFile, userBashScriptFile, manifestFile, jsonConfiguration, licenseFile));

        return Response
            .ok((StreamingOutput) output -> {
                try {
                    InputStream input = new ByteArrayInputStream(baos.toByteArray());
                    baos.flush();
                    IOUtils.copy(input, output);
                    output.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            })
            .type("application/zip")
            .header("Content-Disposition", "attachment; filename=\"" + userBashFileName + ".zip\"")
            .build();
    }

    private void createZipFileStream(ByteArrayOutputStream baos, String scriptName, List<LicketyScriptFile> licketyScriptFiles) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(baos)) {
            zipOutputStream.putNextEntry(new ZipEntry(scriptName + "/"));
            zipOutputStream.closeEntry();

            for (LicketyScriptFile licketyScriptFile : licketyScriptFiles) {
                ZipEntry entry = new ZipEntry(scriptName + "/" + licketyScriptFile.getFileName());

                zipOutputStream.putNextEntry(entry);
                zipOutputStream.write(licketyScriptFile.getFileContents().getBytes(Charset.defaultCharset()));
                zipOutputStream.closeEntry();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

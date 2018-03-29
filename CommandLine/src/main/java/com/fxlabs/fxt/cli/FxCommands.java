package com.fxlabs.fxt.cli;

import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.sdk.services.BotLogger;
import com.fxlabs.fxt.sdk.services.CredUtils;
import com.fxlabs.fxt.sdk.services.FxCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Size;

/**
 * @author Intesar Shannan Mohammed
 */
@ShellComponent
public class FxCommands {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FxCommandService service;

    private boolean connected;

    /*@ShellMethod(key = "fx load", value = "Loads project files into Fx server")
    public void load() {
        service.load();
    }
    */


    /*@ShellMethod(key = "project ls", value = "Lists Projects")
    public void projectLs() {
        service.lsJobs();
    }

    @ShellMethod(key = "job ls", value = "Lists Job")
    public void jobLs() {
        service.lsJobs();
    }*/

    @ShellMethod(key = "run", value = "Executes job")
    public void run(
            @ShellOption(value = {"-p", "--project"}, help = "Project name e.g. org/project-name or FxLabs/Common") @Size(min = 3) String project,
            @ShellOption(value = {"-d", "--dir"}, help = "Project directory path e.g. /opt/Project1 or C:/Project1", defaultValue = "") String projectDir,
            @ShellOption(value = {"-j", "--job"}, help = "Job name from Fxfile.yaml e.g. Stg or Dev etc", defaultValue = "Default") String jobName,
            @ShellOption(value = {"-r", "--region"}, help = "Override bot region for the job e.g. org/region or FxLabs/US_WEST_1", defaultValue = "") String region,
            @ShellOption(value = {"-t", "--tags"}, help = "Override tags e.g. V1,V2", defaultValue = "") String tags,
            @ShellOption(value = {"-e", "--env"}, help = "Override env for the job e.g. Stg or Dev etc", defaultValue = "") String envName,
            @ShellOption(value = {"-s", "--suites"}, help = "Comma separated test-suite names ", defaultValue = "") String suites) {

        try {
            CredUtils.taskLogger.set(new BotLogger());
            service.loadAndRun(projectDir, project, jobName, region, tags, envName, suites);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

    /*@ShellMethod(key = "login", value = "Login to Fx. CLI will try to read credentials from the following locations \n"
            + "fx.properties" + "\n"
            + "/opt/fx/fx.properties" + "\n"
            + "/var/fx/fx.properties" + "\n"
            + "${user.home}/fxt/fx.properties" + "\n"
            + "e.g. file content"  + "\n"
            + "url=https://cloud.fxlabs.io" + "\n"
            + "username=me@company.com" + "\n"
            + "password=mypassword"
    )
    public void login(
            @ShellOption(value = {"-h", "--host"}, help = "Fx URL e.g. https://cloud.fxlabs.io", defaultValue = "https://cloud.fxlabs.io") String host,
            @ShellOption(value = {"-u", "--user"}, help = "Account username e.g. me@company.com", defaultValue = "") String user,
            @ShellOption(value = {"-p", "--password"}, help = "Account password", defaultValue = "") String pass) {


        // check if -f is given try to load from the file.
        // else if host, u, p are empty try to load from the default file
        // else if host, u, p are emtpy throw error


        CredUtils.url.set(host);
        CredUtils.username.set(user);
        CredUtils.password.set(pass);

        tryLogin();

    }*/

    public Availability runAvailability() {
        return connected
                ? Availability.available()
                : Availability.unavailable("you are not logged-in. Please login first.");
    }

    private void resetLogin() {
        CredUtils.url.set(null);
        CredUtils.username.set(null);
        CredUtils.password.set(null);
        connected = false;
        printLoginHelp();
    }

    private void printLoginHelp() {
        String value = "\nFX-CLI will try to read login credentials from the following locations \n"
                + "fx.properties" + "\n"
                + "/opt/fx/fx.properties" + "\n"
                + "/var/fx/fx.properties" + "\n"
                + "${user.home}/fxt/fx.properties" + "\n\n"
                + "e.g. file content" + "\n"
                + "url=https://cloud.fxlabs.io" + "\n"
                + "username=me@company.com" + "\n"
                + "password=mypassword\n";

        System.out.println(value);
        System.exit(0);
    }

    @PostConstruct
    private void tryLogin() {
        try {
            System.out.println("Reading credentials from fx.properties");
            Response<Users> response = service.login();
            if (response == null || response.isErrors() || response.getData() == null) {
                System.out.println(
                        AnsiOutput.toString(AnsiColor.RED,
                                "Login failed!"
                                , AnsiColor.DEFAULT)
                );

                for (Message m : response.getMessages()) {
                    System.out.println(
                            AnsiOutput.toString(AnsiColor.RED,
                                    m.getValue()
                                    , AnsiColor.DEFAULT)
                    );

                    resetLogin();
                    return;
                }
            }
            connected = true;
            String name = response.getData().getName();
            if (StringUtils.isEmpty(name)) {
                name = response.getData().getEmail();
            }
            System.out.println(
                    AnsiOutput.toString(AnsiColor.DEFAULT,
                            String.format("Welcome %s!", name)
                            , AnsiColor.DEFAULT)
            );
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            System.out.println(
                    AnsiOutput.toString(AnsiColor.RED,
                            "Login failed!"
                            , AnsiColor.DEFAULT)
            );
            resetLogin();
        }
    }

    /*@ShellMethod(key = "run inspect", value = "Inspect Run")
    public void inspectRun(String id) {
        service.inspectRun(id);
    }

    @ShellMethod(key = "run ls", value = "Lists Runs")
    public void runLs() {
        service.lsRuns();
    }
    */


}

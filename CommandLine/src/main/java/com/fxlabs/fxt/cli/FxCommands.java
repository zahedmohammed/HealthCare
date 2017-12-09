package com.fxlabs.fxt.cli;

import com.fxlabs.fxt.cli.services.FxCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class FxCommands {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FxCommandService service;

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

    @ShellMethod(key = "run", value = "Loads data and executes tests")
    public void jobRun(@ShellOption(defaultValue = "") String projectDir) {
        service.loadAndRun(projectDir);
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

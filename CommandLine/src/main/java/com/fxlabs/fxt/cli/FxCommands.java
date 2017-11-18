package com.fxlabs.fxt.cli;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.fxlabs.fxt.dto.run.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.table.*;
import org.springframework.stereotype.Component;


import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
public class FxCommands {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FxCommandService service;

    /*@ShellMethod(key = "fx load", value = "Loads project files into Fx server")
    public void load() {
        service.load();
    }
    */


    /*@ShellMethod(key = "project ls", value = "Lists Projects")
    public void projectLs() {
        service.lsJobs();
    }

    @ShellMethod(key = "job ls", value = "Lists Jobs")
    public void jobLs() {
        service.lsJobs();
    }*/

    @ShellMethod(key = "run", value = "Loads data and executes tests")
    public void jobRun() {
        service.loadAndRun();
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

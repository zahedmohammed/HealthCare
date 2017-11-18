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

    @ShellMethod("Loads project files into Fx server")
    public void load() {
        service.load();
    }

    @ShellMethod("Lists Jobs")
    public void jobLs() {
        service.lsJobs();
    }

    @ShellMethod("Run Job")
    public void jobRun(String id) {
        service.runJob(id);
    }

    @ShellMethod("Inspect Run")
    public void inspectRun(String id) {
        service.inspectRun(id);
    }


}

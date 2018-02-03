package com.fxlabs.fxt.gaas.services;

import com.fxlabs.fxt.dto.git.GitTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
public class GitServiceTest {

    //@Autowired
    private GitService service;

    @Test
    public void process() throws Exception {

        GitTask task = new GitTask();
        task.setProjectId("fx-100");
        task.setGitUrl("https://github.com/intesar/HTML.git");
        //System.out.println(service.process(task));

    }

}
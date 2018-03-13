package com.fxlabs.fxt.it.git.skill.services;

import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.it.ITTask;
import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    //@Autowired
    private CloudService service;

    @Test
    public void process() throws Exception {

        ITTask task = new ITTask();
//        task.setProjectId("fx-100");
//        task.setVcUrl("https://github.com/intesar/HTML.git");
        //System.out.println(service.process(task));

    }

}
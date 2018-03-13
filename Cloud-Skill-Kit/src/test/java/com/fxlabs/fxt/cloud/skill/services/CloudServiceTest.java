package com.fxlabs.fxt.cloud.skill.services;

import com.fxlabs.fxt.dto.vc.VCTask;
import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
public class CloudServiceTest {

    //@Autowired
    private CloudService service;

    @Test
    public void process() throws Exception {

        VCTask task = new VCTask();
        task.setProjectId("fx-100");
        task.setVcUrl("https://github.com/intesar/HTML.git");
        //System.out.println(service.process(task));

    }

}
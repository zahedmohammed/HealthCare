package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.services.base.GenericService;
import com.fxlabs.fxt.services.base.Response;

public interface RunService extends GenericService<Run> {

    Response<Run> run(String projectJob);
}

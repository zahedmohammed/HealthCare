package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.dto.run.DataSet;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.services.base.GenericService;
import com.fxlabs.fxt.dto.base.Response;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RunService extends GenericService<Run, String> {

    Response<Run> run(String projectJob);

    public Response<List<DataSet>> findByRunId(String runId, Pageable pageable);
}

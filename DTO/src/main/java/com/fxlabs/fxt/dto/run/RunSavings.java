package com.fxlabs.fxt.dto.run;


import com.fxlabs.fxt.dto.project.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Mohammed Luqman Shareef
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RunSavings implements Serializable{

    private Job job;

    private Long runId;

    private Long timeSaved;

    private Long costSaved;

}


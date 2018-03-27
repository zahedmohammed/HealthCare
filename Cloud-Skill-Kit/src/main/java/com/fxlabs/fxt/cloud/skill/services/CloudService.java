package com.fxlabs.fxt.cloud.skill.services;

import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;

/**
 * @author Intesar Shannan Mohammed
 */
public interface CloudService {

    CloudTaskResponse create(CloudTask task);

    CloudTaskResponse destroy(CloudTask task);
}

package com.snaker.system.api.service.impl;

import com.snaker.system.api.service.IWorkflowService;
import com.snaker.system.utils.SnakerEngineFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements IWorkflowService {
    @Autowired
    private SnakerEngineFacadeImpl snakerEngineFacade;
    @Override
    public Integer deployWf() {

        return null;
    }
}

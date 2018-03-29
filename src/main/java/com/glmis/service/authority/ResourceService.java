package com.glmis.service.authority;

import com.glmis.repository.authority.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ibs on 16/11/6.
 */

@Service

public class ResourceService {
    @Autowired
    ResourceRepository resourceRepository;
}

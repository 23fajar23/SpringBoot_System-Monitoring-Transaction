package com.monitor.transaction.service;

import com.monitor.transaction.constant.ERole;
import com.monitor.transaction.model.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}

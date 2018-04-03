/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.oauth.uma.permission.service.impl;

import org.wso2.carbon.identity.oauth.uma.permission.service.PermissionService;
import org.wso2.carbon.identity.oauth.uma.permission.service.ReadPropertiesFile;
import org.wso2.carbon.identity.oauth.uma.permission.service.dao.PermissionTicketDAO;
import org.wso2.carbon.identity.oauth.uma.permission.service.exception.PermissionDAOException;
import org.wso2.carbon.identity.oauth.uma.permission.service.exception.UMAResourceException;
import org.wso2.carbon.identity.oauth.uma.permission.service.model.PermissionTicketModel;
import org.wso2.carbon.identity.oauth.uma.permission.service.model.Resource;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

/**
 * PermissionServiceImpl service is used for permission registration.
 */
public class PermissionServiceImpl implements PermissionService {

    private static final String UTC = "UTC";

    @Override
    public PermissionTicketModel issuePermissionTicket(List<Resource> resourceList, int tenantId) throws
            UMAResourceException, PermissionDAOException {

        PermissionTicketModel permissionTicketModel = new PermissionTicketModel();
        ReadPropertiesFile.readFileConfigValues(permissionTicketModel);

        //TODO: Make this an extension point.
        String ticketString = UUID.randomUUID().toString();
        permissionTicketModel.setTicket(ticketString);
        permissionTicketModel.setCreatedTime(Calendar.getInstance(TimeZone.getTimeZone(UTC)));
        permissionTicketModel.setStatus("ACTIVE");
        permissionTicketModel.setTenantId(tenantId);

        PermissionTicketDAO.persistPTandRequestedPermissions(resourceList, permissionTicketModel);

        return permissionTicketModel;
    }
}

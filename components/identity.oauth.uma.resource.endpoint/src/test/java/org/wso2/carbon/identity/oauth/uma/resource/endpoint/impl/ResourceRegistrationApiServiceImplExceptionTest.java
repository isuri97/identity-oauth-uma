package org.wso2.carbon.identity.oauth.uma.resource.endpoint.impl;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.mockito.Mock;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.context.internal.OSGiDataHolder;
import org.wso2.carbon.identity.oauth.uma.resource.endpoint.TestUtil;
import org.wso2.carbon.identity.oauth.uma.resource.endpoint.dto.ResourceDetailsDTO;

import org.wso2.carbon.identity.oauth.uma.resource.endpoint.exceptions.ResourceEndpointException;
import org.wso2.carbon.identity.oauth.uma.resource.service.ResourceService;
import org.wso2.carbon.identity.oauth.uma.resource.service.exceptions.UMAException;
import org.wso2.carbon.identity.oauth.uma.resource.service.model.Resource;
import org.wso2.carbon.identity.oauth.uma.resource.service.model.ScopeDataDO;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;


import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertNotEquals;


@PrepareForTest({BundleContext.class, ServiceTracker.class, PrivilegedCarbonContext.class, ResourceService.class})
public class ResourceRegistrationApiServiceImplExceptionTest extends PowerMockTestCase {

    private ResourceRegistrationApiServiceImpl resourcesApiService = null;
    private Resource resource;

    @Mock
    private ResourceService resourceService;

    @Mock
    private BundleContext mockBundleContext;

    @Mock
    private ServiceTracker mockServiceTracker;

    @Mock
    private MessageContext mockMessageContext;

    @ObjectFactory
    public IObjectFactory getObjectFactory() {

        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    @BeforeMethod
    public void setUp() throws Exception {

        resourcesApiService = new ResourceRegistrationApiServiceImpl();
        resource = new Resource();
        //Get OSGIservice by starting the tenant flow.
        whenNew(ServiceTracker.class).withAnyArguments().thenReturn(mockServiceTracker);
        TestUtil.startTenantFlow("carbon.super");
        Object[] services = new Object[1];
        services[0] = resourceService;
        when(mockServiceTracker.getServices()).thenReturn(services);
        OSGiDataHolder.getInstance().setBundleContext(mockBundleContext);

        resource.setName("photo_albem1");
        List<ScopeDataDO> scopeDataDO = new ArrayList<>();
        ScopeDataDO scopeDataDO1 = new ScopeDataDO();
        scopeDataDO1.setResourceId("67378");
        scopeDataDO.add(scopeDataDO1);
        resource.setScopeDataDOArray(scopeDataDO);
        resource.setIconUri("http://www.example.com/icons/sky.png");
        resource.setDescription("Collection of digital photographs");
        resource.setType("http://www.example.com/rsrcs/photoalbum");
    }

   /* @Test
    public void testGetResource() throws Exception {

        try {
            resourcesApiService.getResource(anyString(), mockMessageContext);
        } catch (ResourceEndpointException e) {
            assertNotEquals(e.getResponse().getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
        }
    }*/

   /* @Test
    public void testGetApplicationThrowableException() throws UMAException {
        //Test for invalid resource id.
        try {
            resourcesApiService.getResource(anyString(), mockMessageContext);
        } catch (ResourceEndpointException e) {
            assertEquals(e.getResponse().getStatus(), Response.Status.NOT_FOUND.getStatusCode());
        }
    }*/

    /*@Test
    public void testGetResourceIds() throws Exception {
        try {
            resourcesApiService.getResourceIds(mockMessageContext);
        } catch (ResourceEndpointException e) {
            assertNotEquals(e.getResponse().getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
        }

    }*/
    @Test
    public void testUpdateResource() throws Exception {

        ResourceDetailsDTO updateDetailsDTO = new ResourceDetailsDTO();
        updateDetailsDTO.setName("photo_albem");
        List<String> scopes = new ArrayList<>();
        scopes.add("scope1");
        updateDetailsDTO.setResource_scopes(scopes);
        updateDetailsDTO.setDescription("Collection of digital photographs");
        updateDetailsDTO.setIcon_uri("http://www.example.com/icons/sky.png");

        try {
            resourcesApiService.updateResource("", updateDetailsDTO, mockMessageContext);
        } catch (ResourceEndpointException e) {
            assertNotEquals(e.getResponse().getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
        }

    }

    @Test
    public void testUpdateApplicationThrowableException() throws UMAException {
        //Test for invalid resource id.
        ResourceDetailsDTO updateRequestDTO = new ResourceDetailsDTO();
        updateRequestDTO.setName("");
        try {
            resourcesApiService.updateResource("ClientID", updateRequestDTO, mockMessageContext);
        } catch (ResourceEndpointException e) {
            assertNotEquals(e.getResponse().getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }
}

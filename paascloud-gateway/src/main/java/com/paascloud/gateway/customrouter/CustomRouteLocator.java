/**
 * 
 */
package com.paascloud.gateway.customrouter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import com.paascloud.core.utils.RequestUtil;
import com.paascloud.gateway.fallback.UacFallbackProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xyy
 *
 */
@Slf4j
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ZuulProperties properties;
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    /**
     * @param servletPath
     * @param properties
     */
    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        log.info("servletPath:{}", servletPath);
    }

    /* (non-Javadoc)
     * @see org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator#refresh()
     */
    @Override
    public void refresh() {
        doRefresh();
    }
    
    @Override
    protected Route getRoute(ZuulRoute route, String path) {
        if (route == null) {
            return null;
        }
        if (log.isDebugEnabled()) {
            log.debug("route matched=" + route);
        }
        String targetPath = path;
        String prefix = this.properties.getPrefix();
        if(prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        if (path.startsWith(prefix + "/") && this.properties.isStripPrefix()) {
            targetPath = path.substring(prefix.length());
        }
        if (route.isStripPrefix()) {
            int index = route.getPath().indexOf("*") - 1;
            if (index > 0) {
                String routePrefix = route.getPath().substring(0, index);
                targetPath = targetPath.replaceFirst(routePrefix, "");
                prefix = prefix + routePrefix;
            }
        }
        Boolean retryable = this.properties.getRetryable();
        if (route.getRetryable() != null) {
            retryable = route.getRetryable();
        }
        
        targetPath = RequestUtil.handleTargePath(targetPath);
        String appId = RequestUtil.getAppId(targetPath);
        String location = route.getLocation();
        if (null != appId) {
            location += "-" + appId;
        }
        return new Route(route.getId(), targetPath, location, prefix,
                retryable,
                route.isCustomSensitiveHeaders() ? route.getSensitiveHeaders() : null, 
                route.isStripPrefix());
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
//        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
//        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
//        //获取所有服务
//        List<String> services = discoveryClient.getServices();
//        int id = 0;
//        for (int i = 0; i < services.size(); i++) {
//            String s = services.get(i);
//            List<ServiceInstance> instances = discoveryClient.getInstances(s);
//            for (int j = 0; j < instances.size(); j++) {
//                ServiceInstance serviceInstance = instances.get(j);
//                ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
//                zuulRoute.setId(id + "");
//                zuulRoute.setServiceId(serviceInstance.getServiceId());
//                zuulRoute.setUrl(serviceInstance.getUri().toString());
//                zuulRoute.setRetryable(false);
//                String path = "/" + s + "/**";
//                zuulRoute.setPath(path);
//                values.put(path, zuulRoute);
//                id++;
//            }
//        }
//        
//        return values;
        return super.locateRoutes();
    }
}

package com.example.demo.admin.global.config;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.VersionStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomVersionStrategy implements VersionStrategy {

    private static final String VERSION;

    static {
        VERSION = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    @Override
    public String getResourceVersion(Resource resource) {
        return VERSION;
    }

    @Override
    public String extractVersion(String requestPath) {
        return null;
    }

    @Override
    public String removeVersion(String requestPath, String version) {
        return requestPath;
    }

    @Override
    public String addVersion(String requestPath, String version) {
        return requestPath + "?v=" + version;
    }

}

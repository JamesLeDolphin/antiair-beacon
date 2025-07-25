package com.jdolphin.aabeacon.common.platform.services;

import java.util.List;

public interface IPlatformHelper {

    boolean isDevelopmentEnvironment();

    List<? extends String> allowedTargets();
}
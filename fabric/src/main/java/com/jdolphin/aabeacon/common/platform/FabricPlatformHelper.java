package com.jdolphin.aabeacon.common.platform;

import com.jdolphin.aabeacon.common.AABCommonConfig;
import com.jdolphin.aabeacon.common.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {

    public List<? extends String> allowedTargets() {
        return AABCommonConfig.getAllowedTargets();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}

package com.jdolphin.aabeacon.common.platform;

import com.jdolphin.aabeacon.common.AABCommonConfig;
import com.jdolphin.aabeacon.common.platform.services.IPlatformHelper;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.List;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public List<? extends String> allowedTargets() {
        return AABCommonConfig.getAllowedTargets();
    }
}
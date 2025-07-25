package com.jdolphin.aabeacon.common;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class AABCommonConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> allowed_targets;

    public static List<? extends String> getAllowedTargets() {
        return allowed_targets.get();
    }

    static {
        BUILDER.push("Anti-Air Beacon mod config");

        allowed_targets = BUILDER.comment("List of allowed beacon targets, everything else is excluded",
                "Separate every entry except the last one with commas").defineList("allowed_targets", AABHelper.defaultWhitelist(), String.class::isInstance);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

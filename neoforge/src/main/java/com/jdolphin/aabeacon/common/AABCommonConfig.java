package com.jdolphin.aabeacon.common;


import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class AABCommonConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> allowed_targets;

    public static List<? extends String> getAllowedTargets() {
        return allowed_targets.get();
    }

    static {
        BUILDER.push("Anti-Air Beacon mod config");

        allowed_targets = BUILDER.comment("List of allowed beacon targets, everything else is excluded",
                "Separate every entry except the last one with commas").defineList("allowed_targets", AABHelper.defaultWhitelist(), () -> "", String.class::isInstance);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

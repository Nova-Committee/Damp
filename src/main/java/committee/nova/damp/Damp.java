package committee.nova.damp;

import committee.nova.damp.config.Config;
import net.fabricmc.api.ModInitializer;

public class Damp implements ModInitializer {
    private static Config config;

    private static boolean creeperDampness;
    private static int creeperMaxFuse;
    private static boolean tntDampness;
    private static int tntMaxFuse;

    @Override
    public void onInitialize() {
        config = Config.of("damp-config").provider(path -> """
                #Damp Configs
                creeperDampness=true
                tntDampness=true
                creeperFuse=2000
                tntFuse=2000
                """).request();
        creeperDampness = config.getOrDefault("creeperDampness", true);
        tntDampness = config.getOrDefault("tntDampness", true);
        creeperMaxFuse = config.getOrDefault("creeperFuse", 2000);
        tntMaxFuse = config.getOrDefault("tntFuse", 2000);
    }

    public static int getCreeperMaxFuse() {
        return creeperMaxFuse;
    }

    public static int getTntMaxFuse() {
        return tntMaxFuse;
    }

    public static boolean shouldCreeperDamp() {
        return creeperDampness;
    }

    public static boolean shouldTntDamp() {
        return tntDampness;
    }
}

package committee.nova.damp;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Damp.MODID)
public class Damp {
    public static final String MODID = "damp";
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.IntValue creeperMaxSwell;
    public static final ForgeConfigSpec.IntValue tntMaxFuse;

    static {
        final var builder = new ForgeConfigSpec.Builder();
        builder.comment("QuickPlant Configuration");
        creeperMaxSwell = builder.comment("Damp Configuration", "What's the max fuse time when the creeper reaches his max dampness")
                .defineInRange("creeper_swell", 2000, 100, Integer.MAX_VALUE);
        tntMaxFuse = builder.comment("What's the max fuse time when the primed tnt reaches his max dampness")
                .defineInRange("tnt_fuse", 2000, 100, Integer.MAX_VALUE);
        COMMON_CONFIG = builder.build();
    }

    public Damp() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
    }
}

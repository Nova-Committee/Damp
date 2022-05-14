package committee.nova.damp;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Damp.MODID)
public class Damp {
    public static final String MODID = "damp";

    public Damp() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}

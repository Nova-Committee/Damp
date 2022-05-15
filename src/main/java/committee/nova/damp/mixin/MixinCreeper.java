package committee.nova.damp.mixin;

import committee.nova.damp.Damp;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class MixinCreeper extends LivingEntity {
    @Shadow
    private int maxSwell;

    @Shadow
    private int swell;

    protected MixinCreeper(EntityType<? extends LivingEntity> e, Level w) {
        super(e, w);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (!this.isAlive()) return;
        if (getPersistentData().getBoolean("no_dampness")) return;
        if (!isInWaterOrRain()) {
            if (maxSwell <= 30 || (random.nextInt(2) & 1) == 0) return;
            if (maxSwell - swell > 30) maxSwell--;
        } else {
            if (maxSwell < Damp.creeperMaxSwell.get()) maxSwell++;
            if (swell % 120 == 119) swell = 0;
        }
    }
}

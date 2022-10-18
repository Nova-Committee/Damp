package committee.nova.damp.mixin;

import committee.nova.damp.Damp;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class MixinCreeperEntity extends LivingEntity {

    @Shadow
    private int fuseTime;

    @Shadow
    private int currentFuseTime;

    protected MixinCreeperEntity(EntityType<? extends LivingEntity> e, World w) {
        super(e, w);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (!Damp.shouldCreeperDamp()) return;
        if (!this.isAlive()) return;
        if (writeNbt(new NbtCompound()).getBoolean("no_dampness")) return;
        if (!isTouchingWaterOrRain()) {
            if (fuseTime <= 30 || (random.nextInt(2) & 1) == 0) return;
            if (fuseTime - currentFuseTime > 30) fuseTime--;
            return;
        }
        if (fuseTime < Damp.getCreeperMaxFuse()) fuseTime++;
        if (currentFuseTime % 120 == 119) currentFuseTime = 0;
    }
}

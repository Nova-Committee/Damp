package committee.nova.damp.mixin;

import committee.nova.damp.Damp;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntEntity.class)
public abstract class MixinTntEntity extends Entity {
    @Shadow
    public abstract int getFuse();

    @Shadow
    public abstract void setFuse(int i);

    public MixinTntEntity(EntityType<?> e, World w) {
        super(e, w);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (!Damp.shouldTntDamp()) return;
        if (writeNbt(new NbtCompound()).getBoolean("no_dampness")) return;
        final int fuse = getFuse();
        if (!isTouchingWaterOrRain()) {
            if (fuse <= 80 || (random.nextInt(2) & 1) == 0) return;
            setFuse(fuse - 1);
            return;
        }
        if (fuse < Damp.getTntMaxFuse()) setFuse(fuse + 1);
    }
}

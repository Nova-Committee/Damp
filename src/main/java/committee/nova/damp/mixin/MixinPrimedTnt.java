package committee.nova.damp.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PrimedTnt.class)
public abstract class MixinPrimedTnt extends Entity {
    @Shadow
    public abstract int getFuse();

    @Shadow
    public abstract void setFuse(int p_32086_);

    public MixinPrimedTnt(EntityType<?> e, Level w) {
        super(e, w);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        final int fuse = getFuse();
        if (!level.isRainingAt(new BlockPos(getEyePosition().add(0, 1, 0)))) {
            if (fuse <= 80 || (random.nextInt(2) & 1) == 0) return;
            setFuse(fuse - 1);
        } else {
            if (fuse < 2000) setFuse(fuse + 1);
        }
    }
}
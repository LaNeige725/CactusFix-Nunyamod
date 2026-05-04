package cactusfix.nunyamod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class CactusHitboxMixin {

    /**
     * @author CactusFix
     * @reason Overriding the cactus shape to be a full block (16x16x16)
     */
    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void forceFullCube(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        // Shapes.block() returns the standard 1x1x1 cube (equivalent to a full block)
        cir.setReturnValue(Shapes.block());
    }
}
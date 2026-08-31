package com.extendedae_plus.mixin.ae2;

import appeng.blockentity.grid.AENetworkBlockEntity;
import com.extendedae_plus.content.matrix.supermatrix.SuperAssemblerMatrixPart;
import com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixGlass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 矩阵玻璃在卸载/移除前先解除超级装配矩阵集群。
 *
 * <p>逻辑本属于 {@code TileAssemblerMatrixGlassMixin}，但 onChunkUnloaded / setRemoved 由
 * {@link AENetworkBlockEntity} 声明、玻璃自身并未覆写，只能在声明方注入，再按类型筛选回玻璃。
 */
@Mixin(value = AENetworkBlockEntity.class, remap = false)
public abstract class SuperMatrixGlassLifecycleMixin {

    // onChunkUnloaded 由 Forge 添加、无需重映射；setRemoved 是原版方法，须单独开启重映射（生产环境为 m_7651_）。
    @Inject(method = "onChunkUnloaded", at = @At("HEAD"))
    private void eap$detachSuperMatrixBeforeChunkUnload(CallbackInfo ci) {
        // AE2 销毁网络节点前先解除集群，避免集群保留失效玻璃。
        eap$detachSuperMatrixCluster();
    }

    @Inject(method = "setRemoved", at = @At("HEAD"), remap = true)
    private void eap$detachSuperMatrixBeforeRemoval(CallbackInfo ci) {
        eap$detachSuperMatrixCluster();
    }

    private void eap$detachSuperMatrixCluster() {
        Object self = this;
        if (self instanceof TileAssemblerMatrixGlass && self instanceof SuperAssemblerMatrixPart part) {
            part.eap$destroySuperMatrixClusterQuietly();
        }
    }
}

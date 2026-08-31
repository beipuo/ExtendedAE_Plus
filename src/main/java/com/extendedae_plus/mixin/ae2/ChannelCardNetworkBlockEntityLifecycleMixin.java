package com.extendedae_plus.mixin.ae2;

import com.extendedae_plus.util.wireless.ChannelCardConnectionController;
import appeng.blockentity.grid.AENetworkBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** 网络方块实体卸载时统一释放频道卡无线连接。 */
@Mixin(value = AENetworkBlockEntity.class, remap = false)
public abstract class ChannelCardNetworkBlockEntityLifecycleMixin {
    // onChunkUnloaded 由 Forge 添加、无需重映射；setRemoved 是原版方法，须单独开启重映射（生产环境为 m_7651_）。
    @Inject(method = "onChunkUnloaded", at = @At("HEAD"))
    private void eap$unloadChannelControllers(CallbackInfo ci) {
        ChannelCardConnectionController.unloadFor((net.minecraft.world.level.block.entity.BlockEntity) (Object) this);
    }

    @Inject(method = "setRemoved", at = @At("HEAD"), remap = true)
    private void eap$removeChannelControllers(CallbackInfo ci) {
        ChannelCardConnectionController.unloadFor((net.minecraft.world.level.block.entity.BlockEntity) (Object) this);
    }
}

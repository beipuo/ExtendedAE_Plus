package com.extendedae_plus.mixin.gtlcore;

import appeng.api.crafting.IPatternDetails;
import appeng.crafting.execution.CraftingCpuLogic;
import com.extendedae_plus.api.crafting.ScaledMolecularAssemblerPattern;
import com.extendedae_plus.util.crafting.StrictMolecularAssemblerPattern;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * {@link com.extendedae_plus.mixin.ae2.autopattern.CraftingCpuLogicPatternPowerMixin} 的 GTLCore 版本。
 *
 * <p>GTLCore 以 priority 1100 {@code @Overwrite} 了 {@code CraftingCpuLogic#executeCrafting}（其"ME样板总成自动翻倍"实现），
 * 原版那份 priority 1000 的 mixin 无法注入被合并的方法，失败时还会留下描述符被裁剪的处理器方法而导致
 * {@code VerifyError}，因此它在 GTLCore 存在时被 {@link com.extendedae_plus.mixin.MixinConditions} 裁掉，
 * 由这份 priority 1200 的实现接手——GTLCore 重写后的方法体同样调用
 * {@code CraftingCpuHelper.calculatePatternPower} 并持有 {@code IPatternDetails} 局部变量。
 *
 * <p>注意：这份 mixin 绑定 GTLCore 的实现细节。若 GTLCore 改掉该调用或局部变量，注入将失败并再次触发
 * {@code VerifyError}；届时把它从 mixin 配置中移除即可（代价是丢失超级矩阵按单次合成扣电量的修复）。
 */
@Mixin(value = CraftingCpuLogic.class, priority = 1200, remap = false)
public abstract class GtlCraftingCpuLogicPatternPowerMixin {

    @ModifyExpressionValue(method = "executeCrafting",
            at = @At(value = "INVOKE",
                    target = "Lappeng/crafting/execution/CraftingCpuHelper;calculatePatternPower([Lappeng/api/stacks/KeyCounter;)D"))
    private static double eap$useSingleCraftPowerGtl(double original, @Local IPatternDetails details) {
        if (details instanceof ScaledMolecularAssemblerPattern scaled
                && scaled.getOriginal() instanceof StrictMolecularAssemblerPattern
                && scaled.getMultiplier() > 1) {
            // craftingContainer 仍是整批输入，只将能量表达式还原为一次合成的成本。
            return original / scaled.getMultiplier();
        }
        return original;
    }
}

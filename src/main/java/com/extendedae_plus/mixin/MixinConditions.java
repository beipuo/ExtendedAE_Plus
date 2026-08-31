package com.extendedae_plus.mixin;

import com.extendedae_plus.util.ModCheckUtils;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * Mixin条件加载插件
 * 用于根据模组存在情况动态加载不同的Mixin
 */
public class MixinConditions implements IMixinConfigPlugin {
    
    @Override
    public void onLoad(String mixinPackage) {
        // 初始化时调用
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        try {
            // === MAE2 兼容 ===
            if (mixinClassName.contains("CraftingCPUClusterMixin")) {
                return !ModCheckUtils.isLoaded(ModCheckUtils.MODID_MAE2);
            }

            // === GTLCore 兼容 ===
            // mixin.gtlcore 包只在 GTLCore 存在时应用（专为其 forked/重写过的 AE2 行为适配）。
            if (mixinClassName.startsWith("com.extendedae_plus.mixin.gtlcore.")) {
                return ModCheckUtils.isLoaded(ModCheckUtils.MODID_GTLCORE);
            }

            // GTLCore 以 priority 1100 @Overwrite 了 CraftingCpuLogic.executeCrafting（其"ME样板总成自动翻倍"实现）。
            // 向被合并（merged）的方法注入会在 prepare 阶段失败，而 MixinExtras 已把 @Local 参数从处理器描述符中裁掉，
            // 于是类里留下一个读取不存在局部变量槽的方法，加载 CraftingCpuLogic 时抛 VerifyError: Bad local variable type。
            // GTLCore 存在时改由 mixin.gtlcore.GtlCraftingCpuLogicPatternPowerMixin（priority 1200）接手。
            if (mixinClassName.endsWith("ae2.autopattern.CraftingCpuLogicPatternPowerMixin")) {
                return !ModCheckUtils.isLoaded(ModCheckUtils.MODID_GTLCORE);
            }

            // === EMI 兼容 ===
            // 未安装 EMI 时禁用 mixin.emi 包，避免解析不存在的 dev.emi.* 目标类。
            if (mixinClassName.startsWith("com.extendedae_plus.mixin.emi.")) {
                return ModCheckUtils.isLoaded(ModCheckUtils.MODID_EMI);
            }

            // === AAE 兼容 ===
            if (mixinClassName.startsWith("com.extendedae_plus.mixin.advancedae")) {
                return ModCheckUtils.isLoaded(ModCheckUtils.MODID_AAE);
            }

            // WTLib 兼容目标类只在 WTLib 存在时应用，避免伪目标解析触发硬依赖。
            if (mixinClassName.startsWith("com.extendedae_plus.mixin.ae2WTlib")) {
                return ModCheckUtils.isLoaded(ModCheckUtils.MODID_AE2WTLIB);
            }

            // === AppFlux 兼容 ===
            if (mixinClassName.startsWith("com.extendedae_plus.mixin.appflux")) {
                return ModCheckUtils.isLoaded(ModCheckUtils.MODID_APPFLUX);
            }

            // === JEI 兼容 ===
            if (mixinClassName.startsWith("com.extendedae_plus.mixin.jei.")) {
                return ModCheckUtils.isLoaded("jei");
            }

            // === GuideME 版本兼容 ===
            if (mixinClassName.startsWith("com.extendedae_plus.mixin.guideme.")) {
                return ModCheckUtils.isLoadedAndLowerThan(ModCheckUtils.MODID_GUIDEME, "20.1.14");
            }

            return true;
        } catch (Exception e) {
            System.err.println("[ExtendedAE_Plus] 检查 Mixin 条件时出错: " + e.getMessage());
            return true; // 出错默认加载，避免意外禁用
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        // 接受目标类
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // 应用前调用
    }

    @Override
    public void postApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // 应用后调用
    }
}

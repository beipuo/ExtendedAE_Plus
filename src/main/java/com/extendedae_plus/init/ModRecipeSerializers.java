package com.extendedae_plus.init;

import com.extendedae_plus.ExtendedAEPlus;
import com.extendedae_plus.recipe.SuperCircuitCutterRecipe;
import com.extendedae_plus.recipe.SuperCircuitCutterRecipeSerializer;
import com.extendedae_plus.recipe.SuperCrystalAssemblerRecipe;
import com.extendedae_plus.recipe.SuperCrystalAssemblerRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ExtendedAEPlus.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SuperCrystalAssemblerRecipe>> SUPER_CRYSTAL_ASSEMBLER =
            SERIALIZERS.register("crystal_assembler_plus", () -> SuperCrystalAssemblerRecipeSerializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SuperCircuitCutterRecipe>> SUPER_CIRCUIT_CUTTER =
            SERIALIZERS.register("circuit_cutter_plus", () -> SuperCircuitCutterRecipeSerializer.INSTANCE);

    private ModRecipeSerializers() {
    }
}

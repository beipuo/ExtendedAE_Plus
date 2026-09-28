package com.extendedae_plus.client;

import com.extendedae_plus.ExtendedAEPlus;
import com.extendedae_plus.client.model.MatrixFrameModel;
import com.extendedae_plus.client.render.crafting.EPlusCraftingCubeModelProvider;
import com.extendedae_plus.content.crafting.EPlusCraftingUnitType;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;

@EventBusSubscriber(modid = ExtendedAEPlus.MODID, value = Dist.CLIENT)
public final class ClientModelEvents {
    private ClientModelEvents() {
    }

    @SubscribeEvent
    public static void registerBlockStateModels(RegisterBlockStateModels event) {
        registerCraftingAccelerator(event, "4x", EPlusCraftingUnitType.ACCELERATOR_4x);
        registerCraftingAccelerator(event, "16x", EPlusCraftingUnitType.ACCELERATOR_16x);
        registerCraftingAccelerator(event, "64x", EPlusCraftingUnitType.ACCELERATOR_64x);
        registerCraftingAccelerator(event, "256x", EPlusCraftingUnitType.ACCELERATOR_256x);
        registerCraftingAccelerator(event, "1024x", EPlusCraftingUnitType.ACCELERATOR_1024x);

        event.registerModel(Identifier.fromNamespaceAndPath(ExtendedAEPlus.MODID, "matrix_frame"),
                MatrixFrameModel.MAP_CODEC);
    }

    private static void registerCraftingAccelerator(RegisterBlockStateModels event, String name,
            EPlusCraftingUnitType type) {
        event.registerModel(ExtendedAEPlus.id("block/crafting/" + name + "_accelerator_formed_v2"),
                com.mojang.serialization.MapCodec.unit(() -> new EPlusCraftingCubeModelProvider(type)));
    }
}

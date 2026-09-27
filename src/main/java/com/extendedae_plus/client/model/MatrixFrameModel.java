package com.extendedae_plus.client.model;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import org.jetbrains.annotations.NotNull;

public final class MatrixFrameModel implements CustomUnbakedBlockStateModel {
    public static final MapCodec<MatrixFrameModel> MAP_CODEC = MapCodec.unit(MatrixFrameModel::new);

    @Override
    public @NotNull BlockStateModel bake(@NotNull ModelBaker baker) {
        return new MatrixFrameBakedModel();
    }

    @Override
    public void resolveDependencies(@NotNull Resolver resolver) {
    }

    @Override
    public @NotNull MapCodec<MatrixFrameModel> codec() {
        return MAP_CODEC;
    }

    public static final class Loader {
        public MatrixFrameModel read(Object ignored) {
            return new MatrixFrameModel();
        }
    }
}

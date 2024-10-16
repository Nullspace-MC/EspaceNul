package net.nullspacemc.espacenul.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.MovingBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.InputClientPlayerEntity;
import net.minecraft.client.render.HeldItemRenderer;
import net.minecraft.client.render.texture.Sprite;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.world.World;
import net.nullspacemc.espacenul.util.FeatureCreativeNoClipUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
abstract class FeatureCreativeNoClipMixin {
	@Mixin(PlayerEntity.class)
	abstract static class PlayerMixin extends Entity {

		@Shadow
		public PlayerAbilities abilities;

		public PlayerMixin(World world) {
			super(world);
		}

		@Inject(
			method = "tick",
			at = @At("HEAD")
		)
		private void setNoClipFlag(CallbackInfo ci) {
			this.noClip = FeatureCreativeNoClipUtils.isNoClip(this.abilities);
			this.blocksBuilding = !this.noClip;
		}
	}

	@Mixin(InputClientPlayerEntity.class)
	abstract static class InputClientPlayerEntityMixin {
		@Shadow
		protected Minecraft minecraft;

		@ModifyReturnValue(
			method = "isBlockAtPosFullCube",
			at = @At("RETURN")
		)
		private boolean preventFullCubeChecksForCollisions(boolean original) {
			return !FeatureCreativeNoClipUtils.isNoClip(this.minecraft.player.abilities) && original;
		}
	}

	@Mixin(RenderChunk.class)
	abstract static class RenderChunkMixin {
		@ModifyExpressionValue(
			method = "compile",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getRenderType()I")
		)
		private int changeRenderTypeToNone(int original) {
			return -1;
		}
	}

	// ?????????????????????????????????????????????????
	@Mixin(HeldItemRenderer.class)
	abstract static class HeldItemRendererMixin {
		@Inject(
			method = "renderSuffocatingEffect",
			at = @At("HEAD"),
			cancellable = true
		)
		private void removeSuffocationEffect(float sprite, Sprite par2, CallbackInfo ci) {
			ci.cancel();
		}
	}

	@Mixin(MovingBlockEntity.class)
	abstract static class MovingBlockEntityMixin {
		@ModifyExpressionValue(
			method = "moveEntities",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;")
		)
		private List<Entity> removeNoClipPlayerFromBeingPushedByPiston(List<Entity> original) {
			original.removeIf(e -> e.noClip);
			return original;
		}
	}
}

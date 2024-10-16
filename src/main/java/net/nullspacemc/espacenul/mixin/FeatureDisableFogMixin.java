package net.nullspacemc.espacenul.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import net.nullspacemc.espacenul.config.Config;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
public class FeatureDisableFogMixin {
	@Mixin(GameRenderer.class)
	abstract static class GameRendererMixin {
		@Inject(
			method = "renderFog",
			at = @At("TAIL"),
			cancellable = true
		)
		private void removeFog2(int tickDelta, float par2, CallbackInfo ci) {
			// bit hacky but whatever, it works
			if (Config.disableFog) {
				GL11.glFogi(GL11.GL_FOG_START, 0);
				GL11.glFogi(GL11.GL_FOG_END, Integer.MAX_VALUE);
				ci.cancel();
			}
		}
	}
}

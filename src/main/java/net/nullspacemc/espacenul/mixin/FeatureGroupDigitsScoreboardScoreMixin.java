package net.nullspacemc.espacenul.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GameGui;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.nullspacemc.espacenul.util.StringFormattingUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Environment(EnvType.CLIENT)
public class FeatureGroupDigitsScoreboardScoreMixin {
	@Mixin(GameGui.class)
	abstract static class GameGuiMixin {
		@Redirect(
			method = "renderSidebarObjective",
			at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(I)Ljava/lang/StringBuilder;")
		)
		private StringBuilder scoreboardStringGroupDigitsOnSidebar(StringBuilder instance, int i) {
			return instance.append(StringFormattingUtils.formatGroupedDigits(i));
		}

		@Redirect(
			method = "render",
			at = @At(
				value = "INVOKE",
				target = "Ljava/lang/StringBuilder;append(I)Ljava/lang/StringBuilder;"
			),
			slice = @Slice(
				from = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/scoreboard/Scoreboard;getScore(Ljava/lang/String;Lnet/minecraft/scoreboard/ScoreboardObjective;)Lnet/minecraft/scoreboard/ScoreboardScore;"
				)
			)
		)
		private StringBuilder scoreboardStringGroupDigitsOnPlayerList(StringBuilder instance, int i) {
			return instance.append(StringFormattingUtils.formatGroupedDigits(i));
		}
	}

	@Mixin(PlayerEntityRenderer.class)
	abstract static class PlayerEntityRendererMixin {
		@Redirect(
			method = "renderNameTag(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;DDDLjava/lang/String;FD)V",
			at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(I)Ljava/lang/StringBuilder;")
		)
		private StringBuilder scoreboardStringGroupDigitsBelowName(StringBuilder instance, int i) {
			return instance.append(StringFormattingUtils.formatGroupedDigits(i));
		}
	}
}

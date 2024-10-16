package net.nullspacemc.espacenul.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// TODO: format in playerlist slot and belowname

@Environment(EnvType.CLIENT)
public class FeatureGroupDigitsScoreboardScoreMixin {
	@Mixin(GameGui.class)
	abstract static class GameGuiMixin {
		@Redirect(
			method = "renderSidebarObjective",
			at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(I)Ljava/lang/StringBuilder;")
		)
		private StringBuilder scoreboardStringGroupDigits(StringBuilder instance, int i) {
			return instance.append(espacenul$formatGroupedDigits(i));
		}

		@Unique
		private String espacenul$formatGroupedDigits(int i) {
			return String.format("%,d", i);
		}
	}
}

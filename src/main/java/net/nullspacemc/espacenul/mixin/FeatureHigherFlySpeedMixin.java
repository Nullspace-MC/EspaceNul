package net.nullspacemc.espacenul.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerAbilities;
import net.nullspacemc.espacenul.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// TODO: lessen drag and heighten velocity speed

@Environment(EnvType.CLIENT)
public class FeatureHigherFlySpeedMixin {
	@Mixin(PlayerAbilities.class)
	abstract static class PlayerAbilitiesMixin {
		@ModifyReturnValue(
			method = "getFlySpeed", at = @At("RETURN"))
		private float setHigherFlySpeed(float original) {
			if (Config.higherFlySpeed) return original * 10;
			return original;
		}
	}
}

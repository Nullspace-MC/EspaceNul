package net.nullspacemc.espacenul.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardScore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;
import java.util.stream.Collectors;

// TODO: add filtered out players' score as added sum

@Environment(EnvType.CLIENT)
public class FeatureFixScoreboardNoRenderMixin {		
	@Mixin(Scoreboard.class)
	abstract static class ScoreboardMixin {
		@ModifyReturnValue(
			method = "getScores(Lnet/minecraft/scoreboard/ScoreboardObjective;)Ljava/util/Collection;",
			at = @At("RETURN")
		)
		private Collection<ScoreboardScore> limitTo15Scores(Collection<ScoreboardScore> original) {
			return original
				.stream()
				.sorted(ScoreboardScore.COMPARATOR.reversed())
				.limit(15)
				.sorted(ScoreboardScore.COMPARATOR)
				.collect(Collectors.toList());
		}
	}
}

package net.nullspacemc.espacenul.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GameGui;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardScore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

	@Mixin(GameGui.class)
	abstract static class GameGuiMixin {
		@Inject(
			method = "renderSidebarObjective",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;getWidth(Ljava/lang/String;)I", ordinal = 0)
		)
		private void addTotalAndRemainingScores(ScoreboardObjective width, int height, int text, TextRenderer par4, CallbackInfo ci, @Local Scoreboard scoreboard, @Local Collection<ScoreboardScore> scoreboardScores) {
			List<ScoreboardScore> scores = (List<ScoreboardScore>) scoreboardScores;

			ScoreboardScore remainingScore = new ScoreboardScore(scoreboard, scoreboard.getDisplayObjective(1), "Remaining");
			remainingScore.set(
				scoreboard
					.getScores()
					.stream()
					.sorted(ScoreboardScore.COMPARATOR.reversed())
					.mapToInt(ScoreboardScore::get)
					.skip(15)
					.sum()
			);
			if (remainingScore.get() != 0) scores.add(0, remainingScore);

			ScoreboardScore totalScore = new ScoreboardScore(scoreboard, scoreboard.getDisplayObjective(1), "Total");
			totalScore.set(
				scoreboard
					.getScores()
					.stream()
					.mapToInt(ScoreboardScore::get)
					.sum()
			);
			scores.add(totalScore);
		}
	}
}

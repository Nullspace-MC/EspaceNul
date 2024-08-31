package net.nullspacemc.espacenul.util;

import net.minecraft.entity.player.PlayerAbilities;
import net.nullspacemc.espacenul.config.Config;

public class FeatureCreativeNoClipUtils {
	public static boolean isNoClip(PlayerAbilities abilities) {
		return Config.creativeNoClip && abilities.creativeMode && abilities.flying;
	}
}

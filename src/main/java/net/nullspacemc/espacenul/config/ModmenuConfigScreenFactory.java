package net.nullspacemc.espacenul.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import net.nullspacemc.espacenul.config.gui.ConfigScreen;

public class ModmenuConfigScreenFactory implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (ConfigScreenFactory<Screen>) parent -> new ConfigScreen(parent, Config.getConfigEntryList());
	}
}

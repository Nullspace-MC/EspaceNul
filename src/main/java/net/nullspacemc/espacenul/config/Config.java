package net.nullspacemc.espacenul.config;

import net.minecraft.client.gui.widget.EntryListWidget;
import net.nullspacemc.espacenul.config.gui.BooleanEntry;

import java.util.Arrays;
import java.util.List;

public class Config {
	public static boolean creativeNoClip = false;
	public static boolean disableFog = false;
	public static boolean higherFlySpeed = false;

	public static List<EntryListWidget.Entry> getConfigEntryList() {
		return Arrays.asList(
			new BooleanEntry("net.nullspacemc.espacenul.creativenoclip", () -> creativeNoClip, e -> creativeNoClip = e),
			new BooleanEntry("net.nullspacemc.espacenul.disablefog", () -> disableFog, e -> disableFog = e),
			new BooleanEntry("net.nullspacemc.espacenul.higherflyspeed", () -> higherFlySpeed, e -> higherFlySpeed = e)
		);
	}
}

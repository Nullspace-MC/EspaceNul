package net.nullspacemc.espacenul.config.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.EntryListWidget;
import java.util.List;

public class ConfigListWidget extends EntryListWidget {
	private final List<Entry> configList;

	public ConfigListWidget(Minecraft minecraft, int width, int height, int minY, int maxY, int itemHeight, List<Entry> entryList) {
		super(minecraft, width, height, minY, maxY, itemHeight);
		this.configList = entryList;
	}

	@Override
	public int getRowWidth() {
		return this.width;
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		super.render(mouseX, mouseY, tickDelta);
	}

	@Override
	public Entry getEntry(int index) {
		return configList.get(index);
	}

	@Override
	protected int size() {
		return configList.size();
	}
}

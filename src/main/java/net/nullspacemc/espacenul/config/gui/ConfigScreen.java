package net.nullspacemc.espacenul.config.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.resource.language.I18n;

import java.util.List;

public class ConfigScreen extends Screen {
	private final ConfigListWidget configListWidget;
	private final Screen parent;
	private ButtonWidget closeButton;

	public ConfigScreen(Screen parent, List<EntryListWidget.Entry> entries) {
		this.parent = parent;
		this.minecraft = Minecraft.getInstance();
		configListWidget = new ConfigListWidget(this.minecraft, this.minecraft.width, this.minecraft.height, 20, 200, 20, entries);
	}

	@Override
	public void init() {
		super.init();
		this.closeButton = new ButtonWidget(0, 0, 220, this.width / 2, 20, I18n.translate("net.nullspacemc.espacenul.close"));
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		this.renderBackground();
		this.configListWidget.render(mouseX, mouseY, tickDelta);
		this.closeButton.x = this.width / 4;
		this.closeButton.render(this.minecraft, mouseX, mouseY);
		this.drawCenteredString(this.minecraft.textRenderer, I18n.translate("net.nullspacemc.espacenul.menutitle"), this.width / 2, 5, 16777215);
		super.render(mouseX, mouseY, tickDelta);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		configListWidget.mouseClicked(mouseX, mouseY, mouseButton);
		if (closeButton.isMouseOver(this.minecraft, mouseX, mouseY)) {
			this.minecraft.openScreen(this.parent);
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}

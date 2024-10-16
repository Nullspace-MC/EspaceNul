package net.nullspacemc.espacenul.config.gui;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.resource.language.I18n;
import net.nullspacemc.espacenul.EspaceNul;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanEntry implements EntryListWidget.Entry {
	private final Supplier<Boolean> supplier;
	private final Consumer<Boolean> consumer;
	private final String key;

	private final ButtonWidget button;

		public BooleanEntry(String key, Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
		this.supplier = supplier;
		this.consumer = consumer;
		this.key = key;

		int buttonWidth = 110;

		this.button = new ButtonWidget(0, 300, 20, buttonWidth, 20, String.valueOf(supplier.get()));
	}

	@Override
	public void render(int index, int x, int y, int width, int height, BufferBuilder bufferBuilder, int mouseX, int mouseY, boolean hovered) {
		this.button.y = y;
		this.button.render(Minecraft.getInstance(), mouseX, mouseY);
		Minecraft.getInstance().textRenderer.drawWithShadow(I18n.translate(this.key), 20, y + 5, 16777215);
	}

	@Override
	public boolean mouseClicked(int index, int mouseX, int mouseY, int button, int entryMouseX, int entryMouseY) {
		if (this.button.isMouseOver(Minecraft.getInstance(), mouseX, mouseY)) {
			this.consumer.accept(!this.supplier.get());
			this.button.message = String.valueOf(this.supplier.get());
			return true;
		}
		return false;
	}

	@Override
	public void mouseReleased(int index, int mouseX, int mouseY, int button, int entryMouseX, int entryMouseY) {
		this.button.mouseReleased(entryMouseX, entryMouseY);
	}
}

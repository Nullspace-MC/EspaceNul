package net.nullspacemc.espacenul.mixin;

import com.google.common.base.Splitter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
public class FeatureLongerChatMessageMixin {
	@Mixin(ChatScreen.class)
	abstract static class ChatScreenMixin {
		@ModifyArg(
			method = "init",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setMaxLength(I)V")
		)
		private int longerChatMessageMaxLength(int maximumLength) {
			// 1600 characters should be way more than enough for anything
			return maximumLength * 16;
		}

		@Redirect(
			method = "sendMessage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/LocalClientPlayerEntity;sendChat(Ljava/lang/String;)V")
		)
		private void sendSplitMessageForCompatibilityWithVanillaServer(LocalClientPlayerEntity player, String msg) {
			Splitter.fixedLength(100).split(msg).forEach(player::sendChat);
		}
	}
}

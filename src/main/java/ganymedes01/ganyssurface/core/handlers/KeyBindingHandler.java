package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketPortWorkTable;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class KeyBindingHandler {

	public static KeyBinding worktable = new KeyBinding("Portable Worktable", Keyboard.KEY_P, Reference.MOD_NAME);

	public KeyBindingHandler() {
		ClientRegistry.registerKeyBinding(worktable);
	}

	@SubscribeEvent
	public void onKey(KeyInputEvent event) {
		if (worktable.isPressed()) {
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			if (player == null)
				return;
			for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack != null && stack.getItem() == ModItems.portalDualWorkTable)
					PacketHandler.sendToServer(new PacketPortWorkTable(i));
			}
		}
	}
}
package ganymedes01.ganyssurface.core.handlers;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketPortWorkTable;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class KeyBindingHandler {

	public static KeyBinding worktable;

	public KeyBindingHandler() {
		if (GanysSurface.enableWorkTables) {
			worktable = new KeyBinding(StatCollector.translateToLocal("item.ganyssurface.portableDualWorkTable.name"), Keyboard.KEY_P, Reference.MOD_NAME);
			ClientRegistry.registerKeyBinding(worktable);
		}
	}

	@SubscribeEvent
	public void onKey(KeyInputEvent event) {
		if (GanysSurface.enableWorkTables && worktable.isPressed()) {
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
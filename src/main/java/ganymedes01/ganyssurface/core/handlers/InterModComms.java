package ganymedes01.ganyssurface.core.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import ganymedes01.ganyssurface.lib.IMCKeys;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.recipes.OrganicMatterRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class InterModComms {

	public static void processIMC(IMCEvent event) {
		for (IMCMessage message : event.getMessages())
			if (message.key.equals(IMCKeys.REGISTER_ORGANIC_MATTER))
				registerOrganicMatter(message);
	}

	private static void registerOrganicMatter(IMCMessage message) {
		try {
			NBTTagCompound data = message.getNBTValue();

			int yield = data.getInteger("yield");
			ItemStack matter = ItemStack.loadItemStackFromNBT(data.getCompoundTag("matter"));

			if (matter != null && matter.getItem() != null)
				OrganicMatterRegistry.addMatterYield(matter, yield);
		} catch (Exception e) {
			Logger.getLogger(Reference.MOD_ID).log(Level.WARNING, String.format("%s failed to register a organic matter yield", message.getSender()));
		}
	}
}
package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * TConstuct integration for modified iron/trap doors
 * integration by ezterry, full unrestricted rights of this code document
 * are released to ganymedes01 for use in Gany's Surface and/or
 * other projects
 *
 */

// update TiC recipes to match new iron doors
public class TConstruct extends Integration {

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		NBTTagCompound data;
		int ingotLiquidValue = 144; // 1 ingot amount in mb

		ItemStack iron = new ItemStack(Blocks.iron_block);

		if (GanysSurface.enableDoors) {
			// reload the door melting recipe (will overwrite)
			ItemStack iron_door = new ItemStack(Items.iron_door);
			data = new NBTTagCompound();

			data.setString("FluidName", "iron.molten");
			data.setInteger("Amount", ingotLiquidValue * 2);
			data.setInteger("Temperature", 600);
			data.setTag("Item", iron_door.writeToNBT(new NBTTagCompound()));
			data.setTag("Block", iron.writeToNBT(new NBTTagCompound()));

			FMLInterModComms.sendMessage(getModID(), "addSmelteryMelting", data);
		}
		if (GanysSurface.enableIronTrapdoor) {
			// add a trapdoor smelting recipe
			ItemStack iron_trapdoor = new ItemStack(ModBlocks.ironTrapdoor);
			data = new NBTTagCompound();

			data.setString("FluidName", "iron.molten");
			data.setInteger("Amount", ingotLiquidValue * 4);
			data.setInteger("Temperature", 600);
			data.setTag("Item", iron_trapdoor.writeToNBT(new NBTTagCompound()));
			data.setTag("Block", iron.writeToNBT(new NBTTagCompound()));

			FMLInterModComms.sendMessage(getModID(), "addSmelteryMelting", data);
		}
	}

	@Override
	public String getModID() {
		return "TConstruct";
	}
}
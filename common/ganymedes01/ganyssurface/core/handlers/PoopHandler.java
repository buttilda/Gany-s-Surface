package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PoopHandler {

	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (!event.entityLiving.worldObj.isRemote)
			if (event.entityLiving instanceof EntityAnimal || event.entityLiving instanceof EntityTameable || event.entityLiving instanceof EntityBat)
				if (event.entityLiving.worldObj.rand.nextInt(GanysSurface.poopingChance) == 0)
					if (!event.entityLiving.isChild()) {
						if (event.entityLiving instanceof EntityBat)
							replaceNearbyAirBlock(event.entityLiving.worldObj, (int) event.entityLiving.posX, (int) event.entityLiving.posY, (int) event.entityLiving.posZ, ModBlocks.poop, 1);
						else
							replaceNearbyAirBlock(event.entityLiving.worldObj, (int) event.entityLiving.posX, (int) event.entityLiving.posY, (int) event.entityLiving.posZ, ModBlocks.poop, 0);
						event.entityLiving.playSound("mob.chicken.plop", 1.0F, (event.entityLiving.worldObj.rand.nextFloat() - event.entityLiving.worldObj.rand.nextFloat()) * 0.2F + 1.0F);
					}
	}

	private void replaceNearbyAirBlock(World world, int x, int y, int z, Block block, int meta) {
		for (int i = -1; i < 2; i++)
			for (int k = -1; k < 2; k++)
				if (world.isAirBlock(x + i, y, z + k) || Block.blocksList[world.getBlockId(x + i, y, z + k)].isBlockReplaceable(world, x + i, y, z + k)) {
					world.setBlock(x + i, y, z + k, block.blockID, meta, 3);
					ModBlocks.poop.onNeighborBlockChange(world, x + i, y, z + k, block.blockID);
					return;
				}
		Utils.dropStack(world, x, y, z, new ItemStack(ModItems.poop, 1, meta));
	}
}
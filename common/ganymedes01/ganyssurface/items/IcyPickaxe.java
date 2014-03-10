package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.ModMaterials;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class IcyPickaxe extends ItemPickaxe {

	public IcyPickaxe() {
		super(ModIDs.ICY_PICKAXE_ID, ModMaterials.ICE);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.ICY_PICKAXE_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ICY_PICKAXE_NAME));
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block == Block.ice;
	}

	@Override
	public boolean getIsRepairable(ItemStack tool, ItemStack material) {
		return material.itemID == Block.ice.blockID;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		if (player.worldObj.isRemote)
			return false;
		if (player.worldObj.getBlockId(x, y, z) == Block.ice.blockID) {
			player.worldObj.setBlockToAir(x, y, z);
			Utils.dropStack(player.worldObj, x, y, z, new ItemStack(Block.ice));
			return true;
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition hit = getMovingObjectPositionFromPlayer(world, player, true);
		if (hit == null)
			return stack;

		if (hit.typeOfHit == EnumMovingObjectType.TILE) {
			int x = hit.blockX;
			int y = hit.blockY;
			int z = hit.blockZ;
			int freezes = 0;

			if (!world.isRemote) {
				for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++)
						for (int k = -1; k <= 1; k++) {
							int blockID = world.getBlockId(x + i, y + j, z + k);
							if (blockID == Block.waterMoving.blockID || blockID == Block.waterStill.blockID) {
								if (world.getBlockMetadata(x + i, y + j, z + k) == 0) {
									world.playAuxSFXAtEntity(null, 2001, x + i, y + j, z + k, Block.ice.blockID + (0 << 12));
									world.setBlock(x + i, y + j, z + k, Block.ice.blockID);
									freezes++;
								}
							} else if (blockID == Block.lavaMoving.blockID || blockID == Block.lavaStill.blockID)
								if (world.getBlockMetadata(x + i, y + j, z + k) == 0) {
									world.playAuxSFXAtEntity(null, 2001, x + i, y + j, z + k, Block.obsidian.blockID + (0 << 12));
									world.setBlock(x + i, y + j, z + k, Block.obsidian.blockID);
									freezes++;
								} else {
									world.playAuxSFXAtEntity(null, 2001, x + i, y + j, z + k, Block.cobblestone.blockID + (0 << 12));
									world.setBlock(x + i, y + j, z + k, Block.cobblestone.blockID);
									freezes++;
								}
						}
				if (freezes > 0)
					stack.damageItem(freezes, player);
			} else
				player.swingItem();
		}
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if (stack.isItemDamaged()) {
			int x = (int) player.posX;
			int z = (int) player.posZ;

			if (world.rand.nextInt(80) == 0 && world.getBiomeGenForCoords(x, z).temperature < 0.2F)
				stack.setItemDamage(stack.getItemDamage() - 1);
		}
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int meta) {
		if (canHarvestBlock(block))
			return efficiencyOnProperMaterial;
		return 0.0F;
	}
}
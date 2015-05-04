package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModMaterials;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class IcyPickaxe extends ItemPickaxe implements IConfigurable {

	public IcyPickaxe() {
		super(ModMaterials.ICE);
		setHarvestLevel("pickaxe", 2);
		setTextureName(Utils.getItemTexture(Strings.ICY_PICKAXE_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ICY_PICKAXE_NAME));
		setCreativeTab(GanysSurface.enableIcyPick ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean func_150897_b(Block block) {
		return block == Blocks.ice;
	}

	@Override
	public boolean getIsRepairable(ItemStack tool, ItemStack material) {
		return material.getItem() == Item.getItemFromBlock(Blocks.ice);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		if (player.worldObj.isRemote)
			return false;
		if (!player.capabilities.isCreativeMode)
			if (player.worldObj.getBlock(x, y, z) == Blocks.ice) {
				player.worldObj.setBlockToAir(x, y, z);
				InventoryUtils.dropStack(player.worldObj, x, y, z, new ItemStack(Blocks.ice));
				return true;
			}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition hit = getMovingObjectPositionFromPlayer(world, player, true);
		if (hit == null)
			return stack;

		if (hit.typeOfHit == MovingObjectType.BLOCK) {
			int x = hit.blockX;
			int y = hit.blockY;
			int z = hit.blockZ;
			int freezes = 0;

			if (!world.isRemote) {
				for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++)
						for (int k = -1; k <= 1; k++) {
							Block block = world.getBlock(x + i, y + j, z + k);
							if (block == Blocks.water || block == Blocks.flowing_water) {
								if (world.getBlockMetadata(x + i, y + j, z + k) == 0) {
									world.playAuxSFXAtEntity(null, 2001, x + i, y + j, z + k, Block.getIdFromBlock(Blocks.ice) + (0 << 12));
									world.setBlock(x + i, y + j, z + k, Blocks.ice);
									freezes++;
								}
							} else if (block == Blocks.lava || block == Blocks.flowing_lava)
								if (world.getBlockMetadata(x + i, y + j, z + k) == 0) {
									world.playAuxSFXAtEntity(null, 2001, x + i, y + j, z + k, Block.getIdFromBlock(Blocks.obsidian) + (0 << 12));
									world.setBlock(x + i, y + j, z + k, Blocks.obsidian);
									freezes++;
								} else {
									world.playAuxSFXAtEntity(null, 2001, x + i, y + j, z + k, Block.getIdFromBlock(Blocks.cobblestone) + (0 << 12));
									world.setBlock(x + i, y + j, z + k, Blocks.cobblestone);
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

			int dam = 0;
			if (world.getBiomeGenForCoords(x, z).temperature < 0.2F)
				dam = -1;
			else if (world.getBiomeGenForCoords(x, z).temperature > 1.1F)
				dam = 1;

			if (world.rand.nextInt(80) == 0)
				stack.setItemDamage(stack.getItemDamage() + dam);
		}
	}

	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		return func_150897_b(block) ? efficiencyOnProperMaterial : 0.0F;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableIcyPick;
	}
}
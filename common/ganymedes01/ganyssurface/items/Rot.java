package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class Rot extends Item {

	public Rot(int id) {
		super(id);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.ROT_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ROT_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		if (applyBonemeal(item, world, x, y, z, player)) {
			if (!world.isRemote)
				world.playAuxSFX(2005, x, y, z, 0);
			return true;
		}
		return false;
	}

	public static boolean applyBonemeal(ItemStack item, World world, int x, int y, int z, EntityPlayer player) {
		int target = world.getBlockId(x, y, z);

		BonemealEvent event = new BonemealEvent(player, world, target, x, y, z);
		if (MinecraftForge.EVENT_BUS.post(event))
			return false;

		if (event.getResult() == Result.ALLOW) {
			if (!world.isRemote)
				item.stackSize--;
			return true;
		}

		if (target == Block.sapling.blockID) {
			if (!world.isRemote) {
				if (world.rand.nextFloat() < 0.45D)
					((BlockSapling) Block.sapling).markOrGrowMarked(world, x, y, z, world.rand);
				--item.stackSize;
			}
			return true;
		} else if (target != Block.mushroomBrown.blockID && target != Block.mushroomRed.blockID) {
			if (target != Block.melonStem.blockID && target != Block.pumpkinStem.blockID) {
				if (target > 0 && Block.blocksList[target] instanceof BlockCrops)
					if (world.getBlockMetadata(x, y, z) == 7)
						return false;
					else {
						if (!world.isRemote) {
							((BlockCrops) Block.blocksList[target]).fertilize(world, x, y, z);
							--item.stackSize;
						}
						return true;
					}
				else {
					int i1;
					int j1;
					int k1;

					if (target == Block.cocoaPlant.blockID) {
						i1 = world.getBlockMetadata(x, y, z);
						j1 = BlockDirectional.getDirection(i1);
						k1 = BlockCocoa.func_72219_c(i1);

						if (k1 >= 2)
							return false;
						else {
							if (!world.isRemote) {
								++k1;
								world.setBlockMetadataWithNotify(x, y, z, k1 << 2 | j1, 2);
								--item.stackSize;
							}
							return true;
						}
					} else if (target != Block.grass.blockID)
						return false;
					else {
						if (!world.isRemote) {
							--item.stackSize;
							label102:

							for (i1 = 0; i1 < 128; ++i1) {
								j1 = x;
								k1 = y + 1;
								int l1 = z;

								for (int i2 = 0; i2 < i1 / 16; ++i2) {
									j1 += itemRand.nextInt(3) - 1;
									k1 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
									l1 += itemRand.nextInt(3) - 1;

									if (world.getBlockId(j1, k1 - 1, l1) != Block.grass.blockID || world.isBlockNormalCube(j1, k1, l1))
										continue label102;

								}

								if (world.getBlockId(j1, k1, l1) == 0)
									if (itemRand.nextInt(10) != 0)
										if (Block.tallGrass.canBlockStay(world, j1, k1, l1))
											world.setBlock(j1, k1, l1, Block.tallGrass.blockID, 1, 3);
										else
											ForgeHooks.plantGrass(world, j1, k1, l1);
							}
						}
						return true;
					}
				}
			} else if (world.getBlockMetadata(x, y, z) == 7)
				return false;
			else {
				if (!world.isRemote) {
					((BlockStem) Block.blocksList[target]).fertilizeStem(world, x, y, z);
					--item.stackSize;
				}
				return true;
			}
		} else {
			if (!world.isRemote) {
				if (world.rand.nextFloat() < 0.4D)
					((BlockMushroom) Block.blocksList[target]).fertilizeMushroom(world, x, y, z, world.rand);
				--item.stackSize;
			}
			return true;
		}
	}
}

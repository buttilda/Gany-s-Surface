package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Rot extends Item {

	@SideOnly(Side.CLIENT)
	private Icon[] icon;

	public Rot() {
		super(ModIDs.ROT_ID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysSurface.surfaceTab);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + (stack.getItemDamage() == 0 ? Utils.getUnlocalizedName(Strings.ROT_NAME) : Utils.getUnlocalizedName(Strings.FERTILIZER_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return icon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tabs, List list) {
		list.add(new ItemStack(itemID, 1, 0));
		list.add(new ItemStack(itemID, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icon = new Icon[2];
		icon[0] = reg.registerIcon(Utils.getItemTexture(Strings.ROT_NAME));
		icon[1] = reg.registerIcon(Utils.getItemTexture(Strings.FERTILIZER_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (applyBonemeal(item, world, x, y, z, player)) {
			if (!world.isRemote)
				world.playAuxSFX(2005, x, y, z, 0);
			return true;
		}
		return false;
	}

	private boolean applyBonemeal(ItemStack item, World world, int x, int y, int z, EntityPlayer player) {
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
				item.stackSize--;
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
							item.stackSize--;
						}
						return true;
					}
				else {
					int meta;
					int dir;
					int stage;

					if (target == Block.cocoaPlant.blockID) {
						meta = world.getBlockMetadata(x, y, z);
						dir = BlockDirectional.getDirection(meta);
						stage = BlockCocoa.func_72219_c(meta);

						if (stage >= 2)
							return false;
						else {
							if (!world.isRemote) {
								stage++;
								world.setBlockMetadataWithNotify(x, y, z, stage << 2 | dir, 2);
								item.stackSize--;
							}
							return true;
						}
					} else if (target != Block.grass.blockID)
						return false;
					else {
						if (!world.isRemote) {
							item.stackSize--;
							label102:

							for (meta = 0; meta < 128; meta++) {
								dir = x;
								stage = y + 1;
								int l1 = z;

								for (int i2 = 0; i2 < meta / 16; ++i2) {
									dir += itemRand.nextInt(3) - 1;
									stage += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
									l1 += itemRand.nextInt(3) - 1;

									if (world.getBlockId(dir, stage - 1, l1) != Block.grass.blockID || world.isBlockNormalCube(dir, stage, l1))
										continue label102;

								}

								if (world.getBlockId(dir, stage, l1) == 0)
									if (itemRand.nextInt(10) != 0)
										if (Block.tallGrass.canBlockStay(world, dir, stage, l1))
											world.setBlock(dir, stage, l1, Block.tallGrass.blockID, 1, 3);
										else
											ForgeHooks.plantGrass(world, dir, stage, l1);
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
					item.stackSize--;
				}
				return true;
			}
		} else {
			if (!world.isRemote) {
				if (world.rand.nextFloat() < 0.4D)
					((BlockMushroom) Block.blocksList[target]).fertilizeMushroom(world, x, y, z, world.rand);
				item.stackSize--;
			}
			return true;
		}
	}
}

package ganymedes01.ganyssurface.blocks;

import java.util.Random;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class InvertedRedstoneLamp extends Block implements IConfigurable {

	private final boolean isOff;

	public InvertedRedstoneLamp(boolean isOff) {
		super(Material.redstoneLight);
		this.isOff = isOff;
		setHardness(0.3F);
		setLightLevel(1.0F);
		setStepSound(soundTypeGlass);
		setLightLevel(isOff ? 0.0F : 1.0F);
		String suffix = isOff ? "_off" : "_on";
		setBlockTextureName("redstone_lamp" + suffix);
		setBlockName(Utils.getUnlocalisedName("inverted_redstone_lamp" + suffix));
		setCreativeTab(GanysSurface.enableInvertedRedsontLamp && !isOff ? GanysSurface.surfaceTab : null);

	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.isRemote)
			return;
		if (isOff && !world.isBlockIndirectlyGettingPowered(x, y, z))
			world.scheduleBlockUpdate(x, y, z, this, 4);
		else if (!isOff && world.isBlockIndirectlyGettingPowered(x, y, z))
			world.setBlock(x, y, z, ModBlocks.invertedRedstoneLampOff, 0, 2);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		onBlockAdded(world, x, y, z);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote && isOff && !world.isBlockIndirectlyGettingPowered(x, y, z))
			world.setBlock(x, y, z, ModBlocks.invertedRedstoneLampOn, 0, 2);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.invertedRedstoneLampOn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(ModBlocks.invertedRedstoneLampOn);
	}

	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(ModBlocks.invertedRedstoneLampOn);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableInvertedRedsontLamp;
	}
}
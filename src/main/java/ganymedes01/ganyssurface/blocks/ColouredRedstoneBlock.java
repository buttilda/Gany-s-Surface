package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockOreStorage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ColouredRedstoneBlock extends BlockOreStorage {

	protected ColouredRedstoneBlock() {
		super(ModIDs.COLOURED_REDSTONE_BLOCK_ID);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(soundMetalFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getBlockTexture(Strings.COLOURED_REDSTONE_BLOCK_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.COLOURED_REDSTONE_BLOCK_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return getRenderColor(world.getBlockMetadata(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		float[] colour = EntitySheep.fleeceColorTable[BlockColored.getBlockFromDye(meta)];
		return Utils.getColour((int) (colour[0] * 255), (int) (colour[1] * 255), (int) (colour[2] * 255));
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < 16; i++)
			if (i != 1) // Skip Red
				list.add(new ItemStack(id, 1, i));
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side].getOpposite();
		int id = world.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		if (id == Block.redstoneWire.blockID || Block.blocksList[id] instanceof ColouredRedstone && id != ModBlocks.colouredRedstone[meta].blockID)
			return 0;
		else
			return 15;
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return true;
	}
}
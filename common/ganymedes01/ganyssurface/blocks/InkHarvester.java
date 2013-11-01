package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityInkHarvester;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class InkHarvester extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon blockOn, blockOff;

	protected InkHarvester() {
		super(ModIDs.INK_HARVESTER_ID, Material.rock);
		setHardness(0.2F);
		setStepSound(soundStoneFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.INK_HARVESTER_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta == 0)
			return blockOff;
		else
			return blockOn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockOn = reg.registerIcon(Utils.getBlockTexture(Strings.INK_HARVESTER_NAME, true) + "on");
		blockOff = reg.registerIcon(Utils.getBlockTexture(Strings.INK_HARVESTER_NAME, true) + "off");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityInkHarvester();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityInkHarvester tile = (TileEntityInkHarvester) world.getBlockTileEntity(x, y, z);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.BLOCK_DETECTOR, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityInkHarvester tile = (TileEntityInkHarvester) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int i = 0; i < tile.getSizeInventory(); i++) {
				ItemStack stack = tile.getStackInSlot(i);
				if (stack != null)
					Utils.dropStack(world, x, y, z, stack);
			}
			world.func_96440_m(x, y, z, par5);
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}
}
package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityInkHarvester;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class InkHarvester extends BlockContainer implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon blockOn, blockOff;

	public InkHarvester() {
		super(Material.ground);
		setHardness(0.2F);
		setStepSound(soundTypeStone);
		setBlockName(Utils.getUnlocalisedName(Strings.INK_HARVESTER_NAME));
		setCreativeTab(GanysSurface.enableInkHarvester ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta == 0)
			return blockOff;
		else
			return blockOn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockOn = reg.registerIcon(Utils.getBlockTexture(Strings.INK_HARVESTER_NAME) + "_on");
		blockOff = reg.registerIcon(Utils.getBlockTexture(Strings.INK_HARVESTER_NAME) + "_off");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityInkHarvester();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityInkHarvester tile = Utils.getTileEntity(world, x, y, z, TileEntityInkHarvester.class);
			if (tile != null && tile.isFormed()) {
				player.openGui(GanysSurface.instance, GUIsID.INK_HARVESTER, world, x, y, z);
				return true;
			}
			return false;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableInkHarvester;
	}
}
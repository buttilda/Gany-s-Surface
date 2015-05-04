package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityAutoEncaser;
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

public class AutoEncaser extends BlockContainer implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public AutoEncaser() {
		super(Material.iron);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName(Strings.AUTO_ENCASER_NAME));
		setCreativeTab(GanysSurface.enableEncasers ? GanysSurface.surfaceTab : null);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAutoEncaser();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			player.openGui(GanysSurface.instance, GUIsID.AUTO_ENCASER, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? icons[0] : icons[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[2];
		for (int i = 0; i < icons.length; i++)
			icons[i] = reg.registerIcon(Utils.getBlockTexture(Strings.AUTO_ENCASER_NAME) + i);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableEncasers;
	}
}
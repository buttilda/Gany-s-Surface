package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityAutoEncaser;
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

public class AutoEncaser extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public AutoEncaser() {
		super(ModIDs.AUTO_ENCASER_ID, Material.iron);
		setHardness(2.0F);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.AUTO_ENCASER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
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
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityAutoEncaser tile = (TileEntityAutoEncaser) world.getBlockTileEntity(x, y, z);
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

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? icons[0] : icons[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icons = new Icon[2];
		for (int i = 0; i < icons.length; i++)
			icons[i] = reg.registerIcon(Utils.getBlockTexture(Strings.AUTO_ENCASER_NAME) + i);
	}
}
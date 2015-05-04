package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.tileentities.TileEntityWoodChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.texture.IIconRegister;
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

public class BlockWoodChest extends BlockChest implements IConfigurable {

	private final Block plank;
	private final int meta;

	public BlockWoodChest(Block plank, int meta) {
		super(meta + 200);
		this.plank = plank;
		this.meta = meta;
		setHardness(2.5F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName("chest" + meta));
		setCreativeTab(GanysSurface.enableChests ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return plank.getIcon(side, this.meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWoodChest();
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableChests;
	}

	public ChestType getType() {
		return getType(field_149956_a);
	}

	public static ChestType getType(int type) {
		return ChestType.values()[type - 200];
	}

	public enum ChestType {
		OAK,
		SPRUCE,
		BIRCH,
		JUNGLE,
		ACACIA,
		BIG_OAK
	}
}
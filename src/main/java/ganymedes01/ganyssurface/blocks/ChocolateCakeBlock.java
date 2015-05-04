package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemChocolateCake;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockCake;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

public class ChocolateCakeBlock extends BlockCake implements ISubBlocksBlock, IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon cakeTopIcon, cakeBottomIcon, cakeInner;

	public ChocolateCakeBlock() {
		super();
		disableStats();
		setHardness(0.5F);
		setStepSound(soundTypeCloth);
		setBlockName(Utils.getUnlocalisedName(Strings.CHOCOLATE_CAKE_NAME));
		setCreativeTab(GanysSurface.enableChocolate ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		eatCakeSlice(world, x, y, z, player);
		return true;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		eatCakeSlice(world, x, y, z, player);
	}

	private void eatCakeSlice(World world, int x, int y, int z, EntityPlayer player) {
		if (player.canEat(false)) {
			player.getFoodStats().addStats(2, 0.6F);
			int l = world.getBlockMetadata(x, y, z) + 1;

			if (l >= 6)
				world.setBlockToAir(x, y, z);
			else
				world.setBlockMetadataWithNotify(x, y, z, l, 2);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(ModBlocks.chocolateCake);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? cakeTopIcon : side == 0 ? cakeBottomIcon : meta > 0 && side == 4 ? cakeInner : blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Utils.getBlockTexture(Strings.CHOCOLATE_CAKE_NAME) + "_side");
		cakeInner = reg.registerIcon(Utils.getBlockTexture(Strings.CHOCOLATE_CAKE_NAME) + "_inner");
		cakeTopIcon = reg.registerIcon(Utils.getBlockTexture(Strings.CHOCOLATE_CAKE_NAME) + "_top");
		cakeBottomIcon = reg.registerIcon(Utils.getBlockTexture(Strings.CHOCOLATE_CAKE_NAME) + "_bottom");
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemChocolateCake.class;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableChocolate;
	}
}
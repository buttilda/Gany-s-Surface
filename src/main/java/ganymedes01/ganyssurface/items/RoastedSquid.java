package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class RoastedSquid extends ItemFood implements IConfigurable {

	public RoastedSquid() {
		super(6, 0.5F, true);
		setFull3D();
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ROASTED_SQUID_NAME));
		setCreativeTab(GanysSurface.enablePocketCritters ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePocketCritters;
	}
}
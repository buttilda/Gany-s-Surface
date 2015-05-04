package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.api.IQuiver;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Quiver extends Item implements IQuiver, IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon empty;

	public static int ARROW_STACK_SIZE = 64;

	public Quiver() {
		setFull3D();
		setMaxStackSize(1);
		setTextureName("quiver");
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.QUIVER_NAME));
		setCreativeTab(GanysSurface.enableQuiver ? GanysSurface.surfaceTab : null);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		int max = getMaxArrowCount(stack);
		int current = getArrowCount(stack);

		if (player.isSneaking()) {
			if (current > 0) {
				int amount = Math.min(ARROW_STACK_SIZE, current);
				setArrowCount(stack, current - amount);
				InventoryUtils.addToPlayerInventory(player, new ItemStack(Items.arrow, amount), player.posX, player.posY, player.posZ);
			}
			return stack;
		}

		if (current < max)
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack s = player.inventory.getStackInSlot(i);
				if (s != null && s.getItem() == Items.arrow && s.stackSize > 0) {
					int curr = getArrowCount(stack);
					int newValue = Math.min(curr + s.stackSize, max);
					setArrowCount(stack, newValue);
					s.stackSize -= newValue - curr;
					if (s.stackSize <= 0)
						player.inventory.setInventorySlotContents(i, null);
					player.playSound("random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					return stack;
				}
			}

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(getArrowCount(stack) + "x " + StatCollector.translateToLocal("item.arrow.name"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		empty = reg.registerIcon(Utils.getItemTexture(Strings.QUIVER_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta <= 0 ? empty : itemIcon;
	}

	@Override
	public void setArrowAmount(ItemStack stack, int amount) {
		stack.setItemDamage(Math.min(amount, getMaxArrowCount(stack)));
	}

	@Override
	public int getArrowAmount(ItemStack stack) {
		return stack.getItemDamage();
	}

	@Override
	public int getMaxArrowCount(ItemStack stack) {
		return ARROW_STACK_SIZE * 4;
	}

	public static int getArrowCount(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof IQuiver)
			return ((IQuiver) stack.getItem()).getArrowAmount(stack);
		return 0;
	}

	public static void setArrowCount(ItemStack stack, int count) {
		if (stack != null && stack.getItem() instanceof IQuiver)
			((IQuiver) stack.getItem()).setArrowAmount(stack, count);
		stack.setItemDamage(count);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableQuiver;
	}
}
package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Horsalyser extends Item implements IConfigurable {

	private final DecimalFormat decForm = new DecimalFormat("##.##");

	public Horsalyser() {
		setMaxDamage(64);
		setMaxStackSize(1);
		decForm.setRoundingMode(RoundingMode.DOWN);
		setTextureName(Utils.getItemTexture(Strings.HORSALYSER_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.HORSALYSER_NAME));
		setCreativeTab(GanysSurface.enableAnalisers ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		World world = player.worldObj;

		if (target instanceof EntityHorse) {
			EntityHorse horse = (EntityHorse) target;
			String name = horse.getCommandSenderName();

			if (world.isRemote) {
				Utils.sendMessageToPlayer(player, String.format("----- Analysing %s's Data -----", name));
				Utils.sendMessageToPlayer(player, "Tamed: " + isTamed(horse.isTame(), horse.func_152119_ch()));
				Utils.sendMessageToPlayer(player, "Type: " + getType(horse.getHorseType()));
				Utils.sendMessageToPlayer(player, "Jump Strength: " + getHorseJumpStrength(horse.getHorseJumpStrength()));
				Utils.sendMessageToPlayer(player, "Size: " + getHorseSize(horse.getHorseSize()));
				Utils.sendMessageToPlayer(player, "Max Health: " + getHorseHealth(horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()));
				Utils.sendMessageToPlayer(player, "Max Speed: " + getHorseMaxSpeed(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue()));
				Utils.sendMessageToPlayer(player, "----- End -----");
			} else {
				if (!player.capabilities.isCreativeMode)
					horse.attackEntityFrom(DamageSource.generic, 1.0F);
				stack.damageItem(1, player);
			}
			return true;
		}
		return false;
	}

	private String getType(int type) {
		switch (type) {
			case 0:
				return "Horse";
			case 1:
				return "Donkey";
			case 2:
				return "Mule";
			case 3:
				return "Undead";
			case 4:
				return "Skeleton";
			default:
				return "Unknown";
		}
	}

	// Max = 1
	private String getHorseSize(float size) {
		return decForm.format(size * 100) + " %";
	}

	// Max = 1
	private String getHorseJumpStrength(double jump) {
		return decForm.format((jump - 0.4F) * (500.0F / 3.0F)) + " %";
	}

	// Max = 30
	private String getHorseHealth(double health) {
		return decForm.format(health) + " (" + (int) health / 2 + " H)";
	}

	// Max = 0.3375
	private String getHorseMaxSpeed(double maxSpeed) {
		return decForm.format((maxSpeed - 0.1125D) * (4000D / 9D)) + " %";
	}

	private String isTamed(boolean isTamed, String owner) {
		return isTamed ? owner : "No.";
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableAnalisers;
	}
}
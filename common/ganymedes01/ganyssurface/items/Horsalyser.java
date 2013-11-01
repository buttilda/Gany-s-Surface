package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Horsalyser extends Item implements IRepairable {

	private final DecimalFormat decForm = new DecimalFormat("##.##");

	public Horsalyser() {
		super(ModIDs.HORSALYSER_ID);
		setMaxDamage(64);
		setMaxStackSize(1);
		setCreativeTab(GanysSurface.surfaceTab);
		decForm.setRoundingMode(RoundingMode.DOWN);
		setTextureName(Utils.getItemTexture(Strings.HORSALYSER_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.HORSALYSER_NAME));
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		World world = player.worldObj;

		if (target instanceof EntityHorse) {
			EntityHorse horse = (EntityHorse) target;
			String name = horse.getCustomNameTag() == "" ? "Horse" : horse.getCustomNameTag();
			if (world.isRemote) {
				Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("----- Analysing " + name + "'s Data -----"));
				Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Tamed: " + isTamed(horse.isTame(), horse.getOwnerName())));
				Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Type: " + getType(horse.getHorseType())));
				Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Jump Strength: " + getHorseJumpStrength(horse.getHorseJumpStrength())));
				Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Size: " + getHorseSize(horse.getHorseSize())));
				Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Max Health: " + getHorseHealth(horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue())));
				Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Max Speed: " + getHorseMaxSpeed(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue())));
				Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("----- End -----"));
			} else {
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

	private String getHorseSize(float size) {
		return decForm.format(size * 100) + " %";
	}

	private String getHorseJumpStrength(double jump) {
		return decForm.format((jump - 0.4D) * (500D / 3D)) + " %";
	}

	private String getHorseHealth(double health) {
		return decForm.format(health) + " (" + (int) health / 2 + " H)";
	}

	private String getHorseMaxSpeed(double maxSpeed) {
		return decForm.format((maxSpeed - 0.1125D) * (4000D / 9D)) + " %";
	}

	private String isTamed(boolean isTamed, String owner) {
		return isTamed ? owner : "No.";
	}
}
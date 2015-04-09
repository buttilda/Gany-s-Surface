package ganymedes01.ganyssurface.core.utils;

import ganymedes01.ganyssurface.lib.Reference;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.ReflectionHelper;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Utils {

	public static String getUnlocalisedName(String name) {
		return Reference.MOD_ID + "." + name;
	}

	public static String getBlockTexture(String name) {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + name;
	}

	public static String getItemTexture(String name) {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + name;
	}

	public static String getArmourTexture(String name, int layer) {
		return Reference.ARMOUR_TEXTURE_PATH + name.toLowerCase() + "_layer_" + layer + ".png";
	}

	public static String getGUITexture(String name) {
		return Reference.GUI_TEXTURE_PATH + name + ".png";
	}

	public static String getEntityTexture(String name) {
		return Reference.ENTITY_TEXTURE_PATH + name + ".png";
	}

	public static String getConainerName(String name) {
		return "container." + Reference.MOD_ID + "." + name;
	}

	public static int getColour(int R, int G, int B) {
		return new Color(R < 0 ? 0 : R, G < 0 ? 0 : G, B < 0 ? 0 : B).getRGB() & 0x00ffffff;
	}

	public static ResourceLocation getResource(String path) {
		return new ResourceLocation(path);
	}

	public static ArrayList<Integer> getRandomizedList(int min, int max) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = min; i < max; i++)
			list.add(i);
		Collections.shuffle(list);
		return list;
	}

	public static EntityPlayer getPlayer(World world) {
		if (world.isRemote || !(world instanceof WorldServer))
			return null;
		return FakePlayerFactory.get((WorldServer) world, new GameProfile(UUID.nameUUIDFromBytes(Reference.MOD_ID.getBytes()), "[" + Reference.CHANNEL + "]"));
	}

	@SuppressWarnings("unchecked")
	public static <T> T getTileEntity(IBlockAccess world, int x, int y, int z, Class<T> cls) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!cls.isInstance(tile))
			return null;
		return (T) tile;
	}

	@SuppressWarnings({ "unchecked" })
	public static ChunkCoordinates findNearestVillage(World world, int x, int y, int z) {
		try {
			Village village = null;
			float nearestDistance = Float.MAX_VALUE;
			List<Village> villageList = (List<Village>) ReflectionHelper.findField(VillageCollection.class, new String[] { "villageList", "field_75552_d" }).get(world.villageCollectionObj);
			Iterator<Village> iterator = villageList.iterator();

			while (iterator.hasNext()) {
				Village next = iterator.next();
				float distance = next.getCenter().getDistanceSquared(x, y, z);

				if (distance < nearestDistance) {
					village = next;
					nearestDistance = distance;
				}
			}

			return village != null ? village.getCenter() : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void sendMessageToPlayer(EntityPlayer player, Object message) {
		player.addChatMessage(new ChatComponentText(message.toString()));
	}
}
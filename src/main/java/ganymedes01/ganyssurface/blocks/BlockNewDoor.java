package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.RenderIDs;

import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNewDoor extends BlockDoor {

	private final String name;

	public BlockNewDoor(String name) {
		super(Material.wood);
		disableStats();
		this.name = name;
		setHardness(3.0F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalizedName("door" + name.substring(0, 1).toUpperCase() + name.substring(1)));
		setBlockTextureName(Utils.getBlockTexture("door_" + name));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return getItemDoor();
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return (meta & 8) != 0 ? null : getItemDoor();
	}

	@Override
	public int getRenderType() {
		return RenderIDs.DOOR;
	}

	private Item getItemDoor() {
		if (name.equals("acacia"))
			return ModItems.doorAcacia;
		else if (name.equals("birch"))
			return ModItems.doorBirch;
		else if (name.equals("dark_oak"))
			return ModItems.doorDarkOak;
		else if (name.equals("jungle"))
			return ModItems.doorJungle;
		else if (name.equals("spruce"))
			return ModItems.doorSpruce;

		return Items.wooden_door;
	}
}
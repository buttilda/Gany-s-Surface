package ganymedes01.ganyssurface.world;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Temple {

	private static final Map<WorldCoord, Integer> map = new HashMap<WorldCoord, Integer>();

	public static void makeMap() {
		try {
			InputStream is = GanysSurface.class.getResourceAsStream("/assets/ganyssurface/Temple.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String s;
			while ((s = br.readLine()) != null) {
				String[] data = s.split("-");
				data[0] = data[0].trim();
				data[0] = data[0].substring(1, data[0].length() - 1);

				data[1] = data[1].trim();

				String[] coords = data[0].split(",");

				WorldCoord key = new WorldCoord(Integer.parseInt(coords[0].trim()), Integer.parseInt(coords[1].trim()), Integer.parseInt(coords[2].trim()));
				int value = Integer.parseInt(data[1]);

				map.put(key, value);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<WorldCoord, Integer> getMap() {
		return map;
	}

	public static void buildTemple(World world, int x, int y, int z, int height) {
		if (world.isRemote)
			return;

		height = Math.max(Math.min(height, 9), 0);

		for (Entry<WorldCoord, Integer> entry : Temple.getMap().entrySet()) {
			WorldCoord pos = entry.getKey();
			int value = entry.getValue();

			Block block = null;
			int meta = 0;
			switch (value) {
				case 0:
				case 1:
				case 2:
					block = ModBlocks.prismarineBlocks;
					meta = value;
					break;
				case 3:
					block = ModBlocks.seaLantern;
					break;
				case 4:
					block = Blocks.gold_block;
					break;
				case 5:
					block = Blocks.sponge;
					break;
				case 6:
					block = Blocks.water;
					break;
			}

			if (block != null && block != Blocks.water)
				world.setBlock(pos.x + x, pos.y + y + height, pos.z + z, block, meta, 2);
		}

		for (int i = 0; i < 7; i++) {
			generatePillar(world, x + 5 * i + 4 * i, y, z, height, ModBlocks.prismarineBlocks, 1);
			generatePillar(world, x, y, z + 5 * i + 4 * i, height, ModBlocks.prismarineBlocks, 1);
			generatePillar(world, x + 54, y, z + 5 * i + 4 * i, height, ModBlocks.prismarineBlocks, 1);
			if (i != 3)
				generatePillar(world, x + 5 * i + 4 * i, y, z + 54, height, ModBlocks.prismarineBlocks, 1);
		}
	}

	private static void generatePillar(World world, int x, int y, int z, int height, Block block, int meta) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < height; j++)
				for (int k = 0; k < 4; k++)
					if (world.getBlock(x + i, y + j, z + k).isReplaceable(world, x + i, y + j, z + k))
						world.setBlock(x + i, y + j, z + k, block, meta, 2);
	}

	public static void generateFile(World world, int x, int y, int z, String path) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));

			for (int i = 0; i < 58; i++)
				for (int j = 0; j < 31 - 9; j++)
					for (int k = 0; k < 58; k++) {
						Block b = world.getBlock(x + i, y + j, z + k);
						int meta = world.getBlockMetadata(x + i, y + j, z + k);

						String s = "(" + i + ", " + j + ", " + k + ") - ";
						if (b == ModBlocks.prismarineBlocks)
							s += meta;
						else if (b == ModBlocks.seaLantern)
							s += 3;
						else if (b == Blocks.gold_block)
							s += 4;
						else if (b == Blocks.sponge)
							s += 5;
						else if (b.isAir(world, x + i, y + j, z + k))
							s += 6;

						bw.write(s);
						bw.newLine();
					}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
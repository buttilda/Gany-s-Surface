package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.integration.bc.FillerFillUndergroundCaves;
import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class LoadTextureEvent {

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre evt) {
		if (evt.map.textureType == 0) {
			TextureMap terrainMap = evt.map;
			FillerFillUndergroundCaves.setIcon(terrainMap.registerIcon(Reference.FILLER_PATTER_TEXTURE_PATH + "fillUndergroundCaves"));
		}
	}
}
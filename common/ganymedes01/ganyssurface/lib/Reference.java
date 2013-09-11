package ganymedes01.ganyssurface.lib;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Reference {

	public static final String MOD_ID = "ganyssurface";
	public static final String MOD_NAME = "Gany's Surface";
	public static final String DEPENDENCIES = "required-after:Forge@[9.10.1.849,)";
	public static final String CHANNEL_NAME = "GanysSurface";
	public static final String MASTER = "GanysMods";
	public static final String VERSION_NUMBER = "1.3.0";
	public static final String ITEM_BLOCK_TEXTURE_PATH = MOD_ID + ":";
	public static final String ARMOUR_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/models/armor/";
	public static final String ENTITY_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/entities/";
	public static final String ENTITY_ITEM_TEXTURE_PATH = "textures/entities/";
	public static final String GUI_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/gui/container/";

	public static final String SERVER_PROXY_CLASS = "ganymedes01.ganyssurface.core.proxy.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "ganymedes01.ganyssurface.core.proxy.ClientProxy";
}

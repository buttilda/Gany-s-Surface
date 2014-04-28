package ganymedes01.ganyssurface.core.utils;

import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import net.minecraft.util.EnumChatFormatting;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class VersionHelper implements Runnable {

	private static VersionHelper instance = new VersionHelper();
	public static Properties remoteVersionProperties = new Properties();

	public static final byte UNINITIALIZED = 0;
	public static final byte CURRENT = 1;
	public static final byte OUTDATED = 2;
	public static final byte ERROR = 3;
	public static final byte FINAL_ERROR = 4;

	private static byte result = UNINITIALIZED;
	public static String remoteVersion = null;
	public static String updateURL = null;

	public static void checkVersion() {
		InputStream remoteVersionRepoStream = null;
		result = UNINITIALIZED;

		try {
			URL remoteVersionURL = new URL(Reference.VERSION_CHECK_FILE);
			remoteVersionRepoStream = remoteVersionURL.openStream();
			remoteVersionProperties.loadFromXML(remoteVersionRepoStream);
			String remoteVersionProperty = remoteVersionProperties.getProperty(Reference.CHANNEL);

			if (remoteVersionProperty != null) {
				String[] remoteVersionTokens = remoteVersionProperty.split("\\|");
				if (remoteVersionTokens.length >= 3) {
					remoteVersion = remoteVersionTokens[0];
					Reference.LATEST_VERSION = remoteVersionTokens[1];
					updateURL = remoteVersionTokens[2];
				} else
					result = ERROR;

				if (remoteVersion != null) {
					int version = Integer.parseInt(remoteVersion);
					if (version <= Reference.RAW_VERSION_NUMBER)
						result = CURRENT;
					else
						result = OUTDATED;
				}
			} else
				result = ERROR;

		} catch (Exception e) {
		} finally {
			if (result == UNINITIALIZED)
				result = ERROR;
			try {
				if (remoteVersionRepoStream != null)
					remoteVersionRepoStream.close();
			} catch (Exception ex) {
			}
		}
	}

	public static String getResultMessage() {
		switch (result) {
			case UNINITIALIZED:
				return Strings.VERSION_CHECK_FAIL;
			case CURRENT:
				return Strings.CURRENT_MESSAGE;
			case OUTDATED:
				if (remoteVersion != null && updateURL != null)
					return Strings.OUTDATED_MESSAGE;
			case ERROR:
				return Strings.VERSION_CHECK_FAIL_CONNECT;
			case FINAL_ERROR:
				return Strings.VERSION_CHECK_FAIL_CONNECT_FINAL;
			default:
				result = ERROR;
				return Strings.VERSION_CHECK_FAIL_CONNECT;
		}
	}

	public static String getResultMessageForClient() {
		return EnumChatFormatting.GOLD + Reference.MOD_NAME + EnumChatFormatting.WHITE + " is " + EnumChatFormatting.RED + "outdated" + EnumChatFormatting.WHITE + ". Get " + EnumChatFormatting.GOLD + Reference.LATEST_VERSION + EnumChatFormatting.WHITE + " at: " + EnumChatFormatting.GREEN + updateURL;
	}

	public static byte getResult() {
		return result;
	}

	@Override
	public void run() {
		int count = 0;

		try {
			while (count < 3 - 1 && (result == UNINITIALIZED || result == ERROR)) {
				checkVersion();
				count++;

				if (result == UNINITIALIZED || result == ERROR)
					Thread.sleep(10000);
			}
			if (result == ERROR)
				result = FINAL_ERROR;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void execute() {

		new Thread(instance).start();
	}
}
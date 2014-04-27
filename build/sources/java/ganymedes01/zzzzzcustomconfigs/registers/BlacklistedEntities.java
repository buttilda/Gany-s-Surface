package ganymedes01.zzzzzcustomconfigs.registers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlacklistedEntities {

	public static final ArrayList<String> entityBlacklist = new ArrayList<String>();

	public static void blacklistEntityFromLine(Logger logger, String line) {
		entityBlacklist.add(line.toLowerCase());
		logger.log(Level.INFO, "\tEntity " + line + " blacklisted");
	}
}
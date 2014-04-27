package ganymedes01.ganyssurface.core.utils;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class IdGenerator {

	private final int itemIDBase, blockIDbase;
	private int currentItemID, currentBlockID;

	public IdGenerator(int itemIDBase, int blockIDbase) {
		this.itemIDBase = itemIDBase - 1;
		this.blockIDbase = blockIDbase - 1;
		currentItemID = this.itemIDBase;
		currentBlockID = this.blockIDbase;
	}

	public int getNextItemID() {
		return currentItemID++;
	}

	public int getNextBlockID() {
		return currentBlockID++;
	}

	public int getLastItemID() {
		return currentItemID;
	}

	public int getLastBlockID() {
		return currentBlockID;
	}
}
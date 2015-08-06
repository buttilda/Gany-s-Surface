package ganymedes01.ganyssurface.inventory;

public interface INoConflictRecipeContainer {

	void handleButtonClick(boolean isFirstMatrix, int bump);

	void setCurrentResultIndex(boolean isFirstMatrix, int index);

	void setHasMultipleResults(boolean isFirstMatrix, boolean hasMultipleResults);

	boolean hasMultipleResults(boolean isFirstMatrix);
}
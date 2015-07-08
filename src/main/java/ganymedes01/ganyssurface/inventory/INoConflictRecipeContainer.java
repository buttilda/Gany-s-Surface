package ganymedes01.ganyssurface.inventory;

public interface INoConflictRecipeContainer {

	void handleButtonClick(boolean isFirstValue, int bump);

	void setCurrentResultIndex(boolean isFirstValue, int index);
}
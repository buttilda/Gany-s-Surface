package ganymedes01.ganyssurface.tileentities;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityDualWorkTable extends TileEntityWorkTable {

	public final WorkTableCrafting craftMatrixRight = new WorkTableCrafting(this, 9);

	public TileEntityDualWorkTable() {
		super(18);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (craftMatrixRight.container != null && craftMatrixRight.notifyChange && craftMatrixRight.locked) {
			craftMatrixRight.container.onCraftMatrixChanged(craftMatrixRight);
			craftMatrixRight.notifyChange = false;
			craftMatrixRight.unlock();
		}
	}
}
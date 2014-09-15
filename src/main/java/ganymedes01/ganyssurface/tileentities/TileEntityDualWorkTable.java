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
}
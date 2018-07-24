package ganymedes01.ganyssurface.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArmor extends Slot {

    private int type;

    public SlotArmor(IInventory inventory, int index, int x, int y, int type) {
        super(inventory, index, x, y);
        this.type = type;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return checkIsValidArmor(stack);
    }

    private boolean checkIsValidArmor(ItemStack itemStack) {
        boolean allow = false;
        if (this.type == 0 && itemStack.getItem().isValidArmor(itemStack, 0, null)) {
            allow = true;
        }
        if (this.type == 1 && itemStack.getItem().isValidArmor(itemStack, 1, null)) {
            allow = true;
        }
        if (this.type == 2 && itemStack.getItem().isValidArmor(itemStack, 2, null)) {
            allow = true;
        }
        if (this.type == 3 && itemStack.getItem().isValidArmor(itemStack, 3, null)) {
            allow = true;
        }
        if (allow) {
            return true;
        }
        return false;
    }
}

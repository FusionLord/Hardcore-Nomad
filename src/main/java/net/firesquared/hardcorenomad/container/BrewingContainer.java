package net.firesquared.hardcorenomad.container;

import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBrewingStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//Derived from the vanilla brewing stand code
//Attempted to clean out the worst of the spaghetti code
public class BrewingContainer extends Container
{
    public final TileEntityBrewingStand tileBrewingStand;
    /** Instance of Slot. */
    private final Slot theSlot;
    private int brewTime;

    public BrewingContainer(InventoryPlayer playerInventory, TileEntityBrewingStand brewingStand)
    {
        this.tileBrewingStand = brewingStand;
        this.addSlotToContainer(new BrewingContainer.Potion(playerInventory.player, brewingStand, 0, 56, 46));
        this.addSlotToContainer(new BrewingContainer.Potion(playerInventory.player, brewingStand, 1, 79, 53));
        this.addSlotToContainer(new BrewingContainer.Potion(playerInventory.player, brewingStand, 2, 102, 46));
        this.theSlot = this.addSlotToContainer(new BrewingContainer.Ingredient(brewingStand, 3, 79, 17));
        int i;

        for (i = 1; i < 4; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9, 8 + j * 18, 66 + i * 18));

        for (i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
    }

    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getBrewTime());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.brewTime != this.tileBrewingStand.getBrewTime())
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getBrewTime());
            }
        }

        this.brewTime = this.tileBrewingStand.getBrewTime();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int barID, int time)
    {
        if (barID == 0)
            this.tileBrewingStand.setBrewTime(time);
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.tileBrewingStand.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotID)
    {
        ItemStack isCopy = null;
        Slot slot = (Slot)this.inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack())
        {
            ItemStack is = slot.getStack();
            isCopy = is.copy();

            if ((slotID < 0 || slotID > 2) && slotID != 3)
            {
                if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(is))
                    if (!this.mergeItemStack(is, 3, 4, false))
                        return null;
                if (Potion.canHoldPotion(isCopy))
                    if (!this.mergeItemStack(is, 0, 3, false))
                        return null;
                if (slotID >= 4 && slotID < 31)
                    if (!this.mergeItemStack(is, 31, 40, false))
                        return null;
                if (slotID >= 31 && slotID < 40)
                    if (!this.mergeItemStack(is, 4, 31, false))
                        return null;
                if (!this.mergeItemStack(is, 4, 40, false))
                    return null;
            }
            else
            {
                if (!this.mergeItemStack(is, 4, 40, true))
                    return null;

                slot.onSlotChange(is, isCopy);
            }

            if (is.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();

            if (is.stackSize == isCopy.stackSize)
                return null;

            slot.onPickupFromSlot(entityPlayer, is);
        }

        return isCopy;
    }

    class Ingredient extends Slot
    {
        public Ingredient(IInventory inventory, int slot, int dispX, int dispY)
        {
            super(inventory, slot, dispX, dispY);
        }

        /**
         * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
         */
        public boolean isItemValid(ItemStack itemStack)
        {
            return itemStack != null ? itemStack.getItem().isPotionIngredient(itemStack) : false;
        }

        /**
         * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
         * case of armor slots)
         */
        public int getSlotStackLimit()
        {
            return 64;
        }
    }

    static class Potion extends Slot
        {
            /** The player that has this container open. */
            private EntityPlayer player;

            public Potion(EntityPlayer entityPlayer, IInventory iInventory, int slot, int dispX, int dispY)
            {
                super(iInventory, slot, dispX, dispY);
                this.player = entityPlayer;
            }

            /**
             * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
             */
            public boolean isItemValid(ItemStack itemStack)
            {
                /**
                 * Returns true if this itemstack can be filled with a potion
                 */
                return canHoldPotion(itemStack);
            }

            /**
             * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
             * the case of armor slots)
             */
            public int getSlotStackLimit()
            {
                return 1;
            }

            public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack)
            {
                if (itemStack.getItem() instanceof ItemPotion && itemStack.getItemDamage() > 0)
                {
                    this.player.addStat(AchievementList.potion, 1);
                }

                super.onPickupFromSlot(entityPlayer, itemStack);
            }

            /**
             * Returns true if this itemstack can be filled with a potion
             */
            public static boolean canHoldPotion(ItemStack is)
            {
                return is != null && (is.getItem() instanceof ItemPotion || is.getItem() == Items.glass_bottle);
            }
        }

	public boolean hasCustomInventoryName()
	{
		return tileBrewingStand.hasCustomInventoryName();
	}

	public String getInventoryName()
	{
		return tileBrewingStand.getInventoryName();
	}

	public int getBrewTime()
	{
		return brewTime;
	}
    
}

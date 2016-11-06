package gtPlusPlus.xmod.forestry.bees.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.*;
import gtPlusPlus.core.creative.AddToCreativeTab;
import gtPlusPlus.core.lib.CORE;

public class MB_ItemFrame extends Item implements IHiveFrame
{
	private MB_FrameType type;
	private EnumRarity rarity_value = EnumRarity.uncommon;
	private final String toolTip;

	public MB_ItemFrame(MB_FrameType frameType, String description)
	{
		this(frameType, EnumRarity.uncommon, description);
	}
	
	public MB_ItemFrame(MB_FrameType frameType, EnumRarity rarity, String description)
	{
		super();
		this.type = frameType;
		this.setMaxDamage(this.type.maxDamage);
		this.setMaxStackSize(1);
		this.setCreativeTab(AddToCreativeTab.tabMisc);
		this.setUnlocalizedName("frame" + frameType.getName());
		this.rarity_value = rarity;
		this.toolTip = description;
		GameRegistry.registerItem(this, "frame" + frameType.getName());
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer aPlayer, List list, boolean bool) {
		if (toolTip != "" || !toolTip.equals("")){			
		list.add(EnumChatFormatting.GRAY+toolTip);
		}
		super.addInformation(stack, aPlayer, list, bool);
	}	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(CORE.MODID + ":frame" + type.getName());
	}
	
	// --------- IHiveFrame functions -----------------------------------------
	
	@Override
	public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear) {
		frame.setItemDamage(frame.getItemDamage() + wear);

		if (frame.getItemDamage() >= frame.getMaxDamage()) {
			// Break the frame.
			frame = null;
		}

		return frame;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack){
		return rarity_value;
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack){
		if (rarity_value == EnumRarity.uncommon || rarity_value == EnumRarity.common){
			return false;
		}
		return true;
	}

	public IBeeModifier getBeeModifier() {
		return type;
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2)
	{
		return false;
	}
	
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return type.getTerritoryModifier(genome, currentModifier);
	}

	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return type.getMutationModifier(genome, mate, currentModifier);
	}

	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return type.getLifespanModifier(genome, mate, currentModifier);
	}

	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return type.getProductionModifier(genome, currentModifier);
	}

	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		return type.getFloweringModifier(genome, currentModifier);
	}

	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		return type.getGeneticDecay(genome, currentModifier);
	}

	public boolean isSealed() {
		return type.isSealed();
	}

	public boolean isSelfLighted() {
		return type.isSelfLighted();
	}

	public boolean isSunlightSimulated() {
		return type.isSunlightSimulated();
	}
	
	public boolean isHellish(){
		return type.isHellish();
	}

}

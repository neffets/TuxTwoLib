package Tux2.TuxTwoLib.attributes;

import net.minecraft.server..Item;
import net.minecraft.server..NBTTagCompound;
import net.minecraft.server..NBTTagList;

import org.bukkit.craftbukkit..inventory.CraftItemStack;
import org.bukkit.craftbukkit..util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to apply {@link Attribute}s or {@link Collection}s containing Attributes to ItemStacks.
 * 
 * <b>Example code:</b>
 * <pre>
 * // Instantiate some Attributes
 * Attribute a1 = new Attribute(AttributeType.ATTACK_DAMAGE,Operation.ADD_NUMBER,20.0),
 * 		a2 = new Attribute().setType(AttributeType.KNOCKBACK_RESISTANCE).setOperation(Operation.ADD_PERCENTAGE).setAmount(0.75);
 * 
 * // Create an ItemStack with these Attributes
 * ItemStack is = new ItemStack(Material.NETHER_STAR);
 * 
 * // Apply the attributes to said ItemStack
 * List<Attribute> list = new ArrayList<>();
 * list.add(a1);
 * list.add(a2);
 * Attributes.apply(is, list, true);
 * </pre>
 * @author Ruud Verbeek
 * @see Attribute
 */

public class Attributes {
	
	/**
	 * Applies the given attribute to the given ItemStack
	 * @param original The original ItemStack to apply the attribute to
	 * @param attribute The Attribute to apply.
	 * @param replace Whether or not to remove the attributes that were already on the ItemStack
	 * @return A new ItemStack containing the attribute
	 */
	
	public static ItemStack apply(ItemStack original, Attribute attribute, boolean replace){
		try {
			if(original instanceof CraftItemStack) {
				net.minecraft.server..ItemStack stack = CraftItemStack.asNMSCopy(original);
				NBTTagCompound tag = stack.getTag();
				NBTTagList list;
				if(replace) {
					list = new NBTTagList();
				}else {
					list = tag.getList("AttributeModifiers",10);
				}
				list.add(attribute.write());
				tag.set("AttributeModifiers",list);
				stack.setTag(tag);
				return original;
			}else {
				return original;
			}
		}catch (InstantiationException ex) {
			ex.printStackTrace();
			return original;
		}catch (IllegalAccessException ex) {
			ex.printStackTrace();
			return original;
		}
	}
	
	/**
	 * Applies the given attributes to the given ItemStack
	 * @param original The original ItemStack to apply the attribute to
	 * @param attributes The Attributes to apply.
	 * @param replace Whether or not to remove the attributes that were already on the ItemStack
	 * @return A new ItemStack containing the attributes
	 */
	
	public static ItemStack apply(ItemStack original, Collection<? extends Attribute> attributes, boolean replace){
		if(attributes.size() == 0 && !replace) {
			return original;
		}
		try {
			net.minecraft.server..ItemStack stack = getMinecraftItemStack(original);
			NBTTagCompound tag = stack.getTag();
			if(tag == null) {
				tag = new NBTTagCompound();
			}
			NBTTagList list;
			if(replace) {
				list = new NBTTagList();
			}else {
				list = tag.getList("AttributeModifiers",10);
			}
			for(Attribute attribute : attributes) {
				if(attribute != null) {
					list.add(attribute.write());
				}
			}
			tag.set("AttributeModifiers",list);
			stack.setTag(tag);
			return CraftItemStack.asCraftMirror(stack);
		}catch (InstantiationException ex) {
			ex.printStackTrace();
			return original;
		}catch (IllegalAccessException ex) {
			ex.printStackTrace();
			return original;
		}
	}
	
	/**
	 * Returns a {@link List} containing the {@link Attribute}s on the given {@link ItemStack}.
	 * @param is the ItemStack to take the Attributes from
	 * @return a List containing the Attributes, or an empty list if there weren't any Attributes on the ItemStack or an error occurred.
	 */
	
	public static ArrayList<Attribute> fromStack(ItemStack is){
		try{
			net.minecraft.server..ItemStack mcis = getMinecraftItemStack(is);
			if(mcis == null) {
				return new ArrayList<Attribute>();
			}
			NBTTagCompound tag = mcis.getTag();
			if(tag == null) {
				return new ArrayList<Attribute>();
			}
			NBTTagList attributes;
			if((attributes=tag.getList("AttributeModifiers",10))==null) {
				return new ArrayList<Attribute>();
			}
			ArrayList<Attribute> list = new ArrayList<Attribute>();
			for(int i=0; i<attributes.size(); ++i){
				NBTTagCompound attribute = attributes.get(i);
				list.add(Attribute.fromTag(attribute));
			}
			return list;
		}catch(Exception ex){
			ex.printStackTrace();
			return new ArrayList<Attribute>();
		}
	}
	
	public static net.minecraft.server..ItemStack getMinecraftItemStack(ItemStack is) {
		if(!(is instanceof CraftItemStack)) {

	        Item item = CraftMagicNumbers.getItem(is.getType());

	        if (item == null) {
	            return null;
	        }

	        net.minecraft.server..ItemStack stack = new net.minecraft.server..ItemStack(item, is.getAmount(), is.getDurability());
	        
			CraftItemStack cis = CraftItemStack.asCraftMirror(stack);
			try {
				Field handle = CraftItemStack.class.getDeclaredField("handle");
				handle.setAccessible(true);
				net.minecraft.server..ItemStack mis = (net.minecraft.server..ItemStack) handle.get(cis);
				if (is.hasItemMeta()) {
		            CraftItemStack.setItemMeta(mis, is.getItemMeta());
		        }
				return mis;
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(is instanceof CraftItemStack) {
			CraftItemStack cis = (CraftItemStack) is;
			try {
				Field handle = CraftItemStack.class.getDeclaredField("handle");
				handle.setAccessible(true);
				net.minecraft.server..ItemStack mis = (net.minecraft.server..ItemStack) handle.get(cis);
				return mis;
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}

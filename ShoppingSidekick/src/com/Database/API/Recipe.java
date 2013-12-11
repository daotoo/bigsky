package com.Database.API;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Container class for recipes
 * @author Sean Cavanaugh
 *
 */
public class Recipe implements Serializable{
	
	private String Name;
	
	private String Link;
	
	private ArrayList<String> ItemsID;

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return Link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		Link = link;
	}

	/**
	 * @return the itemsID
	 */
	public ArrayList<String> getItemsID() {
		return ItemsID;
	}

	/**
	 * @param itemsID the itemsID to set
	 */
	public void setItemsID(ArrayList<String> itemsID) {
		ItemsID = itemsID;
	}

}

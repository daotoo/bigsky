package com.Database.API;
import java.sql.Date;

	/**
	 * Container class for Calendar items
	 * @author Sean Cavanaugh
	 *
	 */
	public class CalendarItem
	{
		/**
		 * Represents a food object
		 */
		private Food Food;
		
		/**
		 * Represents the date the food was entered
		 */
		private Date DateStarted;
		
		/**
		 * Represents the date the food will expire
		 */
		private Date DateExpired;
		
		
		private int ID;
		
		public int getID()
		{
			return ID;
		}
		
		public void setID(int ID)
		{
			this.ID = ID;
		}
		/**
		 * Setter method for Food
		 * @param food The food object to be set to
		 */
		public void setFood(Food food)
		{
			this.Food = food;
		}
		
		/**
		 * Setter method for the date started
		 * @param DateStarted The date object to be set to
		 */
		public void setDateStarted(Date DateStarted)
		{
			this.DateStarted = DateStarted;
		}
		
		/**
		 * Setter method for the date expired
		 * @param DateExpired The date object to be set to
		 */
		public void setDateExpired(Date DateExpired)
		{
			this.DateExpired = DateExpired;
		}
		
		/**
		 * Getter method for food
		 * @return the food object associated with this object
		 */
		public Food getFood()
		{
			return Food;
		}
		
		/**
		 * Getter method for date started
		 * @return the date started object associated with this object
		 */
		public Date getDateStarted()
		{
			return DateStarted;
		}
		
		/**
		 * Getter method for date expired
		 * @return the date expired object associated with this object
		 */
		public Date getDateExpired()
		{
			return DateExpired;
		}
	}
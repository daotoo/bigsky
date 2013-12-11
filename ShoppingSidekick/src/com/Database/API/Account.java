package com.Database.API;

import java.io.Serializable;

/**
	 * Container class for the Account table
	 * @author Sean Cavanaugh
	 *
	 */
	public class Account implements Serializable
	{
		/**
		 * Notifications for the row
		 */
		private Boolean Notifications;
		
		/**
		 * ID for the row
		 */
		private int ID;
		
		/**
		 * UserID for the row
		 */
		private String UserID;
		
		/**
		 * Password for the row
		 */
		private String Password;
		
		/**
		 * Email for the row
		 */
		private String Email;
		
		/**
		 * The name of the user's table in the database
		 */
		private String UsersTable;
		
		/**
		 * Getter method for UsersTable
		 * @return the UsersTable associated with this object
		 */
		public String getUsersTable()
		{
			return UsersTable;
		}
		
		/**
		 * Getter method for Password
		 * @return the Password associated with this object
		 */
		public String getPassword()
		{
			return Password;
		}		
		
		/**
		 * Getter method for UserID
		 * @return the UserID associated with this object
		 */
		public String getUserID()
		{
			return UserID;
		}	
		
		/**
		 * Getter method for ID
		 */
		public int getID()
		{
			return ID;
		}

		/**
		 * Getter method for email
		 * @return the email associated with this object
		 */
		public String getEmail()
		{
			return Email;
		}
		
		/**
		 * Setter for email
		 * @param Email The email to be set to
		 */
		public void setEmail(String Email)
		{
			this.Email = Email;
		}
			
		/**
		 * Setter method for UsersTable
		 * @param UsersTable The UserTable to be set to
		 */
		public void setUsersTable(String UsersTable)
		{
			this.UsersTable = UsersTable;
		}
		
		/**
		 * Setter method for Password
		 * @param Password The Password to be set to
		 */
		public void setPassword(String Password)
		{
			this.Password =  Password;
		}		
		
		/**
		 * Setter method for UsersID
		 * @param UserID The UserID to be set to
		 */
		public void setUserID(String UserID)
		{
			this.UserID = UserID;
		}	
		
		/**
		 * Setter method for ID
		 * @param ID The ID to be set to
		 */
		public void setID(int ID)
		{
			this.ID = ID;
		}

		/**
		 * @return the notifications
		 */
		public Boolean getNotifications() 
		{
			return Notifications;
		}

		/**
		 * @param notifications the notifications to set
		 */
		public void setNotifications(Boolean notifications) 
		{
			this.Notifications = notifications;
		}
}
	
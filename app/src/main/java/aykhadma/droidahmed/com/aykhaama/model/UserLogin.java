package aykhadma.droidahmed.com.aykhaama.model;

import java.io.Serializable;

public class UserLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Result;
	private String ID;
	private String FullName;
	private String UserName;
	private String Email;
	private String Picture;
 	private String City;
	private String Mobile;
	private String token;
	private String Country;


	public UserLogin() {
	}



	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public UserLogin(String result, String ID, String fullName,
					 String userName, String email, String picture,
					 String City, String mobile, String token) {
		Result = result;
		this.ID = ID;
		FullName = fullName;
		UserName = userName;
		Email = email;
		Picture = picture;
 		this.City = City;
		Mobile = mobile;
		this.token = token;
	}

	/**
	 * 
	 * @return The ID
	 */
	public String getID() {
		return ID;
	}

	/**
	 * 
	 * @param ID
	 *            The ID
	 */
	public void setID(String ID) {
		this.ID = ID;
	}

	/**
	 * 
	 * @return The FullName
	 */
	public String getFullName() {
		return FullName;
	}

	/**
	 * 
	 * @param FullName
	 *            The FullName
	 */
	public void setFullName(String FullName) {
		this.FullName = FullName;
	}

	/**
	 * 
	 * @return The UserName
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * 
	 * @param UserName
	 *            The UserName
	 */
	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	/**
	 * 
	 * @return The Email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * 
	 * @param Email
	 *            The Email
	 */
	public void setEmail(String Email) {
		this.Email = Email;
	}

	/**
	 * 
	 * @return The Picture
	 */
	public String getPicture() {
		return Picture;
	}

	/**
	 * 
	 * @param Picture
	 *            The Picture
	 */
	public void setPicture(String Picture) {
		this.Picture = Picture;
	}

	/**
	 * 
	 * @return The City
	 */
	public String getCity() {
		return City;
	}

	/**
	 * 
	 * @param City
	 *            The State
	 */
	public void setCity(String City) {
		this.City = City;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}


	public void setAuthPassword(String AuthPassword) {
		AuthPassword = AuthPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

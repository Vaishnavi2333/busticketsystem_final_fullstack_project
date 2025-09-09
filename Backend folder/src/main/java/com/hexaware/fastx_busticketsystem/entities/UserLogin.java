package com.hexaware.fastx_busticketsystem.entities;

import jakarta.persistence.*;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:07-Aug-2025
Description:User login Entity Class*/
@Entity
@Table(name = "user_login")
public class UserLogin {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "user_id")
    private int userId;

	@Column(name = "username", nullable = false, unique = true)
    private String username;

	 @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "userLogin", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private UserData userData;

    public UserLogin() {
    }

    public UserLogin(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
    

  

    public UserLogin(int userId, String username, String password, UserData userData) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userData = userData;
	}

	public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}


package com.ecommerce.Service;

import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserExcepion;
	
	public User findUserProfileByJwt(String jwt) throws UserExcepion;

}

package com.ecommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Config.JwtProvider;
import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.User;
import com.ecommerce.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long userId) throws UserExcepion {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent())
		{
			return user.get();
		}
		
		
		throw new UserExcepion("User not found with id : "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserExcepion {
		
		String email=jwtProvider.getEmailFromToken(jwt);
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserExcepion("User not found with email : "+email);
		}
		return user;
	}

}

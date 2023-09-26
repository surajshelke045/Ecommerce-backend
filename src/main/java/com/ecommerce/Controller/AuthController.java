package com.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Config.JwtProvider;
import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.Cart;
import com.ecommerce.Model.User;
import com.ecommerce.Repository.UserRepository;
import com.ecommerce.Request.LoginRequest;
import com.ecommerce.Response.AuthResponse;
import com.ecommerce.Service.CartService;
import com.ecommerce.Service.CustomUserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserServiceImpl customUserServiceImpl;
	
	@Autowired
	private CartService cartService;
	
	
	@PostMapping("/signin")
	public  ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest ) throws UserExcepion{
		
		
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		
		Authentication authentication=authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	String token=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse( token,"Signin Success");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
	}
	
	private Authentication authenticate(String username, String password) {
	
		UserDetails userDetails=customUserServiceImpl.loadUserByUsername(username);
		
		if (userDetails==null)
		{
			throw new BadCredentialsException("Invalid Usename");
			
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword()))
		{
			throw new BadCredentialsException("Invalid Password..");
		}
			
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}

	@PostMapping("/signup")
	public  ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserExcepion{
		
		String email=user.getEmail();
		String password=user.getPassword();
		String fname=user.getFirstName();
		String lname=user.getLastName();
		String phone=user.getPhone();
		
		
		User isEmailExist=userRepository.findByEmail(email);
		
		if(isEmailExist!=null)
		{
			throw new UserExcepion("This Email is Already Exist..! try with another Email");
		}
		
		User createdUser = new User();
		
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		createdUser.setFirstName(fname);
		createdUser.setLastName(lname);
		createdUser.setPhone(phone);
		
		User savedUser=userRepository.save(createdUser);
		
		Cart cart=cartService.createCart(savedUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse( token,"Signup Success");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
		
		
	}

}

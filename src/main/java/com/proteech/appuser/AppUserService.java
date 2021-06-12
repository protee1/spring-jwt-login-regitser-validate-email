package com.proteech.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proteech.registration.token.ConfirmationToken;
import com.proteech.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor

public class AppUserService implements UserDetailsService {

	@Autowired
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private ConfirmationTokenService confirmationTokenService;
	private final static String USER__NOT_FOUND_MSG="User with email % not found";
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException(String.format(USER__NOT_FOUND_MSG, email)));
	}
	public String signUp(AppUser appUser) {
	boolean userExist=	appUserRepository.findByEmail(appUser.getEmail()).isPresent();
	if(userExist) {
		throw new IllegalStateException("Email alredy taken");
	}
	String encodedPassword=bCryptPasswordEncoder.encode(appUser.getPassword());
	appUser.setPassword(encodedPassword);
	appUserRepository.save(appUser);
	String token=UUID.randomUUID().toString();
	ConfirmationToken confirmationToken= new ConfirmationToken(
			token,
			LocalDateTime.now(),
			LocalDateTime.now().plusMinutes(15),
			appUser
			
			);
	confirmationTokenService.saveConfirmationToken(confirmationToken);
//TODO: send email
		return token;
		
	}
	public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

}

package apiUser.service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import apiUser.entity.Phone;
import apiUser.entity.User;
import apiUser.repository.PhoneRepository;
import apiUser.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PhoneRepository phonesRepository;
	
	@Override
	public Optional<User> insertUser(User user, Map<String, String> HEADER) {
		logger.info("inicia servicio que guarda en BD el user");
		validarInsert(user.getEmail(), user.getPassword());
		
		user.setToken(HEADER.get("authorization").substring(7));
		user.setCreated(new Date());
		user.setLast_login(user.getCreated());
		user.setIsactive(true);
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		userRepository.save(user);
		logger.info("se guarda el user");
		
		for( Phone phone :  user.getPhones()) {
			phone.setIdUser(user.getId());
		}
		
		phonesRepository.saveAll(user.getPhones());
		logger.info("se guarda los datos de telefono");
		Optional<User> userFinal = userRepository.findById(user.getId());
		
		return userFinal;
		
	}
	
	private void validarInsert(String mail, String password) {
		
		if(!mail.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
			throw new RuntimeException("formato de email no valido");
		}else if(emailExists(mail)) {
			throw new RuntimeException("email ya registrado");
		}else if(password.matches("^(?=.*?[0-9].*?[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{7,}$")) {
			throw new RuntimeException("el password debe contener letras minúsculas,al menos una mayuscula y dos numeros");
		}
	}

	private Boolean emailExists(String mail) {
		User user = userRepository.findByEmail(mail);
		return (user != null);
	}
	

}

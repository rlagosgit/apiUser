package apiUser.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import apiUser.entity.Phone;
import apiUser.entity.User;
import apiUser.repository.PhoneRepository;
import apiUser.repository.UserRepository;

public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Spy
	private UserRepository userRepository;
	
	@Spy
	private PhoneRepository phonesRepository;

	
	@Mock
	private User userMock;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void insertUserTestOK() {
		
		//prepare
		Phone phone = new Phone();
		phone.setCitycode(1);
		phone.setContrycode(12);
		phone.setId("123");
		phone.setIdUser("1234");
		phone.setNumber(12345678);
		
		List<Phone> phoneList = new ArrayList<Phone>();
		phoneList.add(phone);
		
		User user = new User();
		user.setEmail("correo@dominio.com");
		user.setPassword("Aaa12");
		user.setToken("qqqqqqqq");
		user.setCreated(new Date());
		user.setIsactive(true);
		user.setPhones(phoneList);
				
		Mockito.doReturn(null).when(userRepository).findByEmail(Mockito.anyString());
		user.setId("123456789");
		Mockito.doReturn(user).when(userRepository).save(Mockito.any());
		
		Mockito.doReturn(new ArrayList<Object>()).when(phonesRepository).saveAll(Mockito.anyIterable());
		
		Mockito.doReturn(Optional.of(user)).when(userRepository).findById(Mockito.anyString());
		

		Map<String, String> headersMock = new HashMap<String, String>();
		headersMock.put("authorization", "zzzzzzzzzz");
		//act
		Optional<User> userFinal = userServiceImpl.insertUser(user, headersMock);
		
		//assert
		assertNotNull(userFinal);
	}
}

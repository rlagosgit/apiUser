package apiUser.controller;

import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;

import apiUser.entity.User;
import apiUser.service.UserServiceImpl;

public class UserControllerTest {
	
	@InjectMocks
	private UserController userController;
	
	@Spy
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private User userMock;
	
	
	Optional<User> optionalUserMock;
	
	@Mock
	Map<String, String> headersMock;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void insertUserTestOK(){
		//prepare
		Mockito.doReturn(optionalUserMock).when(userServiceImpl).insertUser(Mockito.any(User.class), Mockito.anyMap());
		//act
		ResponseEntity<Map<String, Object>> response = userController.insertUser(userMock, headersMock);
		//assert
		assertNotNull(response);
	}

}

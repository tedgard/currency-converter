package services;

import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.edgardndouna.services.UserService;
import com.edgardndouna.services.UserServiceImpl;

import domain.User;

public class UserServiceTest {

	private UserService userService;
	
	@Before
	public void setupUserService(){
		 userService = new UserServiceImpl();
	}
	
	@Test
	public void shouldAuthenticateTheUser(){
		
		User user = userService.authenticateUser("john.doe@example.com", "test1234");
		
		assertThat(user, Matchers.notNullValue());
	}
	
	@Test
	public void shouldNotAuthenticateTheUser(){
		
		User user = userService.authenticateUser("jefferson@example.com", "qwerty123");
		
		assertThat(user, Matchers.nullValue());
	}
	
	@Test
	public void shouldReturnAnExistingUser(){
		
		User user = userService.getUserById(1);
		
		assertThat(user, Matchers.notNullValue());
		assertThat(user, Matchers.hasProperty("id", Matchers.equalTo(1)));
	}
	
	@Test
	public void shouldNotReturnAnyUser(){
		
		User user = userService.getUserById(100);
		
		assertThat(user, Matchers.nullValue());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldSaveNewUser(){
		User user = new User(null, "Jane Roe", "jane.roe@example.com", "test1234", "1988-09-01", "1267 Jackson St", "80000", "Amiens", "France");
		
		User userSaved = userService.saveOrUpdate(user);
		
		assertThat(userSaved, Matchers.notNullValue());
		assertThat(userSaved, Matchers.allOf(
				Matchers.hasProperty("fullName", Matchers.equalTo(user.getFullName())),
				Matchers.hasProperty("email", Matchers.equalTo(user.getEmail())),
				Matchers.hasProperty("password", Matchers.equalTo(user.getPassword())),
				Matchers.hasProperty("dateOfBirth", Matchers.equalTo(user.getDateOfBirth())),
				Matchers.hasProperty("address", Matchers.equalTo(user.getAddress())), 
				Matchers.hasProperty("zipCode", Matchers.equalTo(user.getZipCode())),
				Matchers.hasProperty("city", Matchers.equalTo(user.getCity())),
				Matchers.hasProperty("country", Matchers.equalTo(user.getCountry())))
		);
	}
	
	@Test
	public void shouldUpdateAnExistingUser(){
		User user = userService.getUserById(1);
			user.setFullName("George Thompson");
			user.setCity("");
		
		User userSaved = userService.saveOrUpdate(user);
		
		assertThat(userSaved, Matchers.notNullValue());
		assertThat(userSaved, Matchers.equalTo(user));
	}
	
	@Test
	public void shouldTellMeThatEmailIsValid(){
		
		boolean result = userService.isValidEmailAddress("john.doe@example.com");
		
		assertThat(result, Matchers.equalTo(true));
	}
	
	
	@Test
	public void shouldTellMeThatDateOfBirthIsNotReasonable(){
		
		boolean result = userService.isReasonableDateOfBirth(LocalDate.parse("2020-05-10"));
		
		assertThat(result, Matchers.equalTo(false));
	}
	
	@Test
	public void shouldTellMeThatDateOfBirthIsReasonable(){
		
		boolean result = userService.isReasonableDateOfBirth(LocalDate.parse("1987-03-02"));
		
		assertThat(result, Matchers.equalTo(true));
	}
	
	
	@Test
	public void shouldTellMeThatEmailIsNotValid(){
		
		boolean result01 = userService.isValidEmailAddress("john.doe@example");
		boolean result02 = userService.isValidEmailAddress("john.doe");
		boolean result03 = userService.isValidEmailAddress("john.doe@");
		boolean result04 = userService.isValidEmailAddress("john@.com");
		boolean result05 = userService.isValidEmailAddress("@example.com");
		boolean result06 = userService.isValidEmailAddress("john.doe.example.com");
		boolean result07 = userService.isValidEmailAddress("john.doe@example@example.com");
		boolean result08 = userService.isValidEmailAddress("john..doe@example.com");
		boolean result09 = userService.isValidEmailAddress("john.doe@example.");
		boolean result10 = userService.isValidEmailAddress("john.doe@example..com");
		boolean result11 = userService.isValidEmailAddress("john.doe@111.222.333.44444");
		
		assertThat(result01, Matchers.equalTo(false));
		assertThat(result02, Matchers.equalTo(false));
		assertThat(result03, Matchers.equalTo(false));
		assertThat(result04, Matchers.equalTo(false));
		assertThat(result05, Matchers.equalTo(false));
		assertThat(result06, Matchers.equalTo(false));
		assertThat(result07, Matchers.equalTo(false));
		assertThat(result08, Matchers.equalTo(false));
		assertThat(result09, Matchers.equalTo(false));
		assertThat(result10, Matchers.equalTo(false));
		assertThat(result11, Matchers.equalTo(false));
	}
	
	@Test
	public void shouldTellMeThatEmailAlreadyRegistered(){
		
		boolean result = userService.isEmailAlreadyRegistered("john.doe@example.com");
		
		assertThat(result, Matchers.equalTo(true));
	}
	
	@Test
	public void shouldTellMeThatEmailIsNotRegistered(){
		
		boolean result = userService.isEmailAlreadyRegistered("raven.jackson@example.com");
		
		assertThat(result, Matchers.equalTo(false));
	}

	
}

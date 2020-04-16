/*
import dto.UserDto;
import entities.User;
import exceptions.DeletingEmployeeException;
import exceptions.LoginInUseException;
import exceptions.NoSuchUserException;
import impl.UserCrudServiceImpl;
import interfaces.UserCrudService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserCrudServiceImplTest {
  @InjectMocks
  UserCrudService employeeService = new UserCrudServiceImpl();

    @Mock
    UserRepository userRepository;
    private User expectedUser;
  private List<User> expectedUsers;

  @Before
  public void setUp() {

      expectedUser =
              new User(1, "Ivanov", "Ivan", "Ivanovich", "cashier", "CASHIER", "123456");

      expectedUsers = new ArrayList<>();
      expectedUsers.add(
              new User(1, "Ivanov", "Ivan", "Ivanovich", "cashier", "ROLE_CASHIER", "123456"));
      expectedUsers.add(
              new User(2, "Petrov", "Vasiliy", "Alexandrovich", "admin", "ROLE_ADMIN", "123456"));
  }

  @Test
  public void shouldReturnEmptyArrayListWhenThereAreNoAnyEmployees() {
      when(userRepository.findAll()).thenReturn(new ArrayList<>());
      //Assert.assertEquals(employeeService.getAll(), new ArrayList<>());
      Assert.assertTrue(employeeService.getAll().isEmpty());
  }

  @Test
  public void shouldReturnEmployees() {
      when(userRepository.findAll()).thenReturn(expectedUsers);

      List<UserDto> actual = new ArrayList<>();
      actual.add(
              new UserDto(1, "Ivanov", "Ivan", "Ivanovich", "cashier", "ROLE_CASHIER", "123456"));
      actual.add(
              new UserDto(2, "Petrov", "Vasiliy", "Alexandrovich", "admin", "ROLE_ADMIN", "123456"));

      Assert.assertEquals(employeeService.getAll(), actual);
  }

  @Test(expected = NoSuchUserException.class)
  public void shouldThrowNoSuchEmployeeExceptionWhenThereIsNoEmployeeWithThisId() {
      when(userRepository.findOne(anyInt())).thenReturn(null);
      employeeService.getById(1);
  }

  @Test(expected = NoSuchUserException.class)
  public void shouldThrowNoSuchEmployeeExceptionWhenThereIsNoEmployeeWithThisLogin() {
      when(userRepository.findByLogin(anyString())).thenReturn(null);
      employeeService.getByLogin("");
  }

  @Test
  public void shouldReturnEmployeeById() {
    when(userRepository.findOne(1)).thenReturn(expectedUser);
      UserDto actual =
              new UserDto(1, "Ivanov", "Ivan", "Ivanovich", "cashier", "CASHIER", "123456");

      Assert.assertEquals(employeeService.getById(1), actual);

    verify(userRepository, times(1)).findOne(1);
  }

  @Test
  public void shouldReturnCreatedEmployee() {
      when(userRepository.saveAndFlush(expectedUser)).thenReturn(expectedUser);
      UserDto actual =
              new UserDto(1, "Ivanov", "Ivan", "Ivanovich", "cashier", "CASHIER", "123456");
      Assert.assertEquals(employeeService.create(expectedUser.convertToDto()), actual);
      verify(userRepository, times(1)).saveAndFlush(expectedUser);
  }

  @Test(expected = NoSuchUserException.class)
  public void shouldThrowNoSuchEmployeeExceptionWhenCreateNullEmployee() {
      employeeService.create(null);
  }

  @Test(expected = LoginInUseException.class)
  public void shouldThrowLoginInUseExceptionWhenCreateEmployeeWithLoginWhichHasOtherEmployee() {
      when(userRepository.findByLogin(anyString())).thenReturn(expectedUser);
      employeeService.create(expectedUser.convertToDto());
  }

  @Test(expected = NoSuchUserException.class)
  public void shouldThrowNoSuchEmployeeExceptionWhenUpdateEmployeeByNull() {
      employeeService.update(null);
  }

  @Test(expected = NoSuchUserException.class)
  public void shouldThrowNoSuchEmployeeExceptionWhenThereIsNoSuchUpdatedEmployee() {
      when(userRepository.findOne(anyInt())).thenReturn(null);
      employeeService.update(expectedUser.convertToDto());
  }

  @Test(expected = LoginInUseException.class)
  public void shouldThrowLoginInUseExceptionWhenUpdateEmployeeWithLoginWhichHasOtherEmployee() {
      when(userRepository.findOne(anyInt())).thenReturn(expectedUser);
      when(userRepository.findByLogin(anyString())).thenReturn(new User());
      employeeService.update(expectedUser.convertToDto());
  }

  @Test
  public void shouldReturnUpdatedEmployee() {
      when(userRepository.findOne(anyInt())).thenReturn(expectedUser);
      when(userRepository.saveAndFlush(expectedUser)).thenReturn(expectedUser);
      UserDto actual =
              new UserDto(1, "Ivanov", "Ivan", "Ivanovich", "cashier", "CASHIER", "123456");
      Assert.assertEquals(employeeService.update(expectedUser.convertToDto()), actual);
      verify(userRepository, times(1)).saveAndFlush(expectedUser);
  }

  @Test
  @WithMockUser(username = "administrator", password = "123456", authorities = "ROLE_ADMIN")
  public void testDeleteEmployee() {
    when(userRepository.findOne(1)).thenReturn(expectedUser);
      Mockito.doNothing().when(userRepository).delete(expectedUser);
      employeeService.delete(expectedUser.convertToDto());
      verify(userRepository, times(1)).delete(expectedUser);
  }

  @Test(expected = DeletingEmployeeException.class)
  @WithMockUser(username = "admin", password = "123456", authorities = "ROLE_ADMIN")
  public void shouldThrowDeletingEmployeeExceptionWhenDeleteYourself() {
      when(userRepository.findOne(anyInt())).thenReturn(expectedUsers.get(1));
      employeeService.delete(expectedUsers.get(1).convertToDto());
  }

  @Test(expected = NoSuchUserException.class)
  public void shouldThrowNoSuchEmployeeExceptionWhenThereIsNoSuchEmployeeToDelete() {
      when(userRepository.findOne(anyInt())).thenReturn(null);
      employeeService.delete(expectedUser.convertToDto());
  }

  @Test(expected = NoSuchUserException.class)
  public void shouldThrowNoSuchEmployeeExceptionWhenDeleteNullEmployee() {
      employeeService.delete(null);
  }
}
*/

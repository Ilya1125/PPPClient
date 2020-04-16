package impl;

import dto.UserDto;
import entities.User;
import exceptions.DeletingEmployeeException;
import exceptions.LoginInUseException;
import exceptions.NoSuchUserException;
import interfaces.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCrudServiceImpl implements UserCrudService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public List<UserDto> getAll() {
      return userRepository.findAll().stream().map(User::convertToDto).collect(Collectors.toList());
  }

    @Override
    public UserDto getById(int id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new NoSuchUserException(id);
        }
        return user.convertToDto();
    }

    @Override
    public UserDto getByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new NoSuchUserException("User not found");
        }
        return user.convertToDto();
    }

    @Override
    public UserDto create(UserDto user) {
        if (user == null) {
            throw new NoSuchUserException("User is null");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new LoginInUseException("Login is already in use");
        }
        return userRepository.saveAndFlush(user.convertToEntity()).convertToDto();
    }

    @Override
    public UserDto update(UserDto user) {
        if (user == null) {
            throw new NoSuchUserException("User is null");
        }
        if (userRepository.findOne(user.getUserId()) == null) {
            throw new NoSuchUserException(user.getUserId());
        }
        User us = userRepository.findByLogin(user.getLogin());
        if (us != null && us.getUserId() != user.getUserId()) {
            throw new LoginInUseException("Login is already in use");
        }
    return userRepository.saveAndFlush(user.convertToEntity()).convertToDto();
  }

    @Override
    public void delete(UserDto user) {

        if (user == null) {
            throw new NoSuchUserException("User is null");
        }
        if (userRepository.findOne(user.getUserId()) == null) {
            throw new NoSuchUserException(user.getUserId());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        if (user.getLogin().equals(userDetails.getUsername())) {
      throw new DeletingEmployeeException("You can't delete yourself");
    }
    userRepository.delete(user.convertToEntity());
  }
}

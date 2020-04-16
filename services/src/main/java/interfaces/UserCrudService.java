package interfaces;

import dto.UserDto;

public interface UserCrudService extends CrudService<UserDto> {
    UserDto getByLogin(String login);
}

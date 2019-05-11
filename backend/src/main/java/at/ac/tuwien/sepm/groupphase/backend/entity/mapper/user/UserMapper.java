package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.user;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = SimpleUserMapper.class)
public interface UserMapper {

  User userDtoToUser(UserDto user);

  UserDto userToUserDto(User user);
}

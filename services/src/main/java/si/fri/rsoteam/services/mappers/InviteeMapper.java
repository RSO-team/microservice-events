package si.fri.rsoteam.services.mappers;

import si.fri.rsoteam.lib.dtos.InviteeDto;
import si.fri.rsoteam.lib.dtos.UserDto;
import si.fri.rsoteam.models.entities.InviteeEntity;

public class InviteeMapper {
    public static InviteeDto entityToDto(InviteeEntity et) {
        InviteeDto userDto = new InviteeDto();
        userDto.userId = et.getUserId();
        return userDto;
    }

    public static InviteeDto userDtoToDto(UserDto ud) {
        InviteeDto userDto = new InviteeDto();
        userDto.name = ud.name + " " + ud.surname;
        userDto.userId = ud.id;
        return userDto;
    }

    public static InviteeEntity dtoToEntity(InviteeDto userDto) {
        InviteeEntity userEntity = new InviteeEntity();
        userEntity.setUserId(userDto.userId);
        return  userEntity;
    }
}

package si.fri.rsoteam.services.mappers;

import si.fri.rsoteam.lib.dtos.InviteeDto;
import si.fri.rsoteam.models.entities.InviteeEntity;

public class InviteeMapper {
    public static InviteeDto entityToDto(InviteeEntity et) {
        InviteeDto userDto = new InviteeDto();
        userDto.id = et.getId();
        return userDto;
    }

    public static InviteeEntity dtoToEntity(InviteeDto userDto) {
        InviteeEntity userEntity = new InviteeEntity();
        userEntity.setUserId(userDto.id);
        return  userEntity;
    }
}

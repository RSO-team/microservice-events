package si.fri.rsoteam.models.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Converter(autoApply = true)
public class InviteesAtributeConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        return attribute.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }
}

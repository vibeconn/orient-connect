package com.vibeconn.mappers;

import com.vibeconn.PersonView;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Map;

@Mapper(componentModel = "cdi",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MapToPersonMapper{
    PersonView fromMap(Map<String,String> map);
}

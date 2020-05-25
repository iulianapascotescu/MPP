package ro.ubb.catalog.core.converter;

import ro.ubb.catalog.core.model.BaseEntity;
import ro.ubb.catalog.core.dto.BaseDto;

public interface Converter<Model extends BaseEntity<Integer>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}


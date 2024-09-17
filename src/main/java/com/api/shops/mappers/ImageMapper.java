package com.api.shops.mappers;

import com.api.shops.dto.ImageDTO;
import com.api.shops.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDTO entityToDTO(Image image);
}

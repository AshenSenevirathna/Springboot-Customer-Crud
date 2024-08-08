package com.springbootacademy.batch7newpos.util.mappers;

import com.springbootacademy.batch7newpos.dto.response.ItemGetResponseDTO;
import com.springbootacademy.batch7newpos.entity.Item;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    List<ItemGetResponseDTO> entityListToDtoList(List<Item> items);
    //ItemGetResponseDTO entityToDto(Item item);
    //Item dtoToEntity(ItemSaveRequestDTO itemSaveRequestDTO);

    List<ItemGetResponseDTO>ListDTOToPage(Page<Item> items);

    List<ItemGetResponseDTO> pageToList(Page<Item> getAllItemsByPaginated);
}

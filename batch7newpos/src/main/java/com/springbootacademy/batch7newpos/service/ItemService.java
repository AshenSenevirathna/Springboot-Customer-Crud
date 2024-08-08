package com.springbootacademy.batch7newpos.service;

import com.springbootacademy.batch7newpos.dto.paginated.PaginatedResponseItemDTO;
import com.springbootacademy.batch7newpos.dto.request.ItemSaveRequestDTO;
import com.springbootacademy.batch7newpos.dto.response.ItemGetResponseDTO;

import java.util.List;

public interface ItemService {
    public String saveItem(ItemSaveRequestDTO itemSaveRequestDTO);

    List<ItemGetResponseDTO> getItemByNameAndStatus(String itemName);

    List<ItemGetResponseDTO> getItemByNameAndStatusByMapStruct(String itemName);

    List<ItemGetResponseDTO> getItemByActiveStatus(boolean activeStatus);

    PaginatedResponseItemDTO getItemByActiveStatusWithPaginated(boolean activeStatus, int page, int size);

    PaginatedResponseItemDTO getAllItemsPaginated(int page, int size);

    PaginatedResponseItemDTO getAllActiveItemsPaginated(int page, int size, boolean activeState);
}

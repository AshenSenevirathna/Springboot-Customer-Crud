package com.springbootacademy.batch7newpos.service.impl;

import com.springbootacademy.batch7newpos.dto.paginated.PaginatedResponseItemDTO;
import com.springbootacademy.batch7newpos.dto.request.ItemSaveRequestDTO;
import com.springbootacademy.batch7newpos.dto.response.ItemGetResponseDTO;
import com.springbootacademy.batch7newpos.entity.Item;
import com.springbootacademy.batch7newpos.exception.NotFoundException;
import com.springbootacademy.batch7newpos.repo.ItemRepo;
import com.springbootacademy.batch7newpos.service.ItemService;
import com.springbootacademy.batch7newpos.util.mappers.ItemMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceIMPL implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public String saveItem(ItemSaveRequestDTO itemSaveRequestDTO) {
//        Item item = new Item(
//                1,
//                itemSaveRequestDTO.getItemName(),
//                itemSaveRequestDTO.getMeasuringUnitType(),
//                itemSaveRequestDTO.getBalanceQty(),
//                itemSaveRequestDTO.getSupplierPrice(),
//                itemSaveRequestDTO.getSellingPrice(),
//                true
//        );
//        itemRepo.save(item);
//        return itemSaveRequestDTO.getItemName();

        Item item = modelMapper.map(itemSaveRequestDTO,Item.class);
        //Item item = itemMapper.dtoToEntity(itemSaveRequestDTO);
        if (!itemRepo.existsById(item.getItemId())){
            itemRepo.save(item);
            return item.getItemId()+" saved successfully!";
        }else {
            throw new DuplicateKeyException("Already Added!");
        }

    }

    @Override
    public List<ItemGetResponseDTO> getItemByNameAndStatus(String itemName) {

        boolean b = true;
        List<Item> items = itemRepo.findAllByItemNameEqualsAndActiveStateEquals(itemName,b);
        if (items.size()>0){
            List<ItemGetResponseDTO> itemGetResponseDTOS = modelMapper.map(items,new TypeToken<List<ItemGetResponseDTO>>(){
            }.getType());
            return itemGetResponseDTOS;
        }else {
            throw new RuntimeException("Item is not active");
        }
    }

    @Override
    public List<ItemGetResponseDTO> getItemByNameAndStatusByMapStruct(String itemName) {
        boolean b = true;
        List<Item> items = itemRepo.findAllByItemNameEqualsAndActiveStateEquals(itemName,b);
        if (items.size()>0){
            List<ItemGetResponseDTO> itemGetResponseDTOS = itemMapper.entityListToDtoList(items);
            return itemGetResponseDTOS;
        }else {
            throw new RuntimeException("Item is not active");
        }
    }

    @Override
    public List<ItemGetResponseDTO> getItemByActiveStatus(boolean activeStatus) {
        List<Item> items = itemRepo.findAllByActiveStateEquals(activeStatus);
        if (items.size()>0){
            List<ItemGetResponseDTO> itemGetResponseDTOS = itemMapper.entityListToDtoList(items);
            return itemGetResponseDTOS;
        }else {
            throw new NotFoundException("Item is not active");
        }
    }

    @Override
    public PaginatedResponseItemDTO getItemByActiveStatusWithPaginated(boolean activeStatus, int page, int size) {
        Page<Item> items = itemRepo.findAllByActiveStateEquals(activeStatus, PageRequest.of(page, size));
        if (items.getSize()<1){
            throw new NotFoundException("No Data");
        }
        PaginatedResponseItemDTO paginatedResponseItemDTO = new PaginatedResponseItemDTO(
                itemMapper.ListDTOToPage(items),
                itemRepo.countAllByActiveStateEquals(activeStatus)
        );
        return paginatedResponseItemDTO;
    }

    @Override
    public PaginatedResponseItemDTO getAllItemsPaginated(int page, int size) {
        Page<Item> getAllItemsByPaginated = itemRepo.findAll(PageRequest.of(page, size));
        return new PaginatedResponseItemDTO(
                itemMapper.pageToList(getAllItemsByPaginated),
                itemRepo.count()
        );
    }

    @Override
    public PaginatedResponseItemDTO getAllActiveItemsPaginated(int page, int size, boolean activeState) {
        Page<Item> getAllActiveItemsByPaginated = itemRepo.findAllByActiveStateEquals(activeState,PageRequest.of(page, size));
        return new PaginatedResponseItemDTO(
                itemMapper.pageToList(getAllActiveItemsByPaginated),
                itemRepo.count()
        );
    }
}

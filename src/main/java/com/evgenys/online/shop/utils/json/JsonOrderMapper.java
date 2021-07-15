package com.evgenys.online.shop.utils.json;

import com.evgenys.online.shop.persistence.entities.Order;
import com.evgenys.online.shop.persistence.entities.StorePoint;
import com.evgenys.online.shop.utils.converter.OrderConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonOrderMapper {
    private final ObjectMapper mapperJson;
    private final OrderConverter orderConverter;

    public String objectToJsonString(Order order, StorePoint storePoint){
        String jsonStringDeliveryOrderDto = "";
        try {
            jsonStringDeliveryOrderDto = mapperJson.writeValueAsString(orderConverter.convertToDeliveryOrderDto(order,storePoint));
        } catch (JsonProcessingException e) {
            log.error("Failed to map DeliveryOrderDto to a string",e);
        }
        return jsonStringDeliveryOrderDto;
    }

}

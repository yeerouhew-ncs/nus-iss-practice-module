package tbs.tbsapi.service;

import tbs.tbsapi.dto.AddOrderDto;
import tbs.tbsapi.vo.response.AddOrderResponse;


public interface OrderService {
    public AddOrderResponse addOrder(AddOrderDto addOrderDto);
}

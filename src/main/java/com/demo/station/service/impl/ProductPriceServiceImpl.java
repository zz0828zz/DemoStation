package com.demo.station.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.mapper.ProductPriceMapper;
import com.demo.station.pojo.ProductPrice;
import com.demo.station.service.ProductPriceService;
import org.springframework.stereotype.Service;


/**
 * @author : lipb
 * @date : 2021-01-05 16:25
 */
@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper,ProductPrice> implements ProductPriceService {
}

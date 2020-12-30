package com.demo.station.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.mapper.ProductMapper;
import com.demo.station.mapper.ProductTypeMapper;
import com.demo.station.pojo.Product;
import com.demo.station.pojo.ProductType;
import com.demo.station.service.ProductService;
import com.demo.station.service.ProductTypeService;
import org.springframework.stereotype.Service;

/**
 * @Author lipb
 **/
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {
}

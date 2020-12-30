package com.demo.station.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.mapper.ProductMapper;
import com.demo.station.pojo.Product;
import com.demo.station.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @Author lipb
 **/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {
}

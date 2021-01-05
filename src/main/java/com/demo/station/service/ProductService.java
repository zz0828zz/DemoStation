package com.demo.station.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.station.model.vo.SaveOrUpdateProductVO;
import com.demo.station.pojo.Product;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductService extends IService<Product> {
    boolean saveOrUpdateProduct(SaveOrUpdateProductVO productVO);
}

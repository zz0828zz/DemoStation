package com.demo.station.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.config.jwt.UserUtils;
import com.demo.station.mapper.ProductMapper;
import com.demo.station.model.vo.SaveOrUpdateProductVO;
import com.demo.station.pojo.Product;
import com.demo.station.pojo.ProductPrice;
import com.demo.station.service.ProductPriceService;
import com.demo.station.service.ProductService;
import com.demo.station.utils.CopyUtils;
import com.demo.station.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author lipb
 **/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {
    @Autowired
    private ProductPriceService productPriceService;


    @Transactional
    @Override
    public boolean saveOrUpdateProduct(SaveOrUpdateProductVO productVO) {
        try {
            Product product = CopyUtils.copyPojo(productVO, Product.class);
            product.setCreateTime(new Date());
            if (productVO.getPrice()!=null){
                String sysUserName = UserUtils.getUser(); //获取当前用户账号
                Long productId = product.getId(); //产品id
                //根据用户账号和产品id   判断产品价格是否存在      如果存在则更新不存在则新增
                QueryWrapper<ProductPrice> priceQueryWrapper = new QueryWrapper<>();
                priceQueryWrapper.eq("product_id",productId)
                        .eq("user_name",sysUserName);
                ProductPrice productPrice = productPriceService.getOne(priceQueryWrapper);
                productPrice.setUserName(sysUserName);
                productPrice.setProductId(productId);
                productPrice.setPrice(productVO.getPrice());
                productPriceService.saveOrUpdate(productPrice);
            }
           return this.saveOrUpdate(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

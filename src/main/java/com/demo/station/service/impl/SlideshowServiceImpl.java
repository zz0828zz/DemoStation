package com.demo.station.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.mapper.ProductTypeMapper;
import com.demo.station.mapper.SlideshowMapper;
import com.demo.station.pojo.ProductType;
import com.demo.station.pojo.Slideshow;
import com.demo.station.service.ProductTypeService;
import com.demo.station.service.SlideshowService;
import org.springframework.stereotype.Service;

/**
 * @Author lipb
 **/
@Service
public class SlideshowServiceImpl extends ServiceImpl<SlideshowMapper, Slideshow> implements SlideshowService {
}

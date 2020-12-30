package com.demo.station.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.station.pojo.Slideshow;
import com.demo.station.service.SlideshowService;
import com.demo.station.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("/slideshow")
@Api(tags = "轮播图管理")
public class SlideshowController {

    @Autowired
    private SlideshowService slideshowService;

    @GetMapping(value = "/getSlideshowAllList")
    @ApiOperation("获取全部轮播图")
    public Result<List<Slideshow>> getSlideshowAllList() {
        List<Slideshow> list = slideshowService.list();
        return Result.data(list);
    }

    @PostMapping(value = "/addSlideshow")
    @ApiOperation("添加轮播图")
    @ApiImplicitParam(name = "url", value = "轮播图地址", required = true)
    public Result addSlideshow(String url) {

        Slideshow slideshow = new Slideshow();
        slideshow.setUrl(url);
        slideshowService.save(slideshow);
        return Result.success("保存成功");

    }

    @PostMapping(value = "/updateSlideshow")
    @ApiOperation("修改轮播图")
    public Result updateSlideshow(@RequestBody Slideshow slideshow) {
        slideshowService.updateById(slideshow);
        return Result.success("修改成功");

    }

    @PostMapping(value = "/deleteSlideshow")
    @ApiOperation("删除轮播图")
    @ApiImplicitParam(name = "id", value = "轮播图id", required = true)
    public Result deleteSlideshow(Long id) {
        slideshowService.removeById(id);
        return Result.success("删除成功");

    }

}

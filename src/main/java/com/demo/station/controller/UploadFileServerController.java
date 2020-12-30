package com.demo.station.controller;

import com.demo.station.utils.FTPUtil;
import com.demo.station.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件到文件服务器（CentOS文件服务器）
 * @author : lipb
 * @date : 2020-12-30 10:05
 */
@RestController
@RequestMapping(value = "/uploadFileServer")
@Api(tags = "上传文件到文件服务器（CentOS文件服务器）")
public class UploadFileServerController {

    @ApiOperation(value = "文件上传到文件服务器")
    @RequestMapping(value = "/uploadFolder", method = RequestMethod.POST)
    public Result<Object> uploadFolder(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            FTPUtil.sshSftp(bytes, file.getOriginalFilename());
            return Result.success("http://106.15.106.108:8082/file/"+file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("上传失败");
    }
}

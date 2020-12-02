package com.zgqk.controller;

import com.zgqk.bean.Content;
import com.zgqk.config.ResponseWrapper;
import com.zgqk.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "对信息表的处理")
@RequestMapping("/content")
@RestController
public class Contentcontoller {
    @Autowired
    ContentService contentService;

    @ApiOperation("对信息状态就行修改")
    @PutMapping("/upadteContent")
    public ResponseWrapper updateContent(@RequestBody Content content){
        ResponseWrapper wrapper = contentService.updateContent(content);
        return wrapper;
    }
    @ApiOperation("上传execl表格并解析到content数据库")
    @PostMapping("/upload")
    public ResponseWrapper upload(@RequestParam(value="excelFile",required = false) MultipartFile excelFile){
        ResponseWrapper wrapper  = contentService.UploadExc(excelFile);
        return wrapper;
    }
    @ApiOperation("按照上一级编号查找信息")
    @GetMapping("/findBySupernum")
    public ResponseWrapper findBySupernum(@RequestParam(name = "superNum",defaultValue = "0")Integer superNum,
                                          @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize,
                                          @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum){
        ResponseWrapper wrapper = contentService.findBySupernum(superNum,pageSize,pageNum);
        return wrapper;
    }
    @ApiOperation("按照id查询单条信息")
    @GetMapping("/findContent")
    public ResponseWrapper findContent(Integer id){
        ResponseWrapper wrapper = contentService.findContent(id);
        return wrapper;
    }
    @ApiOperation("按照类别查标题")
    @GetMapping("/findByCategory")
    public ResponseWrapper findByCategory(@RequestParam(name = "category",defaultValue = "1")String category,
                                          @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize,
                                          @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum){
        ResponseWrapper wrapper = contentService.findByCategory(category,pageSize,pageNum);
        return wrapper;
    }

    @ApiOperation("查找已完成的问题")
    @GetMapping("/findByState")
    public ResponseWrapper findByState(@RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize,
                                       @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                       @RequestParam(name = "category",defaultValue = "4")String category){
        ResponseWrapper wrapper = contentService.findByState(pageSize,pageNum,category);
        return wrapper;
    }
}

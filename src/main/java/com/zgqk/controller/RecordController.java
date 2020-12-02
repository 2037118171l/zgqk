package com.zgqk.controller;

import com.zgqk.bean.Record;
import com.zgqk.config.ResponseWrapper;
import com.zgqk.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;


@Api(tags = "对记录表就行增删改和上传exl")
@RequestMapping("/record")
@RestController
public class RecordController {
    @Autowired
    RecordService recordService;

    @ApiOperation("增加记录")
    @PostMapping("/addRecord")
    public ResponseWrapper addRecord(@RequestBody Record record){
        ResponseWrapper wrapper = recordService.addRecord(record);
        return wrapper;
    }
    @ApiOperation("删除记录")
    @DeleteMapping("/deleteRecord")
    public ResponseWrapper deleteRecord(Integer id){
        ResponseWrapper wrapper = recordService.deleteRecord(id);
        return wrapper;
    }
    @ApiOperation("修改莫条记录")
    @PutMapping("/updateRecord")
    public ResponseWrapper updateRecord(@RequestBody Record record){
        ResponseWrapper wrapper = recordService.updateRecord(record);
        return wrapper;
    }
    @ApiOperation("查找所有记录")
    @GetMapping("/findRecord")
    public ResponseWrapper findRecord(@RequestParam(name = "pageSize",defaultValue = "10")Integer pagsSize,
                                      @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                      @RequestParam(name="id")Integer id,
                                      @RequestParam(name = "weeks",required = false)String weeks){
        ResponseWrapper wrapper = recordService.findBySearch(pagsSize,pageNum,id,weeks);
        return wrapper;
    }

    @ApiOperation("导入execl")
    @PostMapping("/UploadExcel")
    public ResponseWrapper ajaxUploadExcel(@RequestParam(value="excelFile",required = false) MultipartFile excelFile) throws ParseException {
        ResponseWrapper wrapper = recordService.UploadExcel(excelFile);
        return wrapper;
    }
}

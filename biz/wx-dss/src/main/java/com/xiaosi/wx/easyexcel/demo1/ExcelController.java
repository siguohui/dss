package com.xiaosi.wx.easyexcel.demo1;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/excel")
public class ExcelController {




//    @TycloudOperation(needAuth = false,ApiLevel = UserType.AGENCY)
//    @ApiOperation(value = "下载模板")
    @GetMapping("/template")
    public void template2(HttpServletResponse response) {
        String fileName = "导入模板";
        String sheetName = "模板";
        try {
            ExcelUtil.createTemplate(response, fileName, sheetName, null,
                    FireChemicalDto.class, FireChemicalDto.getHeadHeight());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}

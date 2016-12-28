package com.atguigu.jf.console.trigger.service.impl;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.jf.console.trigger.bean.bo.ExchangeLogBean;
import com.atguigu.jf.console.trigger.service.CreateExcelService;
import com.atguigu.jf.console.trigger.service.PcExchangeLogService;

@Service
public class CreateExcelServiceImpl implements CreateExcelService {
	@Autowired
	private PcExchangeLogService pcExchangeLogService;
	
	@Override
	public String createExcel() throws Exception {
		String path = "e:\\";
    	String fileName = this.getNewFileName("ImpExchangeLog_");
    	
	    try {
	    	// 创建工作簿
	    	Workbook wb = new HSSFWorkbook();
	    	CreationHelper createHelper = wb.getCreationHelper();
	    	// 创建工作表
	    	Sheet sheet = wb.createSheet("new sheet");
	    	// 创建行（第一行）
	        Row row = sheet.createRow((short)0);
	        /**
	         * 任务：
	         * 1、创建表头
	         * 		"积分导入流水","业务发生时间","积分供应商","导入积分","积分兑换比例","兑换手续费率","结算金额","平台利润","结算状态","结算日期","导出状态","导出时间"
	         * 		定义一个数组，遍历创建单元格
	         * 2、创建行
	         *    1）、从第二行开始，循环创建每一行及单元格对象。
	         *    2）、先去查询List<ExchangeLog>报表的集合，循环（每一行）
	         *    3）、第一种：写12个row.createCell(2).setCellValue()
	         *        第二种：封装一个数组（每一列的值），再循环这个数组，遍历该数组将值取出并给单元格赋值。
	         *          row.createCell(i).setCellValue(str[])
	         * 
	         */
	        // 1、表头，先构造数组
	        String [] title = new String []{"积分导入流水","业务发生时间","积分供应商","导入积分","积分兑换比例","兑换手续费率","结算金额","平台利润","结算状态","结算日期","导出状态","导出时间"};
	        // 遍历创建表头
	        for (int i = 0; i < title.length; i++) {
	        	// 表头的单元格赋值
	        	/*row.createCell(i).setCellValue(
	   	             createHelper.createRichTextString(title[i]));*/
	        	
	        	// 加入填充颜色
	        	CellStyle  style = wb.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        Cell cell = row.createCell((short) i);
		        cell.setCellValue(createHelper.createRichTextString(title[i]));
		        cell.setCellStyle(style);
			}
	        
	        
	        // 2、查询兑换报表
	        Map<String,Object> map = new HashMap<>();
	        List<ExchangeLogBean> list = pcExchangeLogService.selectExchangeLog(map);
	        // 2.2、遍历
	        for (int i = 0; i < list.size(); i++) {
	        	 Row datarow = sheet.createRow(i + 1);
	        	 String str[] = this.createNewArr(list.get(i));
	        	 // 循环新创建的数组
	        	 for (int j = 0; j < str.length; j++) {
	        		 datarow.createCell(j).setCellValue(createHelper.createRichTextString(str[j]));
				}
	        	 
			}
	      
	        
	    	FileOutputStream fileOut = new FileOutputStream(path + fileName);
	    	
	    	wb.write(fileOut);
			fileOut.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return path + fileName;
	}
	
	/**
	 * @方法名: getNewFileName  
	 * @功能描述: 根据时间戳获取新的文件名 
	 * @param pre
	 * @return
	 * @作者  
	 * @日期 Dec 23, 2016
	 */
	public String getNewFileName(String pre){
		return pre + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".xls";
	}
	/**
	 * @方法名: createNewArr  
	 * @功能描述: 根据实体对象构造数组
	 * @param bean
	 * @return
	 * @作者  
	 * @日期 Dec 23, 2016
	 */
	public String[] createNewArr(ExchangeLogBean bean){
		return new String [] {bean.getId(),bean.getImpDate(),bean.getProviderName(),bean.getImpPoint(),bean.getRate(),bean.getFee(),
				bean.getAmount(),bean.getProfit(),bean.getExchangeState(),bean.getExchangeDate(),bean.getExportState(),bean.getExportDate()};
	}

}

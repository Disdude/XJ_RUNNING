package com.atguigu.jf.console.trigger.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.jf.console.trigger.service.CreateExcelService;
import com.atguigu.jf.console.trigger.service.SendEmailService;

public class TriggerJob {
	@Autowired
	private CreateExcelService createExcelService;
	@Autowired
	private SendEmailService sendEmailService;

	public void doIt() {
		
		try {
			// 先导出excel
			String fileName = createExcelService.createExcel();
			// 再发送邮件
			sendEmailService.sendEmail(fileName);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("执行了doIt方法--> " + new Date());
	}
}

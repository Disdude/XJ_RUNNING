package com.atguigu.jf.console.trigger.service.impl;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.atguigu.jf.console.trigger.service.SendEmailService;

@Service
public class SendEmailServiceImpl implements SendEmailService{

	@Override
	public void sendEmail(String fileName) throws Exception {
		/**
		 * 1、通过session创建邮件的配置信息
		 */
		Properties props = new Properties();
		// 发送邮件的服务器地址
		props.setProperty("mail.host", "smtp.qq.com");
		// 使用的协议
		props.setProperty("mail.transport.protocol", "smtp");
		// 是否经过认证
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props);
		// 开启debug模式，能够看到邮件发送程序在发送邮件时的调试信息
		session.setDebug(true);
		
		/**
		 * 2、创建代表邮件内容的Message对象（JavaMail创建的邮件是基于MIME协议的）
		 */
		// 需要将session传入
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("751283482@qq.com"));
		// 标题
		msg.setSubject("这是本次的报表201612");
		// 设置收件人
		msg.setRecipient(RecipientType.TO, new InternetAddress("hcc751283482@gmail.com"));
		
		// 正文部分
		// 1、发送纯文本的邮件
		// msg.setText("你好，乐天，请查收！");
		// 2、发送html格式的邮件
		// msg.setContent("你好，<span style='color:red;'>乐天</span>，请查收！", "text/html;charset=utf-8;");
		// 3、发送带附件的邮件
		// 类似于springMVC 的文件上传
		MimeMultipart mul = new MimeMultipart();
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent("你好，<span style='color:red;'>乐天</span>，请查收！", "text/html;charset=utf-8;");
		// 放入正文
		mul.addBodyPart(bodyPart);
		
		bodyPart  = new MimeBodyPart();
		// 执行附件的文件源
		bodyPart.setDataHandler(new DataHandler(new FileDataSource(new File(fileName))));
		// 附件的命名
		bodyPart.setFileName("test.xls");
		// 放入附件
		mul.addBodyPart(bodyPart);
		
		// 将MimeMultipart加入到message对象中
		msg.setContent(mul);
		
		
		/**
		 * 3、创建Transport对象、连接服务器、发送Message、关闭连接
		 */
		// 通过session获取Transport对象
		Transport tran = session.getTransport();
		// 验证用户的信息，注意这里请使用自己的邮箱
		tran.connect("smtp.qq.com", "751283482@qq.com" , "5571621hcc0716");
		// 发送msg
		tran.sendMessage(msg, msg.getAllRecipients());
		// 关闭连接
		tran.close();
		
	}

	
}

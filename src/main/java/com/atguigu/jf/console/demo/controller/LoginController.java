package com.atguigu.jf.console.demo.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.jf.console.comment.utils.VerifyCodeUtil;
import com.atguigu.jf.console.demo.bean.pojo.User;
import com.atguigu.jf.console.demo.service.UserService;

@Controller
@RequestMapping("/logincontroller")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserService userService;

	@RequestMapping("/getVerifyCodeImage")
	public void getVerifyCodeImage(HttpServletResponse response,
			HttpSession session) throws Exception {

		/**
		 * -> 禁用缓存
		 */
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		/**
		 * -> 利用工具类生成4位的随机数字类型的字符串 type 验证码类型,参见本类的静态属性 length 验证码长度,要求大于0的整数
		 * excludeString 需排除的特殊字符（无需排除则为null）
		 */
		String verifyCode = VerifyCodeUtil.generateTextCode(
				VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);

		/**
		 * 
		 -> 将生成的字符串放入到session中
		 */
		session.setAttribute("verifyCode", verifyCode);

		/**
		 * 
		 -> 根据生成的字符串绘制图片 textCode 文本验证码 width
		 * 图片宽度(注意此宽度若过小,容易造成验证码文本显示不全,如4个字符的文本可使用85到90的宽度) height 图片高度
		 * interLine 图片中干扰线的条数 randomLocation 每个字符的高低位置是否随机 backColor
		 * 图片颜色,若为null则表示采用随机颜色 foreColor 字体颜色,若为null则表示采用随机颜色 lineColor
		 * 干扰线颜色,若为null则表示采用随机颜色
		 */
		BufferedImage image = VerifyCodeUtil.generateImageCode(verifyCode, 90,
				30, 4, true, Color.WHITE, Color.BLACK, null);
		/**
		 * -> 通过response.getOutputStream()输出流写入到浏览器中
		 */
		response.setContentType("image/jpeg");
		ImageIO.write(image, "JPEG", response.getOutputStream());

	}

	@RequestMapping("/login")
	public String login(String loginCode,String loginPasswd, String verifyCode, HttpSession session,
			RedirectAttributes redirectAttributes) {

		// 先从session中获取验证码
		String sVerifyCode = (String) session.getAttribute("verifyCode");
		// 验证码比对
		if (!verifyCode.equals(sVerifyCode)) {
			logger.error("验证码验证失败！");
			redirectAttributes.addFlashAttribute("errMsg", "验证码验证失败！");
			return "redirect:/login";
		}
		// *获取当前的登录用户对象
        Subject currentUser = SecurityUtils.getSubject();
		 // 判断当前用户是否登录
        if (!currentUser.isAuthenticated()) {
        	// 封装了一个UsernamePasswordToken，传入用户名和密码(通过前台参数传入)
            UsernamePasswordToken token = new UsernamePasswordToken(loginCode, loginPasswd);
            token.setRememberMe(true);
            try {
            	// ***通过login方法进行登录
                currentUser.login(token);
            } 
            catch (AuthenticationException ae) {
            	logger.error("用户名或密码错误！");
        		redirectAttributes.addFlashAttribute("errMsg", "用户名或密码错误！");
            }
        }

		
        return "redirect:/success.jsp";
	}
	//@RequestMapping("/login")
	public String llogin(User user, String verifyCode, HttpSession session,
			RedirectAttributes redirectAttributes) {
		System.out.println("user" + user);
		
		// 先从session中获取验证码
		String sVerifyCode = (String) session.getAttribute("verifyCode");
		// 验证码比对
		if (!verifyCode.equals(sVerifyCode)) {
			logger.error("验证码验证失败！");
			redirectAttributes.addFlashAttribute("errMsg", "验证码验证失败！");
			return "redirect:/login";
		}
		User user1 = userService.login(user);
		if (user1 != null) {
			// 将用户对象信息放入到session中
			session.setAttribute("currentUser", user1);
			return "../page/index";
			
		}
		
		logger.error("用户名或密码错误！");
		redirectAttributes.addFlashAttribute("errMsg", "用户名或密码错误！");
		return "redirect:/login";
	}

}

package com.lichking.controller;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lichking.itf.service.IUserService;
import com.lichking.pojo.web.ResultVO;
import com.lichking.pojo.web.UserVO;
import com.lichking.util.session.BMAdminCheck;

/**
 * 后台管理系统的路径总控制以及登录控制
 * @author LichKing
 *
 */
@Controller
@RequestMapping("/back")
public class BMMasterController {

	@Resource
	private IUserService userService;
	
	private Logger log = Logger.getLogger("BackgroundLogger");
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/check_psw")
	public @ResponseBody ResultVO doLogin(@RequestBody UserVO userpojo,HttpServletRequest req){
		log.info("请求路径：/back/check_psw");
		UserVO user = this.userService.getUser(userpojo.getUsername());
		String psw = user.getPassword();
		ResultVO<UserVO> resultPOJO = new ResultVO<UserVO>();
		if(psw.equals(userpojo.getPassword())){
			log.info("检查正确");
			req.getSession().setAttribute("currentUser", user.getUsername());
			resultPOJO.setResult(true);
			resultPOJO.setMsg("登录成功!");
			return resultPOJO;
		}else{
			log.info("检查错误");
			resultPOJO.setResult(false);
			resultPOJO.setMsg("用户名密码错误！");
			return resultPOJO;
		}
	}
	
	@RequestMapping("/login")
	public String Login(){
		log.info("请求路径：/back/login");
		return "back/login";
	}
	
	@RequestMapping("/manage")
	public String Manage(HttpServletRequest req){
		log.info("请求路径：/back/manage");
		boolean islogin = BMAdminCheck.isAdmin("currentUser", req);
		if(!islogin){
			log.info("用户为空，重定向至:/back/login");
			return "redirect:login";
		}else{
			return "back/manage";
		}
	}

	
	@RequestMapping("/ComOverview")
	public String vComOverview(){
		log.info("请求路径：/back/ComOverview");
		return "back/op_for_com/ComOverview";
	}
	
	@RequestMapping("/CreateNewType")
	public String vCreateNewTypes(){
		log.info("请求路径：/back/CreateNewType");
		return "back/op_for_type/CreateNewType";
	}
	
	@RequestMapping("/CreateNewCom")
	public String vCreateNewCom(){
		log.info("请求路径：/back/CreateNewCom");
		return "back/op_for_com/CreateNewCom";
	}
	
	@RequestMapping("/EditCom")
	public String vEditCom(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		log.info("请求路径：/back/EditCom");
		model.addAttribute("id", id);
		return "back/op_for_com/EditCom";
	}
	
	@RequestMapping("/ComOnAndOff")
	public String vComOnAndOff(){
		log.info("请求路径：/back/ComOnAndOff");
		return "back/op_for_com/ComOnAndOff";
	}
	
	@RequestMapping("/ComInventoryManage")
	public String vComInventoryManage(){
		log.info("请求路径：/back/ComInventoryManage");
		return "back/op_for_com/ComInventoryManage";
	}
	
	@RequestMapping("/modifyorder")
	public String vmodifyorder(){
		log.info("请求路径：/back/modifyorder");
		return "back/op_for_order/modifyorder";
	}
	
	@RequestMapping("/deleteorder")
	public String vdeleteorder(){
		log.info("请求路径：/back/deleteorder");
		return "back/op_for_order/deleteorder";
	}
	
	@RequestMapping("/finishorder")
	public String vfinisheorder(){
		log.info("请求路径：/back/finishorder");
		return "back/op_for_order/finishorder";
	}
}

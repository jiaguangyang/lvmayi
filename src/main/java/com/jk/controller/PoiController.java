package com.jk.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jk.dao.LvMapper;
import com.jk.model.Student;
import com.jk.service.LvService;
import com.jk.utils.ExportExcel;
import com.jk.utils.XwpfTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("poi")
@Controller
public class PoiController {
	//  记录日志
	/*private  static final Logger logger = Logger.getLogger(PoiController.class); */
	
	/*@Resource(name="loginService")
	private  LoginService loginService;*/
	@Autowired
	private LvService lvService;
	@Autowired
	private LvMapper lvMapper;

	@RequestMapping("findstu")
	@ResponseBody
	public HashMap<String,Object> findstu(Integer page, Integer limit, Student stu){
		return lvService.findstu(page,limit,stu);
	}
	
	
	@RequestMapping("exportXlsx")
	public  void  exportXlsx(HttpServletResponse response,HttpServletRequest request,String ids){
		List<LinkedHashMap<String,Object>> list=lvService.findstuByid(ids); //  查询数据
		
		//  设置标头
		String [ ] rowName={"ID","姓名","年龄","生日","照片","籍贯","性别","民族","电话","邮箱","详细信息"};
		
		List<Object[]> dataList=new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<String,Object>  map =  list.get(i);
			
			 Object[] array = map.values().toArray();
			 dataList.add(array);
		}
		ExportExcel exportExcel = new ExportExcel("学生信息",rowName, dataList, response);
		try {
			exportExcel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping("exportWord")
	public  void exportWord(HttpServletResponse response,String id) throws Exception{
		     List<LinkedHashMap<String,Object>> list =lvService.findstuByid(id);
		String jsonString = JSON.toJSONString(list.get(0));
		Student student = JSON.parseObject(jsonString, Student.class);
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String format = sim.format(student.getBirthDay());
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		   params.put("${sid}",student.getSid().toString());
	        params.put("${sname}",student.getSname());
	        params.put("${sage}", student.getSage().toString());
	        params.put("${birthDay}",format);
	        params.put("${sphoto}", student.getSphoto());
	        params.put("${area}",student.getArea());
	        params.put("${sex}",student.getSex()==1?"男":"女");
	        params.put("${nation}",student.getNation());
	        params.put("${phone}", student.getPhone());
	        params.put("${email}", student.getEmail());
	        params.put("${info}",student.getInfo());
		XwpfTUtil xw=new XwpfTUtil("D:\\stu.docx", params, response);
		xw.templateWrite();
	}
}

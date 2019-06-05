package com.jk.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;


import redis.clients.jedis.*;

@RequestMapping("testController")
@Controller
public class WebuploaderController {

	/*@Resource(name="shardedJedisPool")
	private ShardedJedisPool shardedJedisPool;*/

	@Autowired
	private JedisPool jedisPool;
	/*@RequestMapping("config")
	public  void config(String t){
		ShardedJedis resource = shardedJedisPool.getResource();
		ShardedJedisPipeline pipelined = resource.pipelined();
		
		pipelined.execute();
		
		System.out.println(t);
	}*/
	
	
	
	//合并、验证分片方法  
	@RequestMapping("mergeOrCheckChunks")
	public  	void   mergeOrCheckChunks(HttpServletRequest request, HttpServletResponse response) {  
	    String param = request.getParameter("param");    
	    String fileName = request.getParameter("fileName");  
	    //当前登录用户信息  
	   // BidsUser sysUser = (BidsUser)request.getSession().getAttribute("user");
	    String newFilePath = (int)(Math.random()*8999+1000)+"_"+fileName;
	    String savePath = request.getRealPath("/");  
	    //文件上传的临时文件保存在项目的temp文件夹下 定时删除  
	    savePath = new File(savePath) + "/upload/";  
	    if(param.equals("mergeChunks")){  
	        //合并文件    
	    	  Jedis jedis =null;
	        try {
	        	jedis = jedisPool.getResource();
	            //读取目录里的所有文件
	            File f = new File(savePath+"/"+jedis.get("fileName_"+fileName));    
	            File[] fileArray = f.listFiles(new FileFilter(){    
	                //排除目录只要文件    
	                @Override    
	                public boolean accept(File pathname) {    
	                    if(pathname.isDirectory()){    
	                        return false;    
	                    }    
	                    return true;    
	                }    
	            });  
	  
	            //转成集合，便于排序    
	            List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));    
	            
	            Collections.sort(fileList,new Comparator<File>() {    
	                @Override    
	                public int compare(File o1, File o2) {    
	                    if(Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())){    
	                        return -1;    
	                    }    
	                    return 1;    
	                }    
	            });    
	  
	            //截取文件名的后缀名  
	            //最后一个"."的位置  
	            int pointIndex=fileName.lastIndexOf(".");  
	            //后缀名  
	            String suffix=fileName.substring(pointIndex);  
	            //合并后的文件  
	            File outputFile = new File(savePath+"/"+jedis.get("fileName_"+fileName)+suffix);    
	            //创建文件    
	            try {  
	                outputFile.createNewFile();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }    
	            //输出流    
	            FileChannel outChnnel = new FileOutputStream(outputFile).getChannel();    
	            //合并    
	            FileChannel inChannel;    
	            for(File file : fileList){    
	                inChannel = new FileInputStream(file).getChannel();    
	                try {  
	                    inChannel.transferTo(0, inChannel.size(), outChnnel);  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }    
	                try {  
	                    inChannel.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }    
	                //删除分片    
	                file.delete();    
	            }    
	            try {  
	                outChnnel.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }    
	  
	            //清除文件夹    
	            File tempFile = new File(savePath+"/"+jedis.get("fileName_"+fileName));    
	            if(tempFile.isDirectory() && tempFile.exists()){    
	                tempFile.delete();    
	            }    
	  
	            Map<String, String> resultMap=new HashMap<>();  
	            //将文件的最后上传时间和生成的文件名返回  
	            resultMap.put("lastUploadTime", jedis.get("lastUploadTime_"+newFilePath));  
	            resultMap.put("pathFileName", jedis.get("fileName_"+fileName)+suffix);  
	              
	            /****************清除redis中的相关信息**********************/  
	            //合并成功后删除redis中的进度信息  
	            jedis.del("jindutiao_"+newFilePath);  
	            //合并成功后删除redis中的最后上传时间，只存没上传完成的  
	            jedis.del("lastUploadTime_"+newFilePath);  
	            //合并成功后删除文件名称与该文件上传时生成的存储分片的临时文件夹的名称在redis中的信息  key：上传文件的真实名称   value：存储分片的临时文件夹名称（由上传文件的MD5值+时间戳组成）  
	            //如果下次再上传同名文件  redis中将存储新的临时文件夹名称  没有上传完成的还要保留在redis中 直到定时任务生效  
	            jedis.del("fileName_"+fileName);  
	              
	            Object json2 = JSON.toJSON(resultMap);
	          //  PrintWriterJsonUtils.printWriter(response, json2.toString());
	            //response.getWriter();
	            response.getWriter().write(json2.toString());
	        
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }finally{
				jedisPool.returnResource(jedis);
	        }  
	    }else if(param.equals("checkChunk")){    
	        /*************************检查当前分块是否上传成功**********************************/    
	        String fileMd5 = request.getParameter("fileMd5");    
	        String chunk = request.getParameter("chunk");    
	        String chunkSize = request.getParameter("chunkSize");    
	        String jindutiao=request.getParameter("jindutiao");//文件上传的实时进度  
	  
	  	  Jedis jedis =null;
	        try {
	        	jedis = jedisPool.getResource();
	            //将当前进度存入redis  
	            jedis.set("jindutiao_"+newFilePath, jindutiao);  
	  
	            //将系统当前时间转换为字符串  
	            Date date=new Date();    
	            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	            String lastUploadTime=formatter.format(date);    
	            //将最后上传时间以字符串形式存入redis  
	            jedis.set("lastUploadTime_"+newFilePath, lastUploadTime);  
	  
	            //自定义文件名： 时间戳（13位）  
	            String tempFileName= String.valueOf(System.currentTimeMillis());  
	            if(jedis.get("fileName_"+fileName)==null || "".equals(jedis.get("fileName_"+fileName))){  
	                //将文件名与该文件上传时生成的存储分片的临时文件夹的名称存入redis  
	                //文件上传时生成的存储分片的临时文件夹的名称由MD5和时间戳组成  
	                jedis.set("fileName_"+fileName, fileMd5+tempFileName);  
	            }  
	  
	            File checkFile = new File(savePath+"/"+jedis.get("fileName_"+fileName)+"/"+chunk);    
	  
	            response.setContentType("text/html;charset=utf-8");    
	            //检查文件是否存在，且大小是否一致    
	            if(checkFile.exists() && checkFile.length()==Integer.parseInt(chunkSize)){    
	                //上传过    
	                try {  
	                    response.getWriter().write("{\"ifExist\":1}");  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }    
	            }else{    
	                //没有上传过    
	                try {  
	                    response.getWriter().write("{\"ifExist\":0}");  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }    
	            }    
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }finally{
				jedisPool.returnResource(jedis);
	        }  
	    }  
	}  
	  
	  
	/**
	 * 
	 * 实现  分片上传
	 * 
	 * 
	 */
	
	//保存上传分片  
	@RequestMapping("fileSave")
	public void fileSave(HttpServletRequest request, HttpServletResponse response) {  
		
		/**
		 * 将请求消息实体中的每一个项目封装成单独的DiskFileItem (FileItem接口的实现) 对象的任务
由 org.apache.commons.fileupload.FileItemFactory 接口的默认实现 org.apache.commons.fileupload.disk.DiskFileItemFactory 来完成。
当上传的文件项目比较小时，直接保存在内存中（速度比较快），
比较大时，以临时文件的形式，保存在磁盘临时文件夹（虽然速度慢些，但是内存资源是有限的）。
		 */
	    DiskFileItemFactory factory = new DiskFileItemFactory();    
	    ServletFileUpload sfu = new ServletFileUpload(factory);    
	    sfu.setHeaderEncoding("utf-8");    
	          
	        String savePath = request.getRealPath("/");  
	        savePath = new File(savePath) + "/upload/";    
	            
	        String fileMd5 = null;    
	        String chunk = null;    
	    String fileName=null;  
	        try {    
	            List<FileItem> items = sfu.parseRequest(request);    
	                
	            for(FileItem item:items){  
	                //上传文件的真实名称  
	                fileName=item.getName();  
	                if(item.isFormField()){    
	                    String fieldName = item.getFieldName();    
	                    if(fieldName.equals("fileMd5")){    
	         try {  
	                fileMd5 = item.getString("utf-8");  
	            } catch (UnsupportedEncodingException e) {  
	                e.printStackTrace();  
	            }    
	                    }    
	                    if(fieldName.equals("chunk")){    
	                        try {  
	                chunk = item.getString("utf-8");  
	            } catch (UnsupportedEncodingException e) {  
	                e.printStackTrace();  
	            }    
	                    }    
	                }else{    
	            Jedis jedis =null;
	            try {  
	                jedis =jedisPool.getResource();
	                File file = new File(savePath+"/"+jedis.get("fileName_"+fileName));    
	                if(!file.exists()){    
	                    file.mkdir();    
	                }    
	                File chunkFile = new File(savePath+"/"+jedis.get("fileName_"+fileName)+"/"+chunk);  
	                FileUtils.copyInputStreamToFile(item.getInputStream(), chunkFile);    
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }finally{
					jedisPool.returnResource(jedis);
	            }  
	                }    
	            }    
	        } catch (FileUploadException e) {    
	            e.printStackTrace();    
	        }    
	}  
	  
	  
	//当有文件添加进队列时 通过文件名查看该文件是否上传过 上传进度是多少  
	@RequestMapping("selectProgressByFileName")
	@ResponseBody
	public String selectProgressByFileName(String fileName) {  
	    String jindutiao="";  
	    Jedis jedis =null;
	    try {  
	        jedis =jedisPool.getResource();
	        if(null!=fileName && !"".equals(fileName)){  
	            jindutiao=jedis.get("jindutiao_"+fileName);  
	        }  
	    }catch(Exception e){  
	        e.printStackTrace();  
	    }finally{
			jedisPool.returnResource(jedis);
	    }  
	    return jindutiao;  
	}  
	
	
}

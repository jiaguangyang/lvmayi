package com.jk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jk.model.BiaoTi;
import com.jk.model.Ossbean;
import com.jk.model.User;
import com.jk.rmi.ThisClient;
import com.jk.service.LvService;
import com.jk.utils.Constant;
import com.jk.utils.MenuTree;
import com.jk.utils.TreeNoteUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class lvController {
    @Autowired
    private LvService lvService;

    @Autowired
    private ThisClient thisClient;

    //邮箱登录
    @RequestMapping("emailLogin")
    @ResponseBody
    public HashMap<String,Object> emailLogin(String email,String emailCode,HttpSession session){
        HashMap<String,Object> map= thisClient.emailLogin(email,emailCode,1);
        if ((Integer)map.get("status")==0){
            User user = (User)session.getAttribute("usr");
            session.setAttribute("user",user);
        }
        return map;
    }

    //手机号登录
    @RequestMapping("numlogin")
    @ResponseBody
    public HashMap<String,Object> numlogin(String phone,String smsCode,HttpSession session){
        HashMap<String,Object> map= thisClient.numlogin(phone,smsCode,1);
                  if ((Integer)map.get("status")==0){
                      User user = (User)session.getAttribute("usr");
                      session.setAttribute("user",user);
                  }
        return map;
    }




    //发送手机验证码
    @RequestMapping(value="findSmsCode",produces="application/json;charset=utf-8")
    @ResponseBody
    public HashMap<String,Object> findSmsCode(String loginNumber,HttpSession session){
        HashMap<String, Object> hashMap = thisClient.findDxCode(loginNumber,1);
        if ((Integer) hashMap.get("status")==0){
            String  user = hashMap.get("user").toString();
            session.setAttribute("usr",JSON.parseObject(user,User.class));
        }

        return hashMap;
    }

    //发送QQ邮箱验证码
    @RequestMapping("getCheckCode")
    @ResponseBody
    public HashMap<String,Object> getCheckCode(String email, HttpSession session){
        HashMap<String,Object> map= thisClient.getCheckCode(email,1);
        if ((Integer)map.get("status")==0){
            session.setAttribute("usr",JSON.parseObject(map.get("user").toString(),User.class));
        }
        return map;
    }

    //jgy删除标题
    @RequestMapping("deleteBiaoti")
    @ResponseBody
    public void deleteBiaoti(String btid,Integer type){
        lvService.deleteBiaoti(btid,type);
    }


    //jgy新增或修改标题跳转页面
    @RequestMapping("toaddBiaoti")
    public String toaddBiaoti(String btid, Model model){
        if (StringUtils.isNotEmpty(btid)){
            //根据IDcha查询标题
            BiaoTi bt= lvService.findBtById(btid);
            model.addAttribute("id",bt.getId());
            model.addAttribute("text",bt.getText());
            model.addAttribute("uri",bt.getUri());
        }
        return "addBiaoti";
    }

    //修改或新增轮播图
    @RequestMapping("addOrUpHref")
    @ResponseBody
    public void addOrUpHref(Ossbean ossbean){
      if (ossbean.getId()!=null){
          lvService.upHref(ossbean); //修改路径
      }else{
          lvService.addLunbo(ossbean);//新增轮播图
      }
    }

    //跳转修改路径页面
    @RequestMapping("toupdateHref")
    public String  toupdateHref(String href,Integer id,Model model){
        model.addAttribute("href",href);
        model.addAttribute("id",id);
        return "updateHref";
    }

    //jgy新增或修改标题
    @RequestMapping("addOrUpBiaoti")
    @ResponseBody
    public void addBiaoti(BiaoTi bt){
        lvService.addBiaoti(bt);
    }

    //jgy查询标题表
    @RequestMapping("finBiaoti")
    @ResponseBody
    public HashMap<String,Object> finBiaoti(Integer pageSize, Integer start){
        return lvService.finBiaoti(pageSize,start);
    }


    /**
     * 绿蚂蚁后台管理系统
     * 同步树  王战坤
     * @return
     */
    @RequestMapping("findTree")
    @ResponseBody
    public String getTreeAll(){

        return lvService.getTreeAll();
    }
    /**
     * 查询黑名单
     * id
     * phone
     */
    @RequestMapping("getblacklist")
    @ResponseBody
    public HashMap<String, Object> getblacklist(Integer pageSize, Integer start){

        return  lvService.getblacklist(pageSize,start);
    }

    /**
     * 删除  黑名单  wzk
     * @param
     * @return
     */
    @RequestMapping("deleteblack")
    @ResponseBody
    public  String  deleteblack(String id){
        lvService.deleteblack(id);
        return null;
    }
     //页面跳转用
    @RequestMapping("find")
    public String  find(String url){
        return url;
    }

    //用户登录
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,Object> login(HttpServletResponse response, String username,String password,Integer remPwd , HttpSession session,Model model){
        HashMap<String,Object>  map= thisClient.findUser(username,password,1);
            if ((Integer) map.get("status")==0){
                String ur = map.get("usr").toString();
                User usr = JSON.parseObject(ur,User.class);

                if (usr!=null){
                    session.setAttribute("username",usr.getUserLogin());
                    if (remPwd!=null){
                        Cookie cookie = new Cookie(Constant.cookieNamePwd,username+Constant.splitstr+password);
                        cookie.setMaxAge(604800);
                        response.addCookie(cookie);
                        model.addAttribute("password",1);
                    }else{
                        Cookie cookie = new Cookie(Constant.cookieNamePwd,"");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }else{
                    Cookie cookie = new Cookie(Constant.cookieNamePwd,"");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }

        return map;
    }

    //跳转登陆页面
    @RequestMapping("tologin")
    public String tologin(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(Constant.cookieNamePwd)){
                    String value = cookie.getValue();
                    String[] split = value.split(Constant.splitstr);
                    model.addAttribute("username",split[0]);
                    model.addAttribute("password",split[1]);
                }
            }
        }
        return "newLogin";
    }

    //查询轮播图
    @RequestMapping("findOssTable")
    @ResponseBody
    public HashMap<String,Object> findOssTable(Integer start,Integer pageSize){
        return lvService.findOssTable(start,pageSize);
    }

    //注销
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "UserLogin";
    }

    @RequestMapping("getSession")
    @ResponseBody
    public String getSession(String ssionKey,HttpSession session){
      return   session.getAttribute(ssionKey).toString();
    }
}

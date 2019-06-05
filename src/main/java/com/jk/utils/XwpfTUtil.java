package com.jk.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


public class XwpfTUtil {  
    String filePath = "D:\\stu.docx";
    InputStream is; 
    XWPFDocument doc; 
    Map<String, Object> params = new HashMap<String, Object>(); 
    HttpServletResponse response;
 
    
    public XwpfTUtil() {
		super();
	}
    /**
     * 
     * <pre>创建一个新的实例 XwpfTUtil.    
     *    
     * @param filePath   模板的位置
     * @param is       输入流对象
     * @param doc
     * @param params</pre>
     */
	public XwpfTUtil(String filePath, Map<String, Object> params, HttpServletResponse response) {
		this.filePath = filePath;
		this.params = params;
		this.response=response;
		try { 
	            is = new FileInputStream(filePath); 
	            this.doc = new XWPFDocument(is); 
	        } catch (FileNotFoundException e) { 
	            e.printStackTrace(); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	 
		
	}

	/** 
     * 用一个docx文档作为模板，然后替换其中的内容，再写入目标文档中。 
     * 
     * @throws Exception 
     */  

    public void templateWrite() throws Exception { 
        //替换段落里面的变量 
       // this.replaceInPara(doc, params); 
        //替换表格里面的变量 
        this.replaceInTable(doc, params); 
        OutputStream os = response.getOutputStream();  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition","attachment;filename="+UUID.randomUUID().toString()+".docx");  
        doc.write(os);  
        os.flush();  
        os.close();  
        this.close(os); 
        this.close(is); 
    }  
/*  
    @Test 
    public void myTest1() throws Exception { 
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator(); 
        XWPFParagraph para; 
        while (iterator.hasNext()) { 
            para = iterator.next(); 
            List<XWPFRun> runs = para.getRuns(); 
            para.removeRun(0); 
            para.insertNewRun(0).setText("hello"); 
        } 
 
        OutputStream os = new FileOutputStream("D:\\sta1.docx"); 
        doc.write(os); 
        this.close(os); 
        this.close(is);
 
        System.out.println(this.matcher("报告日期：${reportDate}").find()); 
 
    } 
  */

    public void myReplaceInPara() { 
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator(); 
        XWPFParagraph para; 
        while (iterator.hasNext()) { 
            para = iterator.next(); 
            List<XWPFRun> runs = para.getRuns(); 
 
 
        } 
 
        System.out.println('{'=='{'); 
 
    }
  
    /** 
     * 替换段落里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */  
/*   public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();  
        XWPFParagraph para;  
        while (iterator.hasNext()) {  
            para = iterator.next();  
           //this.replaceInPara(para, params);  
        }  
    }  */
  
    /** 
     * 替换段落里面的变量 
     * 
     * @param para   要替换的段落 
     * @param params 参数 
     */
    public void replaceInPara(XWPFParagraph para, Map<String, Object> params) {  
        List<XWPFRun> runs;  
        Matcher matcher;  
        if (this.matcher(para.getParagraphText()).find()) {  
            runs = para.getRuns();  
  
            int start = -1;  
            int end = -1;  
            String str = "";  
            for (int i = 0; i < runs.size(); i++) {  
                XWPFRun run = runs.get(i);  
                String runText = run.toString();  
                if ('$' == runText.charAt(0)&&'{' == runText.charAt(1)) {  
                    start = i;  
                }  
                if ((start != -1)) {  
                    str += runText;  
                }  
                if ('}' == runText.charAt(runText.length() - 1)) {  
                    if (start != -1) {  
                        end = i;  
                        break;  
                    }  
                }  
            }  
  
            for (int i = start; i <= end; i++) {  
                para.removeRun(i);  
                i--;  
                end--;  
            }  
  
            for (String key : params.keySet()) {  
                if (str.equals(key)) {  
                    para.createRun().setText((String) params.get(key));  
                    break;  
                }  
            }  
  
  
        }  
    }  
  
    /** 
     * 替换表格里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */  
    public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFTable> iterator = doc.getTablesIterator();  
        XWPFTable table;  
        List<XWPFTableRow> rows;  
        List<XWPFTableCell> cells;  
        List<XWPFParagraph> paras;  
        while (iterator.hasNext()) {  
            table = iterator.next();  
            rows = table.getRows();  
            for (XWPFTableRow row : rows) {  
                cells = row.getTableCells();  
                for (XWPFTableCell cell : cells) {  
                    paras = cell.getParagraphs();  
                    for (XWPFParagraph para : paras) {  
                        this.replaceInPara(para, params);  
                    }  
                }  
            }  
        }  
    }  
  
    /** 
     * 正则匹配字符串 
     * 
     * @param str 
     * @return 
     */  
    private Matcher matcher(String str) {  
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher;  
    }  
  
    /** 
     * 关闭输入流 
     * 
     * @param is 
     */  
    public void close(InputStream is) {  
        if (is != null) {  
            try {  
                is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * 关闭输出流 
     * 
     * @param os 
     */ 
    public void close(OutputStream os) {  
        if (os != null) {  
            try {  
                os.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
}  

/*相比较原文，我这段代码只改了
public void replaceinPara(XWPFParagraph para,Map<String,Object> params>{}
这个函数的实现方式，当然，使用方式也变了，使用方法：
[java] view plain copy
@Action(value = "exportApplyForm")  
    public void exportApplyForm() throws Exception {  
  
        String id = request.getParameter("id");  
        applyForm = applyFormService.retriveById(id);  
  
        Map<String, Object> params = new HashMap<String, Object>();  
  
        params.put("${name}", applyForm.getName());  
        params.put("${sex}", applyForm.getSex());  
        params.put("${political}", applyForm.getPolitical());  
        params.put("${place}", applyForm.getPlace());  
        params.put("${classes}", applyForm.getClasses());  
        params.put("${id}", applyForm.getStudentId());  
        params.put("${qq}", applyForm.getQq());  
        params.put("${tel}", applyForm.getTel());  
        params.put("${oldJob}", applyForm.getOldJob());  
        params.put("${swap}", applyForm.getSwap());  
        params.put("${first}", applyForm.getFirst());  
        params.put("${second}", applyForm.getSecond());  
        params.put("${award}", applyForm.getAward());  
        params.put("${achievement}", applyForm.getAchievement());  
        params.put("${advice}", applyForm.getAdvice());  
        params.put("${attach}", applyForm.getAttach());  
  
        XwpfTUtil xwpfTUtil = new XwpfTUtil();  
  
        XWPFDocument doc;  
        String fileNameInResource = "sta.docx";  
        InputStream is;  
        /*is = new FileInputStream(filePath);  
        is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);  
        doc = new XWPFDocument(is);  
  
        xwpfTUtil.replaceInPara(doc, params);  
        //替换表格里面的变量  
        xwpfTUtil.replaceInTable(doc, params);  
        OutputStream os = response.getOutputStream();  
  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition","attachment;filename="+applyForm.getName()+".docx");  
  
        doc.write(os);  
  
        xwpfTUtil.close(os);  
        xwpfTUtil.close(is);  
  
        os.flush();  
        os.close();  
    } */ 
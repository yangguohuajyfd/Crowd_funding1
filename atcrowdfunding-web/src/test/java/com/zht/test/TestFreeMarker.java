package com.zht.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class TestFreeMarker {

	public static void main(String[] args) throws Exception{
		// 创建Freemarker对象的配置对象
	    Configuration cfg = new Configuration();
	    // 设定默认的位置（路径）
	    cfg.setDirectoryForTemplateLoading(new File("D:\\ftl"));
	    cfg.setDefaultEncoding("UTF-8");
	    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

	    // 数据
	    Map paramMap = new HashMap();
	    
	    String className = "User";
	    
	    paramMap.put("packageName", "com.zht.atcrowdfunding");
	    paramMap.put("className", className);
	    
	    List<Attr> attrs = new ArrayList<>();
	    Attr a1 = new Attr();
	    a1.setType("java.lang.String");
	    a1.setName("loginacct");
	    Attr a2 = new Attr();
	    a2.setType("java.lang.String");
	    a2.setName("userpswd");
	    attrs.add(a1);
	    attrs.add(a2);
	    paramMap.put("attrs", attrs);

	    Template t = cfg.getTemplate("bean.ftl");
	    // 组合生成
	    Writer out = new OutputStreamWriter(new FileOutputStream(new File("D:\\User.java")), "UTF-8");
	    // 执行
	    t.process(paramMap, out);
	    
	    System.out.println("文件生成成功");
	}
}

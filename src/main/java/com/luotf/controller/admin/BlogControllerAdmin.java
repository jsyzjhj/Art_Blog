package com.luotf.controller.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luotf.model.Blog;
import com.luotf.model.BlogType;
import com.luotf.service.BlogService;
import com.luotf.util.subString;

@Controller
@RequestMapping(value = "/admin")
public class BlogControllerAdmin {

	@Resource(name = "blogServiceImpl")
	private BlogService blogService;
	
	/**
	 * 整合summernote实现图片上传
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/uploadImages",method = RequestMethod.POST)
	 @ResponseBody
	 public Map uploadImage(HttpServletRequest request) throws Exception {
		 CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		 Map map=new HashMap();
		 if(multipartResolver.isMultipart(request)){
			 MultipartHttpServletRequest mreq=(MultipartHttpServletRequest) request;
			 Iterator<String> fileNamesIter=mreq.getFileNames();
			 while(fileNamesIter.hasNext()){
				 MultipartFile file=mreq.getFile(fileNamesIter.next());
				 if(file!=null){
					 String myFileName=file.getOriginalFilename();
					 //System.out.println("myFileName:"+myFileName);
					 if(myFileName.trim()!=""){
						 String fileName=file.getOriginalFilename();
						 String fileBaseName=fileName.substring(0,fileName.lastIndexOf("."));
						 String fileExt=fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
						 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
						 String newFileName=df.format(new Date());
						 String fileNames=newFileName+new Random().nextInt(1000)+"."+fileExt;
						 String filePath=request.getSession().getServletContext().getRealPath("/")+"\\upload\\"+newFileName+"\\"+fileNames;
						 File localFile=new File(filePath);
						 if(!localFile.exists()){
							 localFile.mkdirs();
						 }
						 file.transferTo(localFile);
						 fileNames="http://localhost:8080/BlogV1.0/upload/"+newFileName+"/"+fileNames;
						 //System.out.println("上传成功");
						 map.put("name",fileBaseName);
						 map.put("path",fileNames);
						 map.put("status",200);
					 }
				 }
			 }
		 }
		 return map;
	 }
	 
	 /**
	  * 实现添加博客功能
	  * @param blog
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/addBlog",method = RequestMethod.POST)
	 @ResponseBody
	 public Map addBlog(Blog blog) throws Exception{
		 Map map=new HashMap();
		 //设置博客封面
		 blog.setImages(subString.subImages(blog.getContent()));
		 blog.setUpdatetime(new Date());
		 if(blogService.insertBlog(blog)!=0){
			 map.put("status", 200);
		}else{
			 //0表示：插入失败
			 map.put("status", 0);
		 }
		 return map;
	 }
	 
	 
	 /**
	  * 实现删除博客功能
	  * @param blog
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/deleteBlog",method = RequestMethod.POST)
	 @ResponseBody
	 public Map deleteBlog(String id) throws Exception{
		 Map map=new HashMap();
		int blogId=Integer.parseInt(id);
		 if(blogService.deleteBlogById(blogId)!=0){
			 map.put("status", 200);
		 }else{
			 //0表示：删除失败
			 map.put("status", 0);
		 }
		 return map;
	 }
	 
	 
	 /**
	  * 更新博客功能
	  * @param blog
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/updateBlog",method = RequestMethod.POST)
	 @ResponseBody
	 public Map updateBlog(Blog blog) throws Exception{
		 Map map=new HashMap();
		 if(blogService.updateBlogSelective(blog)!=0){
			 map.put("status", 200);
		 }else{
			 //0表示：更新失败
			 map.put("status", 0);
		 }
		 return map;
	 }
	 
	 /**
	  * 按博客id查询博客信息
	  * @param blog
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectBlogById",method = RequestMethod.POST)
	 @ResponseBody
	 public Map selectBlogById(String id) throws Exception{
		 Map map=new HashMap();
		 int blogId=Integer.parseInt(id);
		 Blog blog=blogService.selectBlogById(blogId);
		 if(blog!=null){
			 map.put("status", 200);
			 map.put("blog", blog);
		 }else{
			 //500表示：返回值为Null
			 map.put("status", 500);
		 }
		 return map;
	 }
	 
	 /**
	  * 通过类别typeId查询博客信息
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectBlogByTypeId",method = RequestMethod.POST)
	 @ResponseBody
	 public Map selectBlogByTypeId(String id) throws Exception{
		 Map map=new HashMap();
		 int blogTypeId=Integer.parseInt(id);
		 List<Blog> blogList=blogService.selectBlogByTypeId(blogTypeId);
		 if(blogList.size()>0){
			 map.put("status", 200);
			 map.put("blogList", blogList);
		 }else{
			 //500表示：返回值为Null
			 map.put("status", 500);
		 }
		 return map;
	 }
	 
	 /**
	  * 按照不同条件分页查询博客信息
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectBlogListByPage",method = RequestMethod.POST)
	 @ResponseBody
	 public Map selectBlogListByPage(Blog blog) throws Exception{
		 Map map=new HashMap();
		 
		 if(blog.getTitle()!=null||blog.getTitle()!=""){
			 map.put("title", blog.getTitle());
		 }
		 if(blog.getIntroduction()!=null||blog.getIntroduction()!=""){
			 map.put("introduction", blog.getIntroduction());
		 }
		 if(blog.getKeyword()!=null||blog.getKeyword()!=""){
			 map.put("keyword", blog.getKeyword());
		 }
		 if(blog.getContent()!=null||blog.getContent()!=""){
			 map.put("content", blog.getContent());
		 }
		 if(blog.getIstop()!=null){
			 map.put("isTop", blog.getIstop());
		 }
		 if(blog.getType().getId()!=null){
			 map.put("type_id", blog.getType().getId());
		 }
		 if(blog.getStatus()!=null){
			 map.put("status", blog.getStatus());
		 }
		 if(blog.getIsrecommend()!=null){
			 map.put("isRecommend", blog.getIsrecommend());
		 }
		 if(blog.getAddtime()!=null){
			 map.put("addTime", blog.getAddtime());
		 }
		 //分页显示：第1页开始，每页显示10条记录
		 PageHelper.startPage(1, 10);
		 List<Blog> blogList=blogService.selectBlogListByPage(map);
		 PageInfo<Blog> pageInfo=new PageInfo<Blog>(blogList);
		 	/*System.out.println("总记录数："+pageInfo.getTotal());
	    	System.out.println("总页数："+pageInfo.getPages());
	    	System.out.println("当前页："+pageInfo.getPageNum());
	    	System.out.println("每页的数量："+pageInfo.getPageSize());
	    	System.out.println("当前页数量："+pageInfo.getSize());*/
		 if(blogList.size()>0){
			 map.put("status", 200);
			 map.put("blogList", blogList);
			 map.put("pageInfo", pageInfo);
		 }else{
			 //500表示：返回值为Null
			 map.put("status", 500);
		 }
		 return map;
	 }
	 
	 
	 /**
	  * 按照不同条件查询博客的数量
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectBlogCount",method = RequestMethod.POST)
	 @ResponseBody
	 public Map selectBlogCount(Blog blog) throws Exception{
		 Map map=new HashMap();
		 if(blog.getKeyword()!=null||blog.getKeyword()!=""){
			 map.put("keyword", blog.getKeyword());
		 }
		 if(blog.getType().getId()!=null){
			 map.put("type_id", blog.getType().getId());
		 }
		 if(blog.getStatus()!=null){
			 map.put("status", blog.getStatus());
		 }
		 if(blog.getIsrecommend()!=null){
			 map.put("isRecommend", blog.getIsrecommend());
		 }
		 if(blog.getAddtime()!=null){
			 map.put("addTime", blog.getAddtime());
		 }
		 Long count=blogService.selectBlogCount(map);
		 if(count>0){
			 map.put("status", 200);
			 map.put("count", count);
		 }else{
			 //500表示：返回值为Null
			 map.put("status", 500);
		 }
		 return map;
	 }
	 
	 
	 /**
	  * 查询前一篇博客信息
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectPrevBlog",method = RequestMethod.POST)
	 @ResponseBody
	 public Map selectPrevBlog(String id) throws Exception{
		 Map map=new HashMap();
		 int blogId=Integer.parseInt(id);
		 Blog blog=blogService.selectPrevBlog(blogId);
		 if(blog!=null){
			 map.put("status", 200);
			 map.put("blog", blog);
		 }else{
			 //500表示：返回值为Null
			 map.put("status", 500);
		 }
		 return map;
	 }
	 
	 /**
	  * 查询后一篇博客信息
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectNextBlog",method = RequestMethod.POST)
	 @ResponseBody
	 public Map selectNextBlog(String id) throws Exception{
		 Map map=new HashMap();
		 int blogId=Integer.parseInt(id);
		 Blog blog=blogService.selectNextBlog(blogId);
		 if(blog!=null){
			 map.put("status", 200);
			 map.put("blog", blog);
		 }else{
			 //500表示：返回值为Null
			 map.put("status", 500);
		 }
		 return map;
	 }
	 
	 
}

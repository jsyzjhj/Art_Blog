package com.luotf.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luotf.annotation.AccessLimit;
import com.luotf.model.RResource;
import com.luotf.model.Visit;
import com.luotf.service.VisitService;



@Controller
@RequestMapping(value = "/admin")
public class VisitControllerAdmin {

	@Resource(name = "visitServiceImpl")
	private VisitService visitService;
	/*
	  * 按照不同时间查询访客的数量
	  */
	 @RequestMapping(value = "/selectVisitListByDate",method = RequestMethod.POST)
	 @ResponseBody
	 @AccessLimit(seconds=1,maxCount=10)
	 public Map selectBlogListByDate(@RequestParam(value="format") String format,@RequestParam(value="startTime") String startTime,@RequestParam(value="endTime") String endTime) throws Exception{
		 Map map=new HashMap();
		 if(format!=""&&format!=null){
			 map.put("format", format);
		 }
		 if(startTime!=""&&startTime!=null){
			 map.put("startTime", startTime);
		 }
		 if(endTime!=""&&endTime!=null){
			 map.put("endTime", endTime);
		 }
		 List<Map> list=visitService.selectVisitListByDate(map);
		 Map returnMap=new HashMap();
		 if(list.size()>0){
			 returnMap.put("status", 200);
		 }else{
			 //500表示：返回值为Null
			 returnMap.put("status", 500);
		 }
		 returnMap.put("list", list);
		 return returnMap;
	 }
	 
	 /**
	  * 模糊组合分页查询访客信息
	  * @param id，resource
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectLikeVisitListByPage")
	 @ResponseBody
	 public Map selectLikeVisitListByPage(Visit visit,@RequestParam(value="startTime") String startTime,@RequestParam(value="endTime") String endTime,@RequestParam(value="page", required=true,defaultValue="1") Integer page,@RequestParam(value="pageSize", required=true,defaultValue="9") Integer pageSize) throws Exception{
		 Map map=new HashMap();
		 if(startTime!=""&&startTime!=null){
			 map.put("startTime", startTime);
		 }
		 if(endTime!=""&&endTime!=null){
			 map.put("endTime", endTime);
		 }
		 if(visit.getIp()!=null&&visit.getIp()!=""){
			 map.put("ip", visit.getIp());
		 }
		 if(visit.getCity()!=null&&visit.getCity()!=""){
			 map.put("city", visit.getCity());
		 }
		 if(visit.getPlatformType()!=null&&visit.getPlatformType()!=""){
			 map.put("platformType", visit.getPlatformType());
		 }
		 if(visit.getBrowserType()!=null&&visit.getBrowserType()!=""){
			 map.put("browserType", visit.getBrowserType());
		 }
		 //分页显示：第1页开始，每页显示9条记录
		 PageHelper.startPage(page, pageSize);
		 List<Visit> visitList=visitService.selectLikeVisitListByPage(map);
		 PageInfo<Visit> pageInfo=new PageInfo<Visit>(visitList);
		 Map returnMap=new HashMap();
		 if(visitList.size()>0){
			 returnMap.put("status", 200);
		 }else{
			 //500表示：返回值为Null
			 returnMap.put("status", 500);
		 }
		 returnMap.put("visitList", visitList);
		 returnMap.put("pageInfo", pageInfo);
		 return returnMap;
	 }
	 
	 
	 /**
	  * 模糊组合分页查询访客信息
	  * @param id，resource
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectLikeVisitListGroupByIp")
	 @ResponseBody
	 public Map selectLikeVisitListGroupByIp(Visit visit,@RequestParam(value="startTime") String startTime,@RequestParam(value="endTime") String endTime,@RequestParam(value="page", required=true,defaultValue="1") Integer page,@RequestParam(value="pageSize", required=true,defaultValue="9") Integer pageSize) throws Exception{
		 Map map=new HashMap();
		 if(startTime!=""&&startTime!=null){
			 map.put("startTime", startTime);
		 }
		 if(endTime!=""&&endTime!=null){
			 map.put("endTime", endTime);
		 }
		 if(visit.getIp()!=null&&visit.getIp()!=""){
			 map.put("ip", visit.getIp());
		 }
		 if(visit.getCity()!=null&&visit.getCity()!=""){
			 map.put("city", visit.getCity());
		 }
		 if(visit.getPlatformType()!=null&&visit.getPlatformType()!=""){
			 map.put("platformType", visit.getPlatformType());
		 }
		 if(visit.getBrowserType()!=null&&visit.getBrowserType()!=""){
			 map.put("browserType", visit.getBrowserType());
		 }
		 //分页显示：第1页开始，每页显示9条记录
		 PageHelper.startPage(page, pageSize);
		 List visitList=visitService.selectLikeVisitListGroupByIp(map);
		 PageInfo pageInfo=new PageInfo(visitList);
		 Map returnMap=new HashMap();
		 if(visitList.size()>0){
			 returnMap.put("status", 200);
		 }else{
			 //500表示：返回值为Null
			 returnMap.put("status", 500);
		 }
		 returnMap.put("visitList", visitList);
		 returnMap.put("pageInfo", pageInfo);
		 return returnMap;
	 }
	 
	 
	 
	
	 
	 /**
	  * 
	  * @param 
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/selectVisitListByIp",method = RequestMethod.POST)
	 @ResponseBody
	 public Map selectVisitListByIp() throws Exception{
		 Map map=new HashMap();
		 List list=visitService.selectVisitListByIp(map);
		 if(list.size()>0){
			 map.put("status", 200);
		 }else{
			 //500表示：返回值为Null
			map.put("status", 500);
		 }
		 map.put("list", list);
		 return map;
	 }
	 
	 /**
	  * 更新访客功能
	  * @param visit
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/updateVisit",method = RequestMethod.POST)
	 @ResponseBody
	 public Map updateVisit(Visit visit) throws Exception{
		 Map map=new HashMap();
		 if(visitService.updateByPrimaryKeySelective(visit)!=0){
			 map.put("status", 200);
		 }else{
			 //0表示：更新失败
			 map.put("status", 0);
		 }
		 return map;
	 }
}
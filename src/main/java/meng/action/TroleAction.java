package meng.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import meng.action.base.BaseAction;
import meng.model.base.Trole;
import meng.model.base.Tuser;
import meng.model.base.SessionInfo;
import meng.model.easyui.Grid;
import meng.model.easyui.Json;
import meng.service.TroleServiceI;
import meng.service.TuserServiceI;
import meng.util.ConfigUtil;
import meng.util.HqlFilter;

@SuppressWarnings("serial")
@Namespace("/base")
@Action
public class TroleAction extends BaseAction<Trole>{
	
	@Autowired
	private TuserServiceI userService;
	
	/**
	 * 注入业务逻辑， 使当前action调用servie.xxx的时候，直接是调用基础业务逻辑
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * @param service
	 */
	@Autowired
	public void setService(TroleServiceI service)
	{
		this.service = service;
	}
	
	/**
	 * 角色Grid
	 */
	public void grid()
	{
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getTuser().getId());
		grid.setTotal(((TroleServiceI) service).countRoleByFilter(hqlFilter));
		grid.setRows(((TroleServiceI) service).findRoleByFilter(hqlFilter, page, rows));
		this.writeJson(grid);
	}
	
	/**
	 * 保存一个角色
	 */
	public void save()
	{
		Json json = new Json();
		if(data != null)
		{
			SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
			((TroleServiceI) service).saveRole(data, sessionInfo.getTuser().getId());
			json.setSuccess(true);
		}
		this.writeJson(json);
	}
	
	/**
	 * 角色授权
	 */
	public void grant()
	{
		Json json = new Json();
		((TroleServiceI) service).grant(id, ids);
		json.setSuccess(true);
		this.writeJson(json);
	}
	
	/**
	 * 获取当前用户能看到的所有角色树
	 */
	public void doNotNeedSecurity_getRolesTree()
	{
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		Tuser user = userService.getById(sessionInfo.getTuser().getId());
		Set<Trole> roles = user.getTroles();
		List<Trole> l = new ArrayList<Trole>(roles);
		Collections.sort(l, new Comparator<Trole>(){

			@Override
			public int compare(Trole o1, Trole o2) { //排序
				if(o1.getSeq() == null)
				{
					o1.setSeq(1000);
				}
				if(o2.getSeq() == null)
				{
					o2.setSeq(1000);
				}
				return o1.getSeq().compareTo(o2.getSeq());
			}
			
		});
		this.writeJson(l);
	}
	
	/**
	 * 获取当前用户的角色
	 */
	public void doNotNeedSecurity_getRoleByUserId()
	{
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", id);
		List<Trole> roles = ((TroleServiceI) service).findRoleByFilter(hqlFilter);
		this.writeJson(roles);
	}
	
	/**
	 * 用户角色分布报表
	 */
	public void doNotNeedSecurity_userRoleChart()
	{
		List<Trole> roles = service.find();
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		
		for(Trole role : roles)
		{
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", role.getName());
			m.put("y", userService.countUserByRoleId(role.getId()));
			m.put("sliced", false);
			m.put("selected", false);
			l.add(m);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("name", "无");
		m.put("y", userService.countUserByNotRoleId());
		m.put("sliced", true);
		m.put("selected", true);
		l.add(m);
		this.writeJson(l);
	}

}

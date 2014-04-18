package meng.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import meng.action.base.BaseAction;
import meng.model.base.Torganization;
import meng.model.base.Tuser;
import meng.model.base.SessionInfo;
import meng.model.easyui.Json;
import meng.service.TorganizationServiceI;
import meng.service.TuserServiceI;
import meng.util.ConfigUtil;
import meng.util.HqlFilter;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 机构
 * @author dell
 *
 */
@SuppressWarnings("serial")
@Namespace("/base")
@Action
public class TorganizationAction extends BaseAction<Torganization>{

	@Autowired
	private TuserServiceI userService;
	
	/**
	 * 注入业务逻辑， 使当前action调用servie.xxx的时候，直接是调用基础业务逻辑
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * @param service
	 */
	@Autowired
	public void setService(TorganizationServiceI service)
	{
		this.service = service;
	}
	
	/**
	 * 保存一个机构
	 */
	public void save()
	{
		Json json = new Json();
		if(data != null)
		{
			SessionInfo sessionInfo = (SessionInfo) this.getSession().getAttribute(ConfigUtil.getSessionInfoName());
			((TorganizationServiceI) service).saveOrganization(data, sessionInfo.getTuser().getId());
			json.setSuccess(true);
		}
		this.writeJson(json);
	}
	
	/**
	 * 更新机构
	 */
	public void update()
	{
		Json json = new Json();
		if(!StringUtils.isBlank(data.getId()))
		{
			if(data.getTorganization() != null && StringUtils.equals(data.getId(), data.getTorganization().getId())){
				json.setMsg("父机构不可以是自己");
			}
			else
			{
				((TorganizationServiceI) service).updateOrganization(data);
				json.setSuccess(true);
			}
		}
		
		this.writeJson(json);
	}
	
	/**
	 * 获得机构下拉树
	 */
	public void doNotNeedSecurity_comboTree()
	{
		HqlFilter hqlFilter = new HqlFilter();
		this.writeJson(service.findByFilter(hqlFilter));
	}
	
	/**
	 * 机构授权
	 */
	public void grant()
	{
		Json json = new Json();
		((TorganizationServiceI) service).grant(id, ids);
		json.setSuccess(true);
		this.writeJson(json);
	}
	
	/**
	 * 获得当前用户能看到的所有机构树
	 */
	public void doNotNeedSecurity_getTorganizationsTree()
	{
		SessionInfo sessonInfo = (SessionInfo) this.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		Tuser user = userService.getById(sessonInfo.getTuser().getId());
		Set<Torganization> Torganizations = user.getTorganizations();
		List<Torganization> l = new ArrayList<Torganization>(Torganizations);
		Collections.sort(l, new Comparator<Torganization>(){ //排序

			@Override
			public int compare(Torganization o1, Torganization o2) {
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
	 * 获得当前用户的机构
	 */
	public void doNotNeedSecurity_getTorganizationByUserId()
	{
		HqlFilter hqlFilter = new HqlFilter(this.getRequest());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", id);
		List<Torganization> Torganization = ((TorganizationServiceI) service).findOrganizationByFilter(hqlFilter);
		this.writeJson(Torganization);
	}
}

package meng.action;

import meng.action.base.BaseAction;
import meng.model.base.Tresourcetype;
import meng.service.TresourcetypeServiceI;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/base")
@Action
public class TresourcetypeAction extends BaseAction<Tresourcetype>{

	/**
	 * 注入业务逻辑， 使当前action调用servie.xxx的时候，直接是调用基础业务逻辑
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * @param service
	 */
	@Autowired
	public void setService(TresourcetypeServiceI service)
	{
		this.service = service;
	}
	
	/**
	 * 获得资源类型
	 */
	public void doNotNeedSecurity_combobox()
	{
		this.writeJson(((TresourcetypeServiceI) service).findResourcetype());
	}
}

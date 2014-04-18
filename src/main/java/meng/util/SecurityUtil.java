package meng.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import meng.model.base.Torganization;
import meng.model.base.Tresource;
import meng.model.base.Trole;
import meng.model.base.SessionInfo;

/**
 * 用于判断前台月面判断是否有权限的工具
 * 
 * @author Administrator
 *
 */
public class SecurityUtil {
	private HttpSession session;
	
	public SecurityUtil(HttpSession session){
		this.session = session;
	}
	
	/**
	 * 判断当前用户是否可以访问某些资源
	 * 
	 * @param url
	 * @return
	 */
	public boolean havePermission(String url){
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		List<Tresource> resources = new ArrayList<Tresource>();
		for(Trole role : sessionInfo.getTuser().getTroles()){
			resources.addAll(role.getTresources());
		}
		for(Torganization organization : sessionInfo.getTuser().getTorganizations()){
			resources.addAll(organization.getTresources());
		}
		resources = new ArrayList<Tresource>(new HashSet<Tresource>(resources));//去重复(这里包含了当前用户可访问的所有资源)
		for(Tresource resource : resources){
			if(StringUtils.equals(resource.getUrl(), url)){//如果有相同的，则代表单签用户可以访问这个资源
				return true;
			}
		}
		return false;
	}

}

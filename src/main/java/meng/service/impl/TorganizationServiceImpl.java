package meng.service.impl;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meng.dao.base.BaseDaoI;
import meng.model.base.Torganization;
import meng.model.base.Tresource;
import meng.model.base.Tuser;
import meng.service.TorganizationServiceI;
import meng.service.base.impl.BaseServiceImpl;
import meng.util.BeanUtils;
import meng.util.HqlFilter;

@Service
public class TorganizationServiceImpl extends BaseServiceImpl<Torganization> implements TorganizationServiceI{

	@Autowired
	private BaseDaoI<Tresource> resourceDao;
	
	@Autowired
	private BaseDaoI<Tuser> userDao;

	@Override
	public void updateOrganization(Torganization organization) {
		if(!StringUtils.isBlank(organization.getId()))
		{
			Torganization t = this.getById(organization.getId());
			Torganization oldParent = t.getTorganization();
			BeanUtils.copyNotNullProperties(organization, t, new String[]{"createdatetime"});
			if(organization.getTorganization() != null) //说明要修改的节点选中了上级节点
			{
				Torganization pt = this.getById(organization.getTorganization().getId()); //上级节点
				isParentToChild(t, pt, oldParent); //说明要将当前节点修改到当前节点的子或者孙子下
				t.setTorganization(pt);
			}
			else
			{
				t.setTorganization(null);
			}
		}
	}
	
	/**
	 * 判断是否将当前节点修改到当前节点的子节点下
	 * @param t 当前节点
	 * @param pt 要修改到的节点
	 * @param oldParent 原上级节点
	 * @return
	 */
	private boolean isParentToChild(Torganization t, Torganization pt, Torganization oldParent)
	{
		if(pt != null && pt.getTorganization() != null)
		{
			if(StringUtils.equals(pt.getTorganization().getId(), t.getId()))
			{
				pt.setTorganization(oldParent);
				return true;
			}
			else
			{
				return isParentToChild(t, pt.getTorganization(), oldParent);
			}
		}
		return false;
	}

	@Override
	public void grant(String id, String resourceIds) {
		Torganization Torganization = this.getById(id);
		if(Torganization != null)
		{
			Torganization.setTresources(new HashSet<Tresource>());
			for(String resourceId : resourceIds.split(","))
			{
				if(!StringUtils.isBlank(resourceId))
				{
					Tresource resource = resourceDao.getById(Tresource.class, resourceId);
					if(resource != null)
					{
						Torganization.getTresources().add(resource);
					}
				}
			}
		}
	}

	@Override
	public List<Torganization> findOrganizationByFilter(HqlFilter hqlFilter) {
		String hql = "select distanct t from Torganization t join t.Tusers Tuser";
		return this.find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	@Override
	public void saveOrganization(Torganization organization, String userId) {
		this.save(organization);
		
		Tuser Tuser = userDao.getById(Tuser.class, userId);
		if(Tuser != null)
		{
			Tuser.getTorganizations().add(organization);
		}
		
	}
}

package meng.service.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import meng.dao.base.BaseDaoI;
import meng.model.base.Torganization;
import meng.model.base.Trole;
import meng.model.base.Tuser;
import meng.service.TuserServiceI;
import meng.service.base.impl.BaseServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TuserServiceImpl extends BaseServiceImpl<Tuser> implements TuserServiceI {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TuserServiceImpl.class);

	@Autowired
	private BaseDaoI<Trole> roleDao;
	
	@Autowired
	private BaseDaoI<Torganization> organizationDao;
	
	@Override
	public void grantRole(String id, String roleIds) {
		Tuser user = getById(id);
		if (user != null) {
			user.setTroles(new HashSet<Trole>());
			for (String roleId : roleIds.split(",")) {
				if (!StringUtils.isBlank(roleId)) {
					Trole role = roleDao.getById(Trole.class, roleId);
					if (role != null) {
						user.getTroles().add(role);
					}
				}
			}
		}
	}

	@Override
	public void grantOrganization(String id, String organizationIds) {
		Tuser user = getById(id);
		if (user != null) {
			user.setTorganizations(new HashSet<Torganization>());
			for (String organizationId : organizationIds.split(",")) {
				if (!StringUtils.isBlank(organizationId)) {
					Torganization organization = organizationDao.getById(Torganization.class, organizationId);
					if (organization != null) {
						user.getTorganizations().add(organization);
					}
				}
			}
		}
	}

	@Override
	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("a", k);
			params.put("b", k + 2);
			k = k + 2;
			l.add(count("select count(*) from Tuser t where HOUR(t.createdatetime)>=:a and HOUR(t.createdatetime)<:b", params));
		}
		return l;
	}

	@Override
	public Long countUserByRoleId(String roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		String hql = "select count(*) from Tuser t join t.troles role where role.id = :roleId";
		return count(hql, params);
	}

	@Override
	public Long countUserByNotRoleId() {
		String hql = "select count(*) from Tuser t left join t.troles role where role.id is null";
		return count(hql);
	}

}

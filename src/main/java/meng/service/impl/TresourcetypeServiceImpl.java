package meng.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import meng.model.base.Tresource;
import meng.model.base.Tresourcetype;
import meng.service.TresourcetypeServiceI;
import meng.service.base.BaseServiceI;
import meng.service.base.impl.BaseServiceImpl;

/**
 * 资源类型业务逻辑
 * @author dell
 *
 */
@Service
public class TresourcetypeServiceImpl extends BaseServiceImpl<Tresourcetype> implements TresourcetypeServiceI{

	/**
	 * 为列表添加缓存，查询一次过后，只要不重启服务，缓存一直存在，不需要再查询数据库了，节省一些资源
	 * 在ehcache.xml里面需要有对应的value
	 * <cache name="ResourcetypeServiceCache"
	 * 
	 * key是自己设定的一个ID，用来识别缓存
	 */
	@Override
	public List<Tresourcetype> findResourcetype() {
		return this.find();
	}

}

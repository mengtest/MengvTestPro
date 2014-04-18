package meng.model.base;

/**
 * SessionInfo模型，只要登录成功，就要设置到session里面，便于系统使用
 * @author dell
 *
 */
@SuppressWarnings("serial")
public class SessionInfo implements java.io.Serializable{
	private Tuser tuser;

	public Tuser getTuser() {
		return tuser;
	}

	public void setTuser(Tuser Tuser) {
		this.tuser = Tuser;
	}
	
	@Override
	public String toString()
	{
		return tuser.getLoginname();
	}

}

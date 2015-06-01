package gl.ibatis;

import gl.object.GameInfo;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CenterMysql extends SqlMapClientDaoSupport{

	private static Logger logger = Logger.getLogger(CenterMysql.class);	
	
	public List<GameInfo> querySlavelist(){
		logger.debug("CenterMysql,querySlavelist run");

		return getSqlMapClientTemplate().queryForList("queryslavelist");
	}

}

package com.atguigu.jf.console.baseapi.trigger.mapper;

import java.util.List;
import java.util.Map;

import com.atguigu.jf.console.trigger.bean.bo.ExchangeLogBean;

public interface PcExchangeLogMapper {
	/**
	 * @方法名: selectExchangeLog  
	 * @功能描述: 查询兑换流水报表的信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @作者  
	 * @日期 Dec 23, 2016
	 */
	List<ExchangeLogBean> selectExchangeLog(Map<String,Object> map) throws Exception;

}

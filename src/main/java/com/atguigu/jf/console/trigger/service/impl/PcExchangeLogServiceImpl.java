package com.atguigu.jf.console.trigger.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.jf.console.baseapi.trigger.mapper.PcExchangeLogMapper;
import com.atguigu.jf.console.trigger.bean.bo.ExchangeLogBean;
import com.atguigu.jf.console.trigger.service.PcExchangeLogService;
@Service
public class PcExchangeLogServiceImpl implements PcExchangeLogService {

	@Autowired
	private PcExchangeLogMapper pcExchangeLogMapper;
	
	
	/**
	 * @方法名: selectExchangeLog  
	 * @功能描述: 查询兑换流水报表的信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @作者  
	 * @日期 Dec 23, 2016
	 */
	public List<ExchangeLogBean> selectExchangeLog(Map<String,Object> map) throws Exception{
		return pcExchangeLogMapper.selectExchangeLog(map);
	}
}

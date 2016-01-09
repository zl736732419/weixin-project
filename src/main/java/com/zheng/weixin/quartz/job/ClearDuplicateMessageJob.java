package com.zheng.weixin.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zheng.weixin.kit.DuplicateMessageKit;

public class ClearDuplicateMessageJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		DuplicateMessageKit.clear();
	}

}

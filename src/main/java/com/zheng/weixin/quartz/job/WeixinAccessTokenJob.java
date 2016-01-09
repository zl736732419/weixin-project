package com.zheng.weixin.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zheng.weixin.quartz.task.RefreshAccessTokenTask;

public class WeixinAccessTokenJob extends QuartzJobBean {

	private RefreshAccessTokenTask refreshAccessTokenTask;

	public void setRefreshAccessTokenTask(
			RefreshAccessTokenTask refreshAccessTokenTask) {
		this.refreshAccessTokenTask = refreshAccessTokenTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// 定时获取accessToken
		refreshAccessTokenTask.refreshAccessToken();
	}

}

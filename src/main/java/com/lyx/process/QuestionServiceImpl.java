package com.lyx.process;

import cn.hutool.core.util.StrUtil;
import com.lyx.common.CommonResult;
import com.lyx.entity.Question;
import org.springframework.stereotype.Service;

@Service("questionServiceImpl")
public class QuestionServiceImpl
{
	/**
	 * 获得加密密码
	 */
	public CommonResult getPasswd(Question question)
	{
		if (!StrUtil.equals(question.getChuoHao(), "九斤红"))
		{
			return CommonResult.errorMsg("答案错误");
		}

		if (!StrUtil.equals(question.getXiaoMing(), "路子"))
		{
			return CommonResult.errorMsg("答案错误");
		}

		return CommonResult.successData("jizhu.com741123");
	}
}
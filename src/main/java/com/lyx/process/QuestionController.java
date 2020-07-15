package com.lyx.process;

import com.lyx.common.CommonResult;
import com.lyx.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController
{
	@Autowired
	@Qualifier("questionServiceImpl")
	private QuestionServiceImpl service;

	@PostMapping("/getPasswd")
	public CommonResult getPasswd(@RequestBody Question question)
	{
		return service.getPasswd(question);
	}
}
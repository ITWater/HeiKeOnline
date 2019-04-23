package top.aiteyou.controller.griefstore;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.griefstore.Letter;
import top.aiteyou.service.griefstore.LetterService;
import top.aiteyou.service.griefstore.LetterTemplateService;
import top.aiteyou.websocket.LetterWebSocketService;

/**
	*@author :tb
	*@version 2018年4月29日 下午12:49:27
*/
@Controller
@RequestMapping(value="/griefstore/letters")
public class LetterController {
	@Autowired
	private LetterService letterService=null;
	@Autowired
	private LetterWebSocketService letterWebSocketService=null;
	@Autowired
	private LetterTemplateService letterTemplateService=null;
	/**
	 * 用户寄出一份信  烦恼信/回复信
	 * 返回信封地址
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Letter insertOne(@RequestBody Letter letter){
		letter.setTime(new Timestamp(System.currentTimeMillis()));
		
		int result=letterService.insertOne(letter);
		if (result==1) {
			boolean isFirstLetter=(letter.getToUserId()==null);
			if (isFirstLetter) {
				//异步 尝试选择一个解忧人发送websocket
				letterWebSocketService.saveAndSendRandomUserOfLetter(letter);
			}else {
				//异步 指定某人发送websoket
				letterWebSocketService.saveAndSendUserOfLetter(letter);
				//异步 模板消息推送
				letterTemplateService.sendGriefLetterReply(letter);
				
			}
		}
		return letter;
	}
}



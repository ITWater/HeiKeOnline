package top.aiteyou.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.service.PushService;
import top.aiteyou.transform.GameOverInfo;

/**
 * 	模板消息推送
	*@author :tb
	*@version 2018年4月1日 下午9:26:28
*/
@Controller
@RequestMapping(value="/pushserver")
public class PushMessageController {
	
	@Autowired
	private PushService pushService=null;
	/**
	 * 赛事预告
	 * @param gameId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/app{appId}/{gameId}",method=RequestMethod.GET)
	@ResponseBody
	public List<String> notifySockerGamePushServer(@PathVariable("appId") int appId,@PathVariable("gameId") int gameId) throws Exception{
		return pushService.GamePushServer(appId,gameId);
	}
	/**
	 * 赛事结束通知 
	 */
	@RequestMapping(value="/app{appId}/gameoverinfo",method=RequestMethod.POST)
	@ResponseBody
	public List<String> gameOverInform(@PathVariable("appId") int appId,@RequestBody GameOverInfo gameOverInfo){
		return pushService.GameOverPushServer(appId,gameOverInfo);
	}

}



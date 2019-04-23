package top.aiteyou.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.News;
import top.aiteyou.mapper.NewsMapper;
import top.aiteyou.service.NewsService;
import top.aiteyou.transform.NewsLike;

/**
	*@author :tb
	*@version 2018年3月22日 上午11:54:26
*/
@Controller
@RequestMapping(value="/news")
public class NewsController {
	@Autowired
	private NewsService newsService=null;
	@Autowired
	private NewsMapper newsMapper=null;
	private static String SUCCESS="ok";
	private static String DEFEAT="defeat";
	
	
	/**
	 * 增加浏览数
	 * @return
	 */
	@RequestMapping(value="/addViewCount/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public int addViewCount(@PathVariable("id")Integer newId){
		return newsMapper.addViewCount(newId);
	}
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public List<News> selectNewsBrief(@RequestBody(required=false) RowBounds rowBounds){
		List<News> news=newsService.selectNewsJoinAuthor(rowBounds);
		return news;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public News selectNewsDetail(@PathVariable("id") int newsId){
		News news=newsService.selectOneNewsDetail(newsId);
		String content=news.getContent();
		String[] array=content.split("%&%");
		news.setContent_str(array);
		
		//去除开头的
		List<String> list=new ArrayList<String>(Arrays.asList(array));
		int index=0;
		for(String item:list){
			if (item.trim().equals("")) {
				index++;
			}
			else{
				break;
			}
		}
		for (int j=0;j<index;j++) {
			list.remove(0);
		}
		news.setContent("");
		news.setContent_str(list.toArray(new String[list.size()]));
		//异步线程让资讯浏览数+1
		newsService.addNewsViewCount(newsId);
		return news;
	}
	/**
	 *寻找置顶消息
	 * @return
	 */
	@RequestMapping(value="/top",method=RequestMethod.POST)
	@ResponseBody
	public List<News> selectTopNews(@RequestBody(required=false) RowBounds rowBounds){
		return newsService.selectTopJoinAuthor(rowBounds);
	}
	/**
	 * 资讯 点赞/取消
	 * @return
	 */
	@RequestMapping(value="/addlike/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public int addNewsLikeCount(@RequestBody NewsLike newsLike){
		return newsService.addNewsLikeCount(newsLike);
	}
	
	@RequestMapping(value="/banner",method=RequestMethod.GET)
	@ResponseBody
	public List<News> selectTopBanner(){
		return newsService.selectBanner();
	}
	
}



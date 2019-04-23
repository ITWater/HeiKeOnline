package top.aiteyou.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.HitMorningImage;
import top.aiteyou.entity.HitMorningText;
import top.aiteyou.entity.User;
import top.aiteyou.mapper.HitMorningImageMapper;
import top.aiteyou.mapper.HitMorningTextMapper;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.transform.HitMorningContext;
import top.aiteyou.transform.UserSign;

/**
	*@author :tb
	*@version 2018年4月8日 下午1:27:31
*/

@Service
public class HitMorningServiceImpl implements HitMorningService{
	
	@Autowired
	private HitMorningTextMapper hitMorningTextMapper=null;
	@Autowired
	private HitMorningImageMapper hitMorningImageMapper=null;
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private RedisTemplate redisTemplate=null;
	private List<HitMorningText> texts=null;
	private List<HitMorningImage> images=null;
	
	//保存今日打晨卡排行榜
	private List<UserSign> userSigns=new ArrayList<>();
	private Calendar calendar=Calendar.getInstance();
	/**
	 * 获取排行榜-cache
	 */
	@Override
	public List<UserSign> getRanking() {
		return userSigns;
	}
	/**
	 * 在之后用户数量超过xk时，考虑同步锁
	 */
	@Override
	public HitMorningContext morningSignIn(int userId) {
		calendar.setTime(new Date());
		int nowdays=calendar.get(Calendar.DAY_OF_YEAR);
		int years=calendar.get(Calendar.YEAR);
		//弹出
		List<UserSign> userSigns_cache=(List<UserSign>) redisTemplate.opsForList().leftPop("hitmorning_ranking_year"+years+"days_"+nowdays);
		
		if (texts==null || images==null) {
			texts=getTexts();
			images=getImages();
		}
		if (userSigns_cache == null) {//如果是新的一天
			userSigns_cache=new ArrayList<>();
		}
		//判断是否是真实用户
		User user=userMapper.selectBaseData(userId);
		UserSign userSign=new UserSign(user,new Timestamp(System.currentTimeMillis()));
		if (user==null) {
			//存入缓存
			redisTemplate.opsForList().leftPush("hitmorning_ranking_year"+years+"days_"+nowdays, userSigns_cache);
			return null;
		}
		//只存有用户信息的
		if (user.getName()!=null && !user.getName().equals("")) {
			if (user.getIcon()!=null && !user.getIcon().equals("")) {
					//用户信息
					userSigns_cache.add(userSign);
					userSigns=userSigns_cache;
			}
		}
		//存入缓存
		redisTemplate.opsForList().leftPush("hitmorning_ranking_year"+years+"days_"+nowdays, userSigns_cache);
		
		
		Random random=new  Random();
		int randomNumber=random.nextInt(texts.size() > images.size() ? images.size():texts.size());
		
		HitMorningContext hitMorningContext=new HitMorningContext(userSigns_cache.size(),texts.get(randomNumber),images.get(randomNumber));
		hitMorningContext.setTime(new Timestamp(System.currentTimeMillis()));
		
		
		return hitMorningContext;
		
	}
	
	/**
	 * 从缓存or DB 获取最近100条以内的文字 
	 * @return
	 */
	@Cacheable(value="hitmorningtextinfo",key="hitmorning_texts")
	private List<HitMorningText> getTexts(){
		return hitMorningTextMapper.selectRecently();
	}
	
	/**
	 * 从缓存or DB 获取最近100条以内的图片
	 * @return
	 */
	@Cacheable(value="hitmorningimageinfo",key="hitmorning_images")
	private List<HitMorningImage> getImages(){
		return hitMorningImageMapper.selectRecently();
	}


}



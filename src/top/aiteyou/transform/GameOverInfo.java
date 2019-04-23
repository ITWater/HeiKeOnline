package top.aiteyou.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 赛事结束通知
	*@author :tb
	*@version 2018年4月24日 下午6:53:53
*/

public class GameOverInfo {
	private Integer gameId;
	
	private Integer[] infoRange;
	
	private String info;
	
	
	
	private static List<String> temp_infos;
	
	static{
		temp_infos=new ArrayList<String>();
		temp_infos.add("我脱衣服得到黄牌他们并不生气，他们看到了我的肌肉才感到嫉妒。\r\n  ——巴神");
		temp_infos.add("比赛后我去找我妈妈，那是最美好的时刻。我告诉她，进球是献给她的。\r\n ——巴神");
		temp_infos.add("没有我的世界杯不值得一看，所以没必要期待它的到来。 ——伊布");
		temp_infos.add("瑞典和葡萄牙谁能晋级世界杯?只有上帝才知道答案吧，你现在就在跟上帝说话。\r\n ——伊布");
//		temp_infos.add("我自己认为，我是最出色的，压根就不需要金球奖来证明我是世界第一。\r\n ——伊布");
//		temp_infos.add("我是一个多愁善感的人，我每次获得成功上台领奖的时候，我脑海里都会浮现出我父亲的身影，我想告诉他，爸爸我成功了，你知道吗? \r\n——C罗");
		temp_infos.add("有些人把足球等同与生死，我对此深表失望。我敢向你保证足球远远、远远地超越生死。\r\n ——比尔·香克利");
		temp_infos.add("能否成为贝利或者更伟大的人，对我来说并不重要。重要的是我要踢球、训练、不放弃一分一秒。\r\n ——马拉多纳");
//		temp_infos.add("一个足球队就像一部机器，只有每个部件都运转起来，才能收到最大的效果。\r\n ——卡西奥");
//		temp_infos.add("梅西不是巨星，他只想踢得好一些，更好一些。所以，请球迷不要相信梅西能够制造奇迹，但是一定要相信梅西不怕困难，无畏逆境。\r\n ——梅西");
		temp_infos.add("他是宠儿，也是弃儿 他被追逐，也被放逐 他在失重中重获尊重，更在尊重中获得更多的尊重 他将离开，也永远不会离开 他叫大卫 贝克汉姆 他是一个牵动世界的人，而这次，他是一 个动人的球员。\r\n 《天下足球》");
		temp_infos.add("如果我只想要看昔日战友今日兵戈相见， 如果我只想要看卧薪尝胆再起东山， 如果我只想要看十年蛰伏一朝雪恨， 如果我只想要看公平的背叛与折戟的壮志，痴心不改的守望与不信命数的抗争， 如果我只想要看 英雄的自我救赎与浴火涅槃…… \r\n就像胡美满说的，“我只要去看足球就好了。");
	}
	

	public GameOverInfo() {
		this.gameId = -1;
		this.infoRange =new Integer[]{-1};
		Random random=new Random();
		int value=random.nextInt(temp_infos.size());
		info=temp_infos.get(value);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer[] getInfoRange() {
		return infoRange;
	}

	public void setInfoRange(Integer[] infoRange) {
		this.infoRange = infoRange;
	}

	
}



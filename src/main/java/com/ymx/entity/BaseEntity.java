
package com.ymx.entity;

import java.sql.Timestamp;




/**
 * 
 * @classDesc: 功能描述:(封装一些相同字段和属性)
 * @author: 蚂蚁课堂创始人-余胜军
 * @QQ: 644064779
 * @QQ粉丝群: 116295598
 * @createTime: 2017年10月24日 下午9:20:15
 * @version: v1.0
 * @copyright:每特学院(蚂蚁课堂)上海每特教育科技有限公司
 */

public class BaseEntity {

	private int id;

	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id=id;
	}
}

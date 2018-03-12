package com.hxq.huaxiaoqclient.model;

import java.io.Serializable;

public class HomeData implements Serializable{
	private Long id;

	private long businessId;

	private String businessTitle;

	private Integer businessPlateform;

	private String picUrl;

	private Integer salesPrice;

	private Integer originalPrice;

	private Integer couponPrice;

	private String linkUrl;

	private Integer couponRemainCount;

	private String addTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}

	public String getBusinessTitle() {
		return businessTitle;
	}

	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle == null ? null : businessTitle
				.trim();
	}

	public Integer getBusinessPlateform() {
		return businessPlateform;
	}

	public void setBusinessPlateform(Integer businessPlateform) {
		this.businessPlateform = businessPlateform;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Integer salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(Integer couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getCouponRemainCount() {
		return couponRemainCount;
	}

	public void setCouponRemainCount(Integer couponRemainCount) {
		this.couponRemainCount = couponRemainCount;
	}

}
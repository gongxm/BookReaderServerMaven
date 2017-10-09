package com.gongxm.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "book_info_and_chapter_list_rules")
public class BookInfoAndChapterListRules implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	private int id;
	@Expose
	private String rulesName;// 规则名称
	// 1.标题 2.作者 3.类别 4.状态 5.封面 6.简介7.目录链接正则 8.目录标题正则
	@Expose
	private String titleRegex;
	@Expose
	private String authorRegex;
	@Expose
	private String categoryRegex;
	@Expose
	private String statusRegex;
	@Expose
	private String coverRegex;
	@Expose
	private String shortIntroduceRegex;
	@Expose
	private String contentDivClass;// 目录内容区域正则
	@Expose
	private String contentDivRegex;// 正文内容区域正则

	@Transient // 不需要加入数据库
	private int book_list_rules_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitleRegex() {
		return titleRegex;
	}

	public void setTitleRegex(String titleRegex) {
		this.titleRegex = titleRegex;
	}

	public String getAuthorRegex() {
		return authorRegex;
	}

	public void setAuthorRegex(String authorRegex) {
		this.authorRegex = authorRegex;
	}

	public String getCategoryRegex() {
		return categoryRegex;
	}

	public void setCategoryRegex(String categoryRegex) {
		this.categoryRegex = categoryRegex;
	}

	public String getStatusRegex() {
		return statusRegex;
	}

	public void setStatusRegex(String statusRegex) {
		this.statusRegex = statusRegex;
	}

	public String getCoverRegex() {
		return coverRegex;
	}

	public void setCoverRegex(String coverRegex) {
		this.coverRegex = coverRegex;
	}

	public String getShortIntroduceRegex() {
		return shortIntroduceRegex;
	}

	public void setShortIntroduceRegex(String shortIntroduceRegex) {
		this.shortIntroduceRegex = shortIntroduceRegex;
	}

	public int getBook_list_rules_id() {
		return book_list_rules_id;
	}

	public void setBook_list_rules_id(int book_list_rules_id) {
		this.book_list_rules_id = book_list_rules_id;
	}

	public String getContentDivClass() {
		return contentDivClass;
	}

	public void setContentDivClass(String contentDivClass) {
		this.contentDivClass = contentDivClass;
	}

	public String getContentDivRegex() {
		return contentDivRegex;
	}

	public void setContentDivRegex(String contentDivRegex) {
		this.contentDivRegex = contentDivRegex;
	}

	public String getRulesName() {
		return rulesName;
	}

	public void setRulesName(String rulesName) {
		this.rulesName = rulesName;
	}

}

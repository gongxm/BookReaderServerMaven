package com.gongxm.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "books")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	// @Expose(serialize = true, deserialize = false) // serialize:
	// 可以被序列化,deserialize:可以被反序列化
	@Expose // @Expose:默认为同时可以序列化和反序列化
	private String id;
	@Expose
	private String book_name;
	@Expose
	private String author;
	@Expose
	private String category;
	@Expose
	private String cover;
	@Expose
	private String status;
	@Expose
	private String book_link;
	@Type(type = "text")
	@Expose
	private String shortIntroduce;
	@Expose
	private int rulesId; // 采集规则ID
	@Expose
	private int collectStatus;// 采集状态

	// 新增最新章节和最后更新时间
	@Expose
	private String lastChapter;
	@Expose
	private long lastUpdateTime;
	// 章节总数量
	@Expose
	private int chapterCount;

	public Book() {
	}

	public Book(String bookId, String book_name, String author, String category, String status, String cover,
			String shortIntroduce, String book_link, int rulesId) {
		super();
		this.id = bookId;
		this.book_name = book_name;
		this.category = category;
		this.author = author;
		this.cover = cover;
		this.status = status;
		this.shortIntroduce = shortIntroduce;
		this.book_link = book_link;
		this.rulesId = rulesId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getBook_link() {
		return book_link;
	}

	public void setBook_link(String book_link) {
		this.book_link = book_link;
	}

	public String getShortIntroduce() {
		return shortIntroduce;
	}

	public void setShortIntroduce(String shortIntroduce) {
		this.shortIntroduce = shortIntroduce;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRulesId() {
		return rulesId;
	}

	public void setRulesId(int rulesId) {
		this.rulesId = rulesId;
	}

	public int getCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(int collectStatus) {
		this.collectStatus = collectStatus;
	}

	public String getLastChapter() {
		return lastChapter;
	}

	public void setLastChapter(String lastChapter) {
		this.lastChapter = lastChapter;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getChapterCount() {
		return chapterCount;
	}

	public void setChapterCount(int chapterCount) {
		this.chapterCount = chapterCount;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((book_name == null) ? 0 : book_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (book_name == null) {
			if (other.book_name != null)
				return false;
		} else if (!book_name.equals(other.book_name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", book_name=" + book_name + ", author=" + author + ", category=" + category
				+ ", cover=" + cover + ", status=" + status + ", book_link=" + book_link + ", shortIntroduce="
				+ shortIntroduce + ", rulesId=" + rulesId + ", collectStatus=" + collectStatus + ", lastChapter="
				+ lastChapter + ", lastUpdateTime=" + lastUpdateTime + ", chapterCount=" + chapterCount + "]";
	}

}
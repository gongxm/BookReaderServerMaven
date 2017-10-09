package com.gongxm.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import com.google.gson.annotations.Expose;


@Entity
@Table(name = "books")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Expose(serialize = true, deserialize = false) // serialize:
	// 可以被序列化,deserialize:可以被反序列化
	@Expose // @Expose:默认为同时可以序列化和反序列化
	private int id;
	@Column
	@Expose
	private String book_name;
	@Column
	@Expose
	private String author;
	@Column
	@Expose
	private String category;
	@Column
	@Expose
	private String cover;
	@Column
	@Expose
	private String status;
	@Column
	@Expose
	private String book_link;
	@Column
	@Type(type = "text")
	@Expose
	private String shortIntroduce;

	@OneToMany(targetEntity = BookChapter.class)
	@Fetch(FetchMode.SELECT)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@JoinColumn(name = "book_id")
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	private Set<BookChapter> chapters = new HashSet<BookChapter>();

	public Book() {
	}

	public Book(String book_name, String author, String category, String status, String cover, String shortIntroduce,
			String book_link) {
		super();
		this.book_name = book_name;
		this.category = category;
		this.author = author;
		this.cover = cover;
		this.status = status;
		this.shortIntroduce = shortIntroduce;
		this.book_link = book_link;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Set<BookChapter> getChapters() {
		return chapters;
	}

	public void setChapters(Set<BookChapter> chapters) {
		this.chapters = chapters;
	}


	@Override
	public String toString() {
		return "Book [book_name=" + book_name + ", author=" + author + ", category=" + category + ", cover=" + cover
				+ ", status=" + status + ", book_link=" + book_link + ", shortIntroduce=" + shortIntroduce + "]";
	}

}
package api.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetLombok {
	
	public PetLombok(Integer id, CategoryCustom category, String name, List<String> photoUrls, List<TagCustom> tags,
			String status) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.photoUrls = photoUrls;
		this.tags = tags;
		this.status = status;
	}

	private Integer id;
	private CategoryCustom category;
	private String name;
	List<String> photoUrls;
	List<TagCustom> tags;
	private String status;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CategoryCustom getCategory() {
		return category;
	}

	public void setCategory(CategoryCustom category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public List<TagCustom> getTags() {
		return tags;
	}

	public void setTags(List<TagCustom> tags) {
		this.tags = tags;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class CategoryCustom
	{
		public CategoryCustom(Integer id, String name) {
			this.id = id;
			this.name = name;
		}
		
		private Integer id;
		private String name;
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class TagCustom
	{
		public TagCustom(Integer id, String name) {
			this.id = id;
			this.name = name;
		}
		
		private Integer id;
		private String name;
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}

}

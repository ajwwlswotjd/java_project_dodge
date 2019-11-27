package domain;

public class UserVO {
	private String id;
	private String name;
	private int max_score;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMaxScore() {
		return this.max_score;
	}
	public void setMaxScore(int max_score) {
		this.max_score = max_score;
	}
}
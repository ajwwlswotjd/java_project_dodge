package domain;

public class UserVO {
	private String id;
	private String name;
	private int max_score;
	private int idx;
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
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getIdx() {
		return this.idx;
	}
	
	@Override
	public String toString() {
		return this.idx+"위 : "+this.name+" ( "+this.max_score+" 점)";
	}
}
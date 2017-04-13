package commons.constant;

public enum CateogryType {
	ARTICLE(0), //文章分类
	PRODUCT(1), //商品分类
	COMMUNITY(2),  //社群(圈子,小组)
	COMMUNITYCATEGORY(3), //社群分类
	TOPIC(4), //专题
	TOPICCATEOGRY(5), //专题分类
	EXAM(6), //试卷
	EXAMCATEGORY(7),  //试卷分类
	VIDEO(8); //视频

	private int index;

	private CateogryType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}

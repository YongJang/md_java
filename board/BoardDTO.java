package board;

public class BoardDTO {
	private int num;
	private String title;
	private String writer;
	private String content;
	private String regdate;
	private int readcount;
	private int replyqty;
	private int ref;
	private int re_step;
	private int re_level;
	private String filename;
	private int filesize;
	public BoardDTO() {
		super();
	}
	public BoardDTO(int num, String title, String writer, String content,
			String regdate, int readcount, int replyqty, int ref, int re_step,
			int re_level, String filename, int filesize) {
		super();
		this.num = num;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.regdate = regdate;
		this.readcount = readcount;
		this.replyqty = replyqty;
		this.ref = ref;
		this.re_step = re_step;
		this.re_level = re_level;
		this.filename = filename;
		this.filesize = filesize;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public int getReplyqty() {
		return replyqty;
	}
	public void setReplyqty(int replyqty) {
		this.replyqty = replyqty;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getRe_step() {
		return re_step;
	}
	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}
	public int getRe_level() {
		return re_level;
	}
	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

}

package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CommentBean;
import bean.FollowBean;
import bean.LikesBean;
import bean.NoteBean;
import bean.UserBean;
import dao.CollectDao;
import dao.CommentDao;
import dao.FollowDao;
import dao.LikesDao;
import dao.NoteDao;
import dao.UserDao;

/**
 * Servlet implementation class FollowNoteServlet
 */
@WebServlet("/FollowNoteServlet")
public class FollowNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowNoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setCharacterEncoding("utf-8");
		LikesDao like = new LikesDao();
		UserDao userdao = new UserDao();
		CollectDao collect = new CollectDao();
		CommentDao comment = new CommentDao();
		CommentBean combean = new CommentBean();
		NoteDao notedao = new NoteDao();
		FollowDao follow  = new FollowDao();
		List<NoteBean> noteList = new ArrayList<NoteBean>();
		//当前用户id
		String userid = request.getParameter("userId");
		String noteid = request.getParameter("noteId");
		String islike = request.getParameter("islike"); 
		String isCollect = request.getParameter("iscollect");
		String isComment = request.getParameter("iscomment");
		String isfollow = request.getParameter("isfollow");
		String commentdetail = request.getParameter("commentdetail");
		//获取当前用户赞过的笔记
		List<NoteBean> noteLike = new ArrayList<NoteBean>();
		//获取当前用户收藏过的笔记
		List<NoteBean> noteCollect = new ArrayList<NoteBean>();
		//List<FollowBean> notefollow = new ArrayList<FollowBean>();
		if(userid!=null) {
			int userId = Integer.parseInt(userid);
			UserBean user = new UserBean();
			user=userdao.getUserById(userId);
			noteLike=like.getLikeNoteList(user.getUserId());
			noteCollect=collect.getCollectNoteList(user);
			//notefollow = follow.getFollow(user);
			System.out.println("notelike"+noteLike.size());
			if(noteid!=null) {
				int noteId = Integer.parseInt(noteid);
				NoteBean no = new NoteBean();
				no= notedao.getNoteById(noteId);
				int followed=no.getUser().getUserId();
				System.out.println("userID"+userId+"followedid:"+followed);
				if(isfollow!=null&&isfollow.equals("false")) {
					System.out.println(isfollow);
					System.out.println("userID"+userId+"关注:"+followed);
					follow.addFollow(userId, followed);
				}else if(isfollow!=null&&isfollow.equals("true")){
		    	  System.out.println(isfollow);
		    	  System.out.println("userID"+userId+"取消关注:"+followed);
		    	follow.deleteFollow(userId, followed); 
		      }
				if(islike!=null&&islike.equals("false")) {
					System.out.println("userID"+userId+"点赞:"+noteId);
					like.addLike(userId, noteId);
				}else if(islike!=null&&islike.equals("true")) {
					System.out.println("userID"+userId+"取消赞:"+noteId);
					like.deleteLike(userId, noteId);  
				}
				if(isCollect!=null&&isCollect.equals("false")) {
					System.out.println("collect"+userId+"收藏"+noteId);
					collect.addCollect(userId, noteId);
				}else if(isCollect!=null&&isCollect.equals("true")){
					System.out.println("collect"+userId+"取消收藏"+noteId);
					collect.deleteCollect(userId, noteId);  
				}
				if(isComment!=null&&isComment.equals("false")) {
					CommentBean com = new CommentBean();
					com.setUser(user);
					com.setNote(no);
					com.setCommentDetail(commentdetail);
					comment.addComment(com);
				}
			}
		//获取当前登陆的用户关注别人的数量
		int followcount = follow.getFollowCount(user.getUserId());
		if(followcount>0) {
			noteList= notedao.getNoteByuserId(userId);
			List<CommentBean> list = new ArrayList<CommentBean>();
			List<LikesBean> Like = new ArrayList<LikesBean>();
			for(NoteBean note:noteList) {
				
				int u=note.getUser().getUserId();
				//System.out.println("userId"+u);
				int a = note.getNoteId();
				int b = like.selectLike(a);
				int folcount=follow.getFollowCount(u);
				int fancount=follow.getFunCount(u);
				Like=like.getLikeList(u);
				int likecount = Like.size();	
				System.out.println(u+"点赞"+likecount);
				System.out.println("关注"+folcount);
				System.out.println("粉丝"+fancount);
				int c = comment.getCommentCount(a);
				int d = collect.selectCollectCount(a);
				int ilike = 0;int ifollow = 1;int icollect=0;
				list = comment.getCommentsBynoteId(a);
				note.setComment(list);
				note.setNoteLikeCount(b);
				note.setNoteCollectionCount(d);
				note.setNoteCommentCount(c);
				note.setUserContent(user);
				note.getUser().setLikeCount(likecount);
				note.getUser().setFanCount(fancount);
				note.getUser().setFollowCount(folcount);
				for(NoteBean nlike:noteLike) {
					//System.out.println(note.getNoteId()+"点赞"+nlike.getNoteId());
					if(note.getNoteId()==nlike.getNoteId()) {
						ilike=1;
						note.setLike(ilike);
						break;
					}else {
						ilike=0;
						note.setLike(ilike);
					}
				}
				for(NoteBean ncollect:noteCollect) {
					//System.out.println(note.getNoteId()+"点赞"+nlike.getNoteId());
					if(note.getNoteId()==ncollect.getNoteId()) {
						icollect=1;
						note.setIsCollect(icollect);
						break;
					}else {
						icollect=0;
						note.setIsCollect(icollect);
					}
				}
				//note.setLike(ilike);note.setIsFollow(ifollow);note.setIsCollect(icollect);
//				System.out.println(note.getNoteId()+"iscollect"+note.getIsCollect());
//				System.out.println(note.getNoteId()+"islike"+note.isLike());
				//System.out.println(note.getNoteLikeCount());
				System.out.println(u+"点赞"+note.getUser().getLikeCount());
				System.out.println(u+"关注"+note.getUser().getFollowCount());
				System.out.println(u+"粉丝"+note.getUser().getFanCount());

		}
		}
		}
		request.setAttribute("noteList", noteList);
		String jsonString="";
		jsonString = JsonTools.createJsonString("note",noteList);
		response.getWriter().append(jsonString).append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

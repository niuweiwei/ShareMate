package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.CommentBean;
import dao.CommentDao;
import dao.FollowDao;
import dao.NoteDao;
import dao.UserDao;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//笔记页底部评论
		String remark=request.getParameter("remark");
		if("selectComment".equals(remark)){
			int noteId=Integer.parseInt(request.getParameter("noteId"));
			List<CommentBean> commentList=new CommentDao().getCommentsBynoteId(noteId);
			JSONArray array=new JSONArray();
			for(CommentBean comment:commentList) {
				JSONObject obj=new JSONObject();
				obj.put("commentId", comment.getCommentId());
				obj.put("commentDetail", comment.getCommentDetail());
				
				SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
				obj.put("commentDate", sdf.format(comment.getCommentDate()));
				obj.put("commentLikeCount", new CommentDao().getLikeCount(comment.getCommentId()));
				FollowDao followdao=new FollowDao();
				int followCount = followdao.getFollowCount(comment.getUser().getUserId());
				int fanCount = followdao.getFunCount(comment.getUser().getUserId());
				int likeCount = new NoteDao().getLikeCount(comment.getUser().getUserId());
				obj.put("followCount", followCount);
				obj.put("fanCount", fanCount);
				obj.put("likeCount", likeCount);
				obj.put("introduce", comment.getUser().getUserIntro());
				obj.put("userId", comment.getUser().getUserId());
				obj.put("userName", comment.getUser().getUserName());
				obj.put("userPhoto", comment.getUser().getUserPhoto());
				array.put(obj);
			}
			response.getWriter().append(array.toString());
		}else if("addComment".equals(remark)){
			//添加评论
			int noteId=Integer.parseInt(request.getParameter("noteId"));
			String content=request.getParameter("commentDetail");
			int userId=Integer.parseInt(request.getParameter("userId"));
			CommentBean comment=new CommentBean();
			comment.setCommentDetail(content);
			comment.setNote(new NoteDao().getNoteById(noteId));
			comment.setUser(new UserDao().getUserById(userId));
			new CommentDao().addComment(comment);
			response.getWriter().append("添加成功");
		}else if("addLike".equals(remark)) {
			int userId=Integer.parseInt(request.getParameter("userId"));
			int commentId=Integer.parseInt(request.getParameter("commentId"));
			new CommentDao().clickLike(userId, commentId);
			response.getWriter().append("点赞成功");
		}else if("deleteLike".equals(remark)) {
			int userId=Integer.parseInt(request.getParameter("userId"));
			int commentId=Integer.parseInt(request.getParameter("commentId"));
			new CommentDao().cancelLike(userId, commentId);
			response.getWriter().append("取消赞");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

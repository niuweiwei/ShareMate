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

import bean.ReplyBean;
import dao.CommentDao;
import dao.ReplyDao;
import dao.UserDao;
import sun.rmi.runtime.Log;

/**
 * Servlet implementation class ReplyServlet
 */
@WebServlet("/ReplyServlet")
public class ReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String remark=request.getParameter("remark");
		if("selectReply".equals(remark)) {
			int commentId=Integer.parseInt(request.getParameter("commentId"));
			List<ReplyBean> replyList=new ReplyDao().getRepliesBycommentId(commentId);
			JSONArray array=new JSONArray();
			for(ReplyBean reply:replyList) {
				JSONObject obj=new JSONObject();
				obj.put("replyId", reply.getReplyId());
//				System.out.println( reply.getReplyId());
				obj.put("replyDetail",reply.getReplyDetail());
				SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
				obj.put("replyDate", sdf.format(reply.getReplyTime()));
				obj.put("replyLikeCount", new ReplyDao().getLikeCount(reply.getReplyId()));
				obj.put("userName", reply.getUser().getUserName());
				obj.put("userPhoto", reply.getUser().getUserPhoto());
				if(reply.getReReplyId()==0) {
					obj.put("reReplyName", 0+"");
				}else {
//					System.out.println(reply.getReReplyId());
					obj.put("reReplyName", new ReplyDao().getUserNameByReplyId(reply.getReReplyId()));
//					System.out.println(new ReplyDao().getUserNameByReplyId(reply.getReReplyId()));
				}
				array.put(obj);
			}
			response.getWriter().append(array.toString());
		}else if("addReply".equals(remark)) {
			int commentId=Integer.parseInt(request.getParameter("commentId"));
			String content=request.getParameter("replyDetail");
			int userId=Integer.parseInt(request.getParameter("userId"));
			ReplyBean reply=new ReplyBean();
			reply.setReplyDetail(content);
			reply.setUser(new UserDao().getUserById(userId));
			reply.setComment(new CommentDao().getCommentBycommentId(commentId));
			new ReplyDao().addCommentReply(reply);
			response.getWriter().append("回复成功");
		}else if("addReReply".equals(remark)) {
			int reReplyId=Integer.parseInt(request.getParameter("reReplyId"));
			String content=request.getParameter("replyDetail");
			int userId=Integer.parseInt(request.getParameter("userId"));
			ReplyBean reply=new ReplyBean();
			reply.setReplyDetail(content);
			reply.setUser(new UserDao().getUserById(userId));
			reply.setReReplyId(reReplyId);
			new ReplyDao().addReReply(reply);
			response.getWriter().append("回复成功");
		}else if("addLike".equals(remark)) {
			int userId=Integer.parseInt(request.getParameter("userId"));
			int replyId=Integer.parseInt(request.getParameter("replyId"));
			new ReplyDao().clickLike(userId, replyId);
			response.getWriter().append("点赞成功");
		}else if("deleteLike".equals(remark)) {
			int userId=Integer.parseInt(request.getParameter("userId"));
			int replyId=Integer.parseInt(request.getParameter("replyId"));
			new ReplyDao().cancelLike(userId, replyId);
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

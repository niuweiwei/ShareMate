package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import bean.CAndRBean;
import bean.ReplyBean;
import dao.CommentDao;
import dao.ReplyDao;
import dao.UserDao;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AddReplyServlet
 */
@WebServlet("/AddReplyServlet")
public class AddReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddReplyServlet() {
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
		
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String result = br.readLine();
		JSONObject object = JSONObject.fromObject(result);
		int tag = object.getInt("tag");
		int id = object.getInt("id");//获得要回复的是评论的id或回复的id
		int userId = object.getInt("userId");
		String content = object.getString("replyContent");
		
		ReplyBean reply = new ReplyBean();
		ReplyDao replyDao = new ReplyDao();
		reply.setUser(new UserDao().getUserById(userId));
		reply.setReplyDetail(content);
		
		if(tag == CAndRBean.COMMENT) {
			//表示该回复是对评论进行回复
			reply.setComment(new CommentDao().getCommentBycommentId(id));
			replyDao.addCommentReply(reply);
		}else if(tag==CAndRBean.REPLYCOMMENT||tag==CAndRBean.REPLYREPLY) {
			//表示该回复是对回复进行回复
			reply.setReReplyId(id);
			replyDao.addReReply(reply);
		}
		
		response.getWriter().append("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

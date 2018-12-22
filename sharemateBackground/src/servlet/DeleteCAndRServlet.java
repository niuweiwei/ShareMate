package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.CAndRBean;
import dao.CommentDao;
import dao.ReplyDao;

/**
 * Servlet implementation class DeleteCAndRServlet
 */
@WebServlet("/DeleteCAndRServlet")
public class DeleteCAndRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCAndRServlet() {
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
		int tag = Integer.parseInt(request.getParameter("tag"));
		int id = Integer.parseInt(request.getParameter("id"));
		
		if(tag==CAndRBean.COMMENT) {
			//删除评论
			CommentDao commentDao = new CommentDao();
			commentDao.removeComment(id);
		}else if (tag==CAndRBean.REPLYCOMMENT||tag==CAndRBean.REPLYREPLY) {
			//删除回复
			ReplyDao replyDao = new ReplyDao();
			replyDao.removeReply(id);
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

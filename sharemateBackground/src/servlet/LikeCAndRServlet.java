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
 * Servlet implementation class LikeCAndRServlet
 */
@WebServlet("/LikeCAndRServlet")
public class LikeCAndRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeCAndRServlet() {
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
		int userId = Integer.parseInt(request.getParameter("userId"));
		int tag = Integer.parseInt(request.getParameter("tag"));
		int id = Integer.parseInt(request.getParameter("id"));
		String action = request.getParameter("act");
		
		if("like".equals(action)) {
			if(tag == CAndRBean.COMMENT) {
				CommentDao commentDao = new CommentDao();
				commentDao.clickLike(userId,id);
			}else if(tag==CAndRBean.REPLYCOMMENT||tag==CAndRBean.REPLYREPLY) {
				ReplyDao replyDao = new ReplyDao();
				replyDao.clickLike(userId,id);
			}
		}else if("cancel".equals(action)) {
			if(tag == CAndRBean.COMMENT) {
				CommentDao commentDao = new CommentDao();
				commentDao.cancelLike(userId,id);
			}else if(tag==CAndRBean.REPLYCOMMENT||tag==CAndRBean.REPLYREPLY) {
				ReplyDao replyDao = new ReplyDao();
				replyDao.cancelLike(userId,id);
			}
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

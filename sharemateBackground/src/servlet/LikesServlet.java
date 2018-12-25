package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FollowBean;
import dao.FollowDao;
import dao.LikesDao;
import dao.UserDao;

/**
 * Servlet implementation class LikesBean
 */
@WebServlet("/LikesServlet")
public class LikesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesServlet() {
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
		int userId=Integer.parseInt(request.getParameter("userId"));
		int noteId=Integer.parseInt(request.getParameter("noteId"));
		LikesDao likesDao=new LikesDao();
		String msg="";
		if("addLike".equals(remark)) {
			likesDao.addLike(userId, noteId);
			msg="点赞成功";
		}else if("deleteLike".equals(remark)){
			likesDao.deleteLike(userId, noteId);
			msg="取消赞";
		}else if("judgeLike".equals(remark)){
			boolean isLike=likesDao.judgeLike(userId, noteId);
			if(isLike) {
				msg="已点赞";
			}else {
				msg="未点赞";
			}
		}
		response.getWriter().append(msg);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

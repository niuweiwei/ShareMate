package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FollowBean;
import dao.FollowDao;
import dao.UserDao;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowServlet() {
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
		int followId=Integer.parseInt(request.getParameter("followId"));
		FollowDao followDao=new FollowDao();
		String msg="";
		if("addFollow".equals(remark)) {
			followDao.addFollow(followId, userId);
			msg="关注成功";
		}else if("deleteFollow".equals(remark)){
			followDao.deleteFollow(followId, userId);
			msg="取消关注";
		}else if("judgeFollow".equals(remark)){
			FollowBean followBean=new FollowBean();
			followBean.setFollowId(followId);
			followBean.setUserbean(new UserDao().getUserById(userId));
			boolean isAttention=followDao.eachFollow(followBean);
			if(isAttention) {
				msg="已关注";
			}else {
				msg="未关注";
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

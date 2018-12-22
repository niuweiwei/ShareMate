package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bean.UserBean;
import dao.FollowDao;
import dao.NoteDao;
import dao.UserDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UserDao userdao = new UserDao();
		FollowDao followdao = new FollowDao();
		NoteDao notedao = new NoteDao();
		String userId = request.getParameter("userId");
		int uId = Integer.parseInt(userId);
		int followCount = followdao.getFollowCount(uId);
		int fanCount = followdao.getFunCount(uId);
		int likeCount = notedao.getLikeCount(uId);
		UserBean userBean = userdao.getUserById(uId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId",String.format("%06d", userBean.getUserId()));
		jsonObject.put("userName", userBean.getUserName());
		jsonObject.put("userPhoto",userBean.getUserPhoto());
		jsonObject.put("userSex", userBean.getUserSex());
		jsonObject.put("userBirth", userBean.getUserBirth());
		jsonObject.put("userAddress", userBean.getUserAddress());
		jsonObject.put("userIntro", userBean.getUserIntro());
		jsonObject.put("followCount", followCount);
		jsonObject.put("fanCount", fanCount);
		jsonObject.put("likeCount", likeCount);
		response.getWriter().append(jsonObject.toString()).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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
		int userId =Integer.parseInt(request.getParameter("userId")) ;
		String remark=request.getParameter("remark");
		if("userlikeComment".equals(remark)){
			UserDao userDao=new UserDao();
			List<Integer> list=userDao.getLikeComment(userId);	
			JSONArray array=new JSONArray();
			for(Integer a:list) {
				JSONObject obj=new JSONObject();
				obj.put("commentId", a);
				array.put(obj);
			}
			response.getWriter().append(array.toString());
		}else if("userlikeReply".equals(remark)) {
			UserDao userDao=new UserDao();
			List<Integer> list=userDao.getLikeReply(userId);
			
			JSONArray array=new JSONArray();
			for(Integer a:list) {
//				System.out.println(a);
				JSONObject obj=new JSONObject();
				obj.put("replyId", a);
				array.put(obj);
			}
			response.getWriter().append(array.toString());
		}else if("selectUser".equals(remark)) {
			String userPhotoPath=new UserDao().getUserById(userId).getUserPhoto();
			String userName=new UserDao().getUserById(userId).getUserName();
			JSONObject obj=new JSONObject();
			obj.put("userPhoto", userPhotoPath);
			obj.put("userName", userName);
			response.getWriter().append(obj.toString());
		}else {
			int followCount = followdao.getFollowCount(userId);
			int fanCount = followdao.getFunCount(userId);
			int likeCount = notedao.getLikeCount(userId);
			UserBean userBean = userdao.getUserById(userId);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId",userBean.getUserId());
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

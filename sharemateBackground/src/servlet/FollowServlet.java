package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.FollowBean;
import bean.UserBean;
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String remark=request.getParameter("remark");
		if(remark == null) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			FollowDao followDao = new FollowDao();
			UserDao userDao = new UserDao();
			HashMap<FollowBean,Boolean> map = followDao.getFollows(userId);
			JSONArray array = new JSONArray();
			ArrayList <FollowBean> list = new ArrayList<>(map.keySet());
			
			for(FollowBean follow : list) {
				JSONObject object = new JSONObject();
				UserBean user = userDao.getUserById(follow.getFollowId());
				//得到该粉丝用户的id
				object.put("fansId", follow.getFollowId());
				//得到粉丝用户的昵称
				object.put("fansName", user.getUserName());
				//得到粉丝用户的头像路径
				object.put("fansPhotoPath",user.getUserPhoto());
				//得到关注的时间
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
				object.put("followDate",sdf.format(follow.getDate()));
				//得到该用户是否关注了该粉丝
				object.put("isFollow", map.get(follow));
				array.put(object);
			}
			response.getWriter().append(array.toString()).append(request.getContextPath());
		}else {
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

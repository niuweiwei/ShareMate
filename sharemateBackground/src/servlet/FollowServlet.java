package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.FollowBean;
import dao.FollowDao;
import dao.NoteDao;
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
		String fid=request.getParameter("followId");
		if(fid==null) {
			FollowDao followDao = new FollowDao();
			NoteDao noteDao = new NoteDao();
			JSONArray array = new JSONArray();
			HashMap<FollowBean,Boolean> map = followDao.getFollows(userId);
			ArrayList <FollowBean> list = new ArrayList<>(map.keySet());
			
			for(FollowBean follow : list) {
				JSONObject object = new JSONObject();
				//得到该粉丝用户的id
				object.put("fansId",follow.getUserbean().getUserId());
				//得到粉丝用户的昵称
				object.put("fansName", follow.getUserbean().getUserName());
				//得到粉丝用户的头像路径
				object.put("fansPhotoPath",follow.getUserbean().getUserPhoto());
				//得到粉丝用户的简介
				object.put("fansIntroduce", follow.getUserbean().getUserIntro());
				//得到粉丝用户的关注数、粉丝数、赞数
				int fanCount = followDao.getFunCount(follow.getUserbean().getUserId());
				int followCount = followDao.getFollowCount(follow.getUserbean().getUserId());
				int likeCount = noteDao.getLikeCount(follow.getUserbean().getUserId());
				object.put("fanCount", fanCount);
				object.put("followCount", followCount);
				object.put("likeCount", likeCount);
				//得到关注的时间
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
				object.put("followDate",sdf.format(follow.getDate()));
				//得到该用户是否关注了该粉丝
				object.put("isFollow", map.get(follow));
				array.put(object);
			}
			response.getWriter().append(array.toString()).append(request.getContextPath());
		}else {
			int followId=Integer.parseInt(fid);
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
				boolean isAttention=followDao.eachFan(followBean);
				System.out.println(isAttention);
				if(isAttention) {
					msg="已关注";
				}else {
					msg="未关注";
				}
				System.out.println(msg);
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

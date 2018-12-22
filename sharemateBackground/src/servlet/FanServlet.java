package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Servlet implementation class FanServlet
 */
@WebServlet("/FanServlet")
public class FanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int userId = Integer.parseInt(request.getParameter("userId"));
		FollowDao followdao = new FollowDao();
		NoteDao notedao = new NoteDao();
		List<FollowBean> fanList = new ArrayList<>();
		fanList = followdao.getFanList(userId);
		JSONArray array = new JSONArray();
		for(FollowBean follow:fanList) {
			int noteCount = notedao.getNoteCount(follow.getUserbean().getUserId());
			int fanCount = followdao.getFunCount(follow.getUserbean().getUserId());
			int followCount = followdao.getFollowCount(follow.getUserbean().getUserId());
			int likeCount = notedao.getLikeCount(follow.getUserbean().getUserId());
			JSONObject object = new JSONObject();
			object.put("userId",String.format("%06d",follow.getUserbean().getUserId()));
			object.put("userName",follow.getUserbean().getUserName());
			object.put("userPhoto", follow.getUserbean().getUserPhoto());
			object.put("userIntro", follow.getUserbean().getUserIntro());
			object.put("status", follow.isStatus());
			object.put("noteCount", noteCount);
			object.put("fanCount", fanCount);
			object.put("followCount", followCount);
			object.put("likeCount", likeCount);
			array.put(object);
		}
		response.getWriter().append(array.toString()).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

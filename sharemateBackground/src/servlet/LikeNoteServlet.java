package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.LikesBean;
import dao.LikesDao;

/**
 * Servlet implementation class LikeNoteServlet
 */
@WebServlet("/LikeNoteServlet")
public class LikeNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeNoteServlet() {
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
		LikesDao likesDao = new LikesDao();
		//得到该用户发布的笔记的所有赞
		List<LikesBean> likeList = likesDao.getLikeList(userId);
		Collections.sort(likeList, new DateComparator());
		JSONArray array = new JSONArray();
		for(LikesBean like:likeList) {
			JSONObject object = new JSONObject();
			object.put("userPhoto", like.getUser().getUserPhoto());
			object.put("userName",like.getUser().getUserName());
			object.put("noteId", like.getNote().getNoteId());
			object.put("notePhoto", like.getNote().getNoteImage());
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
			object.put("likeDate",sdf.format(like.getDate()));
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
	
	private class DateComparator implements Comparator<LikesBean>{

		@Override
		public int compare(LikesBean o1, LikesBean o2) {
			// TODO Auto-generated method stub
			Date date1 = o1.getDate();
			Date date2 = o2.getDate();
			if(date1.after(date2)) 
				return -1;
			else if(date1.before(date2))
				return 1;
			else
				return 0;
		}
		
	}

}

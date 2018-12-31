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

import bean.CAndRBean;
import dao.CAndRDao;
import dao.CommentDao;
import dao.FollowDao;
import dao.NoteDao;
import dao.ReplyDao;

/**
 * Servlet implementation class CAndRServlet
 */
@WebServlet("/CAndRServlet")
public class CAndRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CAndRServlet() {
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
		CAndRDao dao = new CAndRDao();
		FollowDao followDao = new FollowDao();
		NoteDao noteDao = new NoteDao();
		List<CAndRBean> list = dao.getCAndBeanList(userId);
		Collections.sort(list, new DateComparator());
		JSONArray array = new JSONArray();
		for(CAndRBean bean : list) {
			JSONObject object = new JSONObject();
			
			int tag = bean.getTag();
			int id = bean.getId();
			boolean isLike = false;
			if(tag == CAndRBean.COMMENT) {
				isLike = new CommentDao().isLike(userId, id);
			}else if(tag ==CAndRBean.REPLYCOMMENT || tag==CAndRBean.REPLYREPLY) {
				isLike = new ReplyDao().isLike(userId, id);
			}
	
			object.put("tag", tag);
			object.put("id", id);
			int fanCount = followDao.getFunCount(bean.getPublisher().getUserId());
			int followCount = followDao.getFollowCount(bean.getPublisher().getUserId());
			int likeCount = noteDao.getLikeCount(bean.getPublisher().getUserId());
			object.put("publisherId", bean.getPublisher().getUserId());
			object.put("publisherName", bean.getPublisher().getUserName());
			object.put("publisherPhotoPath", bean.getPublisher().getUserPhoto());
			object.put("publisherIntro", bean.getPublisher().getUserIntro());
			object.put("fanCount", fanCount);
			object.put("followCount", followCount);
			object.put("likeCount", likeCount);
			object.put("userId", bean.getUser().getUserId());
			object.put("userName",bean.getUser().getUserName());
			object.put("noteId", bean.getNoteId());
			object.put("notePhotoPath", bean.getNoteImage());
			object.put("content", bean.getContent());
			object.put("arguedId", bean.getArguedId());
			object.put("isLike", isLike);
			String argued = bean.getArgued();
			if(argued==null) {
				object.put("argued", "");
			}else {
				object.put("argued", bean.getArgued());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
			String date = sdf.format(bean.getDate());
			object.put("date", date);
//			System.out.println(object.toString());
			array.put(object);
		}
		System.out.println(array.length());

		response.getWriter().append(array.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private class DateComparator implements Comparator<CAndRBean>{

		@Override
		public int compare(CAndRBean o1, CAndRBean o2) {
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

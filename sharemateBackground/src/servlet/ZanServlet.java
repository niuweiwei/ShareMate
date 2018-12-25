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

import bean.NoteBean;
import dao.LikesDao;

/**
 * Servlet implementation class ZanServlet
 */
@WebServlet("/ZanServlet")
public class ZanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<NoteBean> noteList = new ArrayList<>();
		String userId = request.getParameter("userId");
		int uId = Integer.parseInt(userId);
		LikesDao likedao = new LikesDao();
		noteList = likedao.getLikeNoteList(uId);
		JSONArray noteArray = new JSONArray();
		for(NoteBean noteBean:noteList) {
			JSONObject noteObject = new JSONObject();
			noteObject.put("notePhoto", noteBean.getNoteImage());
			noteObject.put("noteTitle", noteBean.getNoteTitle());
			noteArray.put(noteObject);
		}
		response.getWriter().append(noteArray.toString()).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

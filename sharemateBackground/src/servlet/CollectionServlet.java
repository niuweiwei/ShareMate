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
import dao.NoteDao;

/**
 * Servlet implementation class CollectionServlet
 */
@WebServlet("/CollectionServlet")
public class CollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<NoteBean> collectList = new ArrayList<>();
		int userId = Integer.parseInt(request.getParameter("userId"));
		NoteDao notedao = new NoteDao();
		collectList = notedao.getCollectList(userId);
		JSONArray noteArray = new JSONArray();
		for(NoteBean noteBean:collectList) {
			JSONObject noteObject = new JSONObject();
			noteObject.put("noteId", noteBean.getNoteId());
			noteObject.put("noteTitle", noteBean.getNoteTitle());
			noteObject.put("notePhoto", noteBean.getNoteImage());
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

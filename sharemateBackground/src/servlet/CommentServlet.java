package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.CommentBean;
import dao.CommentDao;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
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
		//笔记页底部评论
		int noteId=Integer.parseInt(request.getParameter("noteId"));
		List<CommentBean> commentList=new CommentDao().getCommentsBynoteId(noteId);
		JSONArray array=new JSONArray();
		for(CommentBean comment:commentList) {
			JSONObject obj=new JSONObject();
			obj.put("commentDetail", comment.getCommentDetail());
			
			SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
			obj.put("commentDate", sdf.format(comment.getCommentDate()));
			
			obj.put("commentLikeCount", comment.getCommentLikeCount());
			obj.put("userName", comment.getUser().getUserName());
			obj.put("userPhoto", comment.getUser().getUserPhoto());
			array.put(obj);
		}
		response.getWriter().append(array.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

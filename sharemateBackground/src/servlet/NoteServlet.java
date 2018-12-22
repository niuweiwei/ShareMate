package servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.CommentBean;
import bean.NoteBean;
import bean.ReplyBean;
import bean.UserBean;
import dao.CollectDao;
import dao.CommentDao;
import dao.LikesDao;
import dao.NoteDao;
import dao.ReplyDao;
import dao.UserDao;

/**
 * Servlet implementation class NoteServlet
 */
@WebServlet("/NoteServlet")
public class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int noteId=Integer.parseInt(request.getParameter("noteId"));
//		int noteId=1;
		//把数据编码成JSON格式
		JSONObject noteObj=new JSONObject();
		NoteDao noteDao=new NoteDao();
		NoteBean noteBean=noteDao.getNoteById(noteId);
		noteObj.put("noteTitle", noteBean.getNoteTitle());
		noteObj.put("noteDetail", noteBean.getNoteDetail());
		noteObj.put("noteImage", noteBean.getNoteImage());
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd hh:mm");
		noteObj.put("noteDate", sdf.format(noteBean.getNoteDate()));
		
		UserDao userDao=new UserDao();
		UserBean userBean=userDao.getUserById(noteBean.getUser().getUserId());
		noteObj.put("userId",noteBean.getUser().getUserId());
		noteObj.put("userPhoto",userBean.getUserPhoto());
		noteObj.put("userName",userBean.getUserName());
		
		LikesDao likeDao=new LikesDao();
		noteObj.put("likeCount",likeDao.selectLike(noteId));
		CollectDao collectDao=new CollectDao();
		noteObj.put("collectCount", collectDao.selectCollectCount(noteId));
		CommentDao commentDao=new CommentDao();
		noteObj.put("commentCount", commentDao.getCommentCount(noteId));
		response.getWriter().append(noteObj.toString());
	}  

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

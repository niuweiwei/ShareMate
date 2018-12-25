package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import bean.NoteBean;
import bean.UserBean;
import dao.CollectDao;
import dao.CommentDao;
import dao.LikesDao;
import dao.NoteDao;
import dao.UserDao;

/**
 * Servlet implementation class NoteServlet
 */
@WebServlet("/TravelNoteServlet")
public class TravelNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelNoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoteDao noteDao = new NoteDao();
		LikesDao like = new LikesDao();
		CollectDao collect = new CollectDao();
		CommentDao comment = new CommentDao();
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String userid = request.getParameter("userId");
		String noteid = request.getParameter("noteId");
		UserDao userDao = new UserDao();
		UserBean user = new UserBean();
		List<NoteBean> noteLike = new ArrayList<NoteBean>();
		if(userid!=null) {
			int userId = Integer.parseInt(userid);
		    user=userDao.getUserById(userId);
	    	noteLike=like.getLikeNoteList(user.getUserId());
	    	System.out.println("notelike"+noteLike.size());
		}
		if(userid!=null&&noteid!=null) {
	    int userId = Integer.parseInt(userid);
	    int noteId = Integer.parseInt(noteid);
	    String islike = request.getParameter("islike");
	    System.out.println("userID"+userId+"noteid:"+noteId);
	      if(islike.equals("false")) {
	        like.addLike(userId, noteId);
	      }else {
	    	like.deleteLike(userId, noteId);  
	      }
	    }
		//UserBean user = (UserBean) session.getAttribute("user");
    		List<NoteBean> noteList=null;
    		if(session.getAttribute("noteList")==null) {
    			noteList = noteDao.getNoteBytypeId(2);
    			session.setAttribute("noteList", noteList);
    		}else {
    			noteList = noteDao.getNoteBytypeId(2);
    			noteList=(List<NoteBean>) session.getAttribute("noteList");
    		}
    			
    		for(NoteBean note:noteList) {
    			int a = note.getNoteId();
    			int b = like.selectLike(a);
    			int c = comment.getCommentCount(a);
    			int d = collect.selectCollectCount(a);
    			int ilike = 0;
    			note.setNoteLikeCount(b);
    			note.setNoteCollectionCount(d);
    			note.setNoteCommentCount(c);
    			for(NoteBean nlike:noteLike) {
    				//System.out.println(note.getNoteId()+"点赞"+nlike.getNoteId());
    				if(note.getNoteId()==nlike.getNoteId()) {
    					ilike=1;
    					break;
    				}else {
    					ilike=0;
    				}
    			}
    			note.setLike(ilike);
    			System.out.println(note.getNoteId()+"islike"+note.isLike());
    		}
    		
    		request.setAttribute("noteList", noteList);
    		String jsonString="";
    		jsonString = JsonTools.createJsonString("note",noteList);
    		response.getWriter().append(jsonString).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

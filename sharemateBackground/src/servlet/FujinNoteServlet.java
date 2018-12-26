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

import bean.CommentBean;
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
@WebServlet("/FujinNoteServlet")
public class FujinNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FujinNoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoteDao noteDao = new NoteDao();
		UserDao userDao = new UserDao();
		LikesDao like = new LikesDao();
		CollectDao collect = new CollectDao();
		CommentDao comment = new CommentDao();
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String userid = request.getParameter("userId");
		String noteid = request.getParameter("noteId");
		if(userid!=null&&noteid!=null) {
	    int userId = Integer.parseInt(userid);
	    int noteId = Integer.parseInt(noteid);
	    String islike = request.getParameter("islike");
	    System.out.println("userID"+userId+"noteid:"+noteId);
	    //点赞和取消赞
	      if(islike.equals("false")) {
	        like.addLike(userId, noteId);
	      }else {
	    	like.deleteLike(userId, noteId);  
	      }
	    }
		//获取用户地址范围内的笔记
        
    	List<NoteBean> noteList=new ArrayList<NoteBean>();
    	List<UserBean> alluser=userDao.getAllUser();
    	UserBean user = new UserBean();
        if(userid!=null) {
        	int userId = Integer.parseInt(userid);
        	user = userDao.getUserById(userId);
        	String userAddress = user.getUserAddress();
        	System.out.println(userAddress);
        	for(UserBean u:alluser) {
        		if(u.getUserAddress().equals(userAddress)&&u.getUserId()!=user.getUserId()) {
        			System.out.println("一个地方"+u.getUserAddress());
        			List<NoteBean> partnote = noteDao.getNoteList(u.getUserId());
        			System.out.println("par"+partnote.size());
        			noteList.addAll(partnote);
        			
        		}
        	}System.out.println("note"+noteList.size());
        }
      
    	//获取笔记的赞评论收藏数	
    		for(NoteBean note:noteList) {
    			int a = note.getNoteId();
    			int b = like.selectLike(a);
    			int c = comment.getCommentCount(a);
    			int d = collect.selectCollectCount(a);
    			note.setNoteLikeCount(b);
    			note.setNoteCollectionCount(d);
    			note.setNoteCommentCount(c);
    			//System.out.println(note.getNoteLikeCount());
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

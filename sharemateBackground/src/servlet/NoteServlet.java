package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
		NoteDao noteDao = new NoteDao();
		UserDao userDao = new UserDao();
		LikesDao like = new LikesDao();
		CollectDao collect = new CollectDao();
		CommentDao comment = new CommentDao();
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String userid = request.getParameter("userId");
		String noteid = request.getParameter("noteId");
		String typeid1 = request.getParameter("typeid1");
		String typeid2 = request.getParameter("typeid2");
		String typeid3 = request.getParameter("typeid3");
		String typeid4 = request.getParameter("typeid4");
		String typeid5 = request.getParameter("typeid5");
		String typeid6 = request.getParameter("typeid6");
		UserBean user = new UserBean();
		List<NoteBean> noteLike = new ArrayList<NoteBean>();
		if(userid!=null) {
			int userId = Integer.parseInt(userid);
		    user=userDao.getUserById(userId);
	    	noteLike=like.getLikeNoteList(user);
	    	System.out.println("notelike"+noteLike.size());
		}
		if(userid!=null&&noteid!=null) {
	    int userId = Integer.parseInt(userid);
	    int noteId = Integer.parseInt(noteid);
	    //给笔记点赞和取消赞
	    String islike = request.getParameter("islike");
	    System.out.println("userID"+userId+"noteid:"+noteId);
	      if(islike.equals("false")) {
	        like.addLike(userId, noteId);
	      }else {
	    	like.deleteLike(userId, noteId);  
	      }
	    }
	    //根据typeID获取笔记列表
    	List<NoteBean> noteList=null;
        if(typeid1!=null&&typeid2!=null&&typeid3!=null){
        	int id1 = Integer.parseInt(typeid1);
        	int id2 = Integer.parseInt(typeid2);
        	int id3 = Integer.parseInt(typeid3);
        	noteList=noteDao.getNoteByThreetypeId(id1, id2, id3);
        }
        else if(typeid1!=null&&typeid2!=null&&typeid3!=null&&typeid4!=null){
        	int id1 = Integer.parseInt(typeid1);
        	int id2 = Integer.parseInt(typeid2);
        	int id3 = Integer.parseInt(typeid3);
        	int id4 = Integer.parseInt(typeid4);
        	noteList=noteDao.getNoteByFourtypeId(id1, id2, id3, id4);
        }		
        else if(typeid1!=null&&typeid2!=null&&typeid3!=null&&typeid4!=null&&typeid5!=null){
        	int id1 = Integer.parseInt(typeid1);
        	int id2 = Integer.parseInt(typeid2);
        	int id3 = Integer.parseInt(typeid3);
        	int id4 = Integer.parseInt(typeid4);
        	int id5 = Integer.parseInt(typeid5);
        	noteList=noteDao.getNoteByFivetypeId(id1, id2, id3, id4, id5);
        }else {
        	noteList=noteDao.getAllNote();
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
    			//System.out.println(note.getNoteId()+"islike"+note.isLike());
    		}
    		Collections.shuffle(noteList);
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

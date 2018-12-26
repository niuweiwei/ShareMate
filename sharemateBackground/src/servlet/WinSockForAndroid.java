package servlet;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.NoteBean;
import bean.TypeBean;
import bean.UserBean;
import dao.NoteDao;

/**
 * Servlet implementation class WinSockForAndroid
 */
@WebServlet("/WinSockForAndroid")
public class WinSockForAndroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WinSockForAndroid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("���յ�����");
		NoteBean notebean1=null;//��ʼ�����ܵĴ������Ϊ��
		//��ȡandroid���󣬶Դ����������ݽ��д���
		InputStream is=request.getInputStream();
		InputStreamReader isr=new InputStreamReader(is,"utf-8");
		BufferedReader br=new BufferedReader(isr);
		String notejson=br.readLine();
		JSONArray notearr=new JSONArray(notejson);
		JSONObject noteobj=notearr.getJSONObject(0);
		int userid=noteobj.getInt("userid");
		int typeid=noteobj.getInt("typeid");
		
		UserBean userbean=new UserBean();
		TypeBean typebean=new TypeBean();
		userbean.setUserId(userid);
		typebean.setTypeId(typeid);
		notebean1=new NoteBean();
		notebean1.setNoteDetail(noteobj.getString("detial") );
		notebean1.setNoteTitle(noteobj.getString("title"));
		notebean1.setUser(userbean);
		notebean1.setType(typebean);
		
		System.out.println("�ô������:\n"
				+"detial:"+notebean1.getNoteDetail()
				+"\ntitle:"+notebean1.getNoteTitle()
				+"\nuserid"+notebean1.getUser().getUserId()
				+"\ntypeid"+notebean1.getType().getTypeId()
				);
		NoteDao notedao=new NoteDao();
		notedao.addNoteshare(notebean1);
		System.out.println("���ݿ�������");
		//���ؽ��
		OutputStream os=response.getOutputStream();
		OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
		BufferedWriter bw = new BufferedWriter(osw);
		if(notebean1!=null) {
			bw.write("succeed");
		}
		bw.flush();
		if(osw!=null) osw.close();
		if(is!=null)is.close();
		if(isr!=null)isr.close();
		if(br!=null)br.close();
		if(bw!=null)bw.close();
		
		
		
	}

}

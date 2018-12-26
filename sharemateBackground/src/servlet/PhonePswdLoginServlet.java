package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import bean.UserBean;
import dao.TitleDao;
import dao.TypeDao;
import dao.UserDao;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class PhonePswdLoginServlet
 */
@WebServlet("/PhonePswdLoginServlet")
public class PhonePswdLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhonePswdLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String json = reader.readLine();
		System.out.println("数据："+json);
		
		JSONObject object = JSONObject.fromObject(json);
		String userPhone = object.getString("userPhone");
		String userPassword = object.getString("userPassword");
		System.out.println("userPhone:"+userPhone+" userPassword"+userPassword);
		
		UserDao userDao = new UserDao();
		boolean result = userDao.isUserExistByPhoneAndPassword(userPhone, userPassword);
		System.out.println("result:"+result);
		if(result == true) {
			int userId = userDao.getUserIdByPhoneAndPassword(userPhone, userPassword);
			TitleDao titleDao = new TitleDao();
			List<Integer> type = titleDao.getType(userId);
			System.out.println("userId:"+userId);
			JSONArray array = new JSONArray();
			for(Integer i:type) {
				JSONObject back = new JSONObject();
				back.put("typeId", i);
				back.put("msg", "该用户存在");
				back.put("userId",userId);
				array.put(back);
			}
			System.out.println(array.toString());
			response.getWriter().append(array.toString());
		}else {
			JSONObject back = new JSONObject();
			back.put("msg", "该用户不存在");
			response.getWriter().append(back.toString());
		}
	}

}

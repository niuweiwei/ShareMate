package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import dao.UserDao;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String json = reader.readLine();
		System.out.println("数据："+json);
		
		JSONObject object = JSONObject.fromObject(json);
		String userPhone = object.getString("userPhone");
		System.out.println("userPhone:"+userPhone);
		
		UserDao userDao = new UserDao();
		boolean result = userDao.isUserExistByPhone(userPhone);
		System.out.println("result:"+result);
		if(result == false) {
			JSONObject back = new JSONObject();
			back.put("msg", "用户不存在");
			response.getWriter().append(back.toString());
		}else{
			int userId = userDao.getUserIdByPhone(userPhone);
			System.out.println("userId:"+userId);
			UserBean user = userDao.getUserById(userId);
			JSONObject back = new JSONObject();
			back.put("msg", "用户存在");
			back.put("userId",userId);
			back.put("userPhone",user.getUserPhone());
			response.getWriter().append(back.toString());
		}
	}

}

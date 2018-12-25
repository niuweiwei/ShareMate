package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import dao.UserDao;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class RegisterFontServlet
 */
@WebServlet("/RegisterFontServlet")
public class RegisterFontServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterFontServlet() {
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
		response.setContentType("text/html;charset=utf-8");
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String json = br.readLine();
		System.out.println("数据："+json);
		//获取客户端发来的请求，恢复其JSON格式，需要客户端发请求时也封装成JSON格式
		JSONObject object = JSONObject.fromObject(json);
		String userName = object.getString("userName");
		String userPassword = object.getString("userPassword");
		String userPhone = object.getString("userPhone");
		String userPhoto = "images/userPhotos/0.jpg";
		String userIntro = "添加个人描述，可以让大家更好的认识你";
		String userAddress = "完善你的位置信息";
		System.out.println("头像：" + userPhoto + "; 用户名：" + userName + "; 密码：" + userPassword + "; 电话：" + userPhone);
		UserBean user = new UserBean();
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setUserPhone(userPhone);
		user.setUserPhoto(userPhoto);
		user.setUserAddress(userAddress);
		user.setUserIntro(userIntro);
		//插入用户
		UserDao userDao = new UserDao();
		//判断用户是否注册过
		boolean result = userDao.isRegisterByNameOrPhone(user);
		System.out.println("result:"+result);
		if(result == false) {
			userDao.insertUser(user);
			response.getWriter().append("插入成功");
		}else{
			response.getWriter().append("该用户已注册");
		}
		
		
	}

}

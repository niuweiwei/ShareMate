package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import bean.UserBean;
import dao.UserDao;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterImageServlet")
public class RegisterImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterImageServlet() {
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
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//1、创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录。 
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//2、使用DiskFileItemFactory 对象创建ServletFileUpload对象，并设置上传文件的大小限制。
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list;
		String userPhoto = "";
		try {
			//3、调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
			list = upload.parseRequest(request);
			//4、对list进行迭代，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件：
			for(FileItem item : list) {
				if(item.isFormField()) {
					//4.1、 为普通表单字段，则调用getFieldName、getString方法得到字段名和字段值。
					
				}else {
					//4.2、为上传文件，则调用getInputStream方法得到数据输入流，从而读取上传数据。
					//pathName有的浏览器会返回文件名，而有的浏览器会返回“路径”+“文件名”
					String pathName = item.getName();
					//fileName获取的是文件的名字
					String fileName = pathName.substring(pathName.lastIndexOf("\\")+1);
					System.out.println(fileName);
					//serverPath是项目运行后的路径,在使用ServletContext.getRealPath() 时，传入的参数是从 当前servlet 部署在tomcat中的文件夹算起的相对路径，要以"/" 开头，否则会找不到路径，导致NullPointerException
					String serverPath = getServletContext().getRealPath("/");
					UserDao userDao = new UserDao();
					int count = userDao.getUserCount();
					fileName = count+1+".jpg";
					item.write(new File(serverPath+"\\images\\userPhotos\\",fileName));
					userPhoto = fileName;
					System.out.println(userPhoto);
//					out.println(userPhoto+"文件上传成功");
//					out.println("<img src='images/userPhotos/"+fileName+"'/>");
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("userPhoto", userPhoto);
//		request.setAttribute("userPhoto", userPhoto);
		System.out.println("userPhoto:"+userPhoto);
		request.getRequestDispatcher("RegisterFontServlet").forward(request, response);
	}

}

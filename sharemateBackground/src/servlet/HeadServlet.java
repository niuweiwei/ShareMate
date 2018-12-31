package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.UserDao;

/**
 * Servlet implementation class HeadServlet
 */
@WebServlet("/HeadServlet")
public class HeadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setCharacterEncoding("utf-8");
	     request.setCharacterEncoding("utf-8");
	     int userId = 0;
	     DiskFileItemFactory factory = new DiskFileItemFactory();
	     ServletFileUpload upload = new ServletFileUpload(factory);
	        try{  
	        	List<FileItem>list = upload.parseRequest(request);
				for(FileItem item:list) {
					if(item.isFormField()) {
						//文本域
						userId = Integer.parseInt(item.getString());
					}else{   
						//pathName有的浏览器会返回文件名,而有的浏览器会返回“路径”+“文件名”
						String pathName = item.getName(); 
						//fileName获取的是文件的名字
						String fileName = pathName.substring(pathName.lastIndexOf("\\")+1);
						//serverPath是项目运行后的路径
						String serverPath = getServletContext().getRealPath("/");
						item.write(new File(serverPath+"\\images\\userPhotos\\"+fileName));
						UserDao userdao = new UserDao();
						userdao.setUserHead(userId, "images/userPhotos/"+fileName);
					}
				}
			}catch(Exception e){
				e.printStackTrace();  
	        }     
	    }  


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

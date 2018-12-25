package servlet;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.NoteDao;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
	     response.setContentType("text/html;charset=utf-8");
	     PrintWriter out = response.getWriter();
	     DiskFileItemFactory factory = new DiskFileItemFactory();
	     ServletFileUpload upload = new ServletFileUpload(factory);
	        try{  
	        	List<FileItem>list = upload.parseRequest(request);
				for(FileItem item:list) {
					if(item.isFormField()) {//�ı���
					}else{   
						//pathName�е�������᷵���ļ���,���е�������᷵�ء�·����+���ļ�����
						String pathName = item.getName(); 
						//fileName��ȡ�����ļ�������
						String fileName = pathName.substring(pathName.lastIndexOf("\\")+1);
						System.out.print(fileName);
						//serverPath����Ŀ���к��·��
						String serverPath = getServletContext().getRealPath("/");
						item.write(new File(serverPath+"\\images\\notePhotos\\"+fileName));
						String picpath="images/notePhotos/"+fileName;
						NoteDao notedao=new NoteDao();
						int noteid=notedao.findid();
						System.out.println(noteid);
						System.out.println(picpath);
						notedao.addNotePic(picpath, noteid);
						System.out.println("ͼƬ�ϴ��ɹ�");
					}
				}
			}catch(Exception e){
				e.printStackTrace();  
	        }     
	    }  
	}

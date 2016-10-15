
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = { "/tree" })
@MultipartConfig

public class BeaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parameter = request.getParameter("data");
		PrintWriter out = response.getWriter();
		out.write("get:\n");

		BinaryTree binaryTree = new BinaryTree();

		for (int i = 0; i < parameter.length(); i++) {
			binaryTree.bitRead(parameter.charAt(i));
		}
		binaryTree.printTree(out);

		out.write("\nMélység: " + binaryTree.getMaxDepth() + "\nÁtlag: " + binaryTree.getAverageDepth()
				+ "\nSzórás: " + binaryTree.getDeviation());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Part part = request.getPart("file");
        InputStream input = part.getInputStream();
        
		PrintWriter out = response.getWriter();
		out.write("post:\n");

		BinaryTree binaryTree = new BinaryTree();

		int character = 0;
		while((character = input.read()) != -1) {
			binaryTree.parseCharacter((char)character);
		}
		out.println();

		binaryTree.printTree(out);

		out.write("\nMélység: " + binaryTree.getMaxDepth() + "\nÁtlag: " + binaryTree.getAverageDepth()
				+ "\nSzórás: " + binaryTree.getDeviation());
	}
}

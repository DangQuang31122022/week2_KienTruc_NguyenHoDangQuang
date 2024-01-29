package dangquang;

import java.io.File;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
/*
 * @author: Quang
 * @created-date: 24/01/2024
 */
public class CommonOperations {
	public static void listMethodCalls(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println("=================================");
			System.out.println(path);
			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(PackageDeclaration n, Object arg) {
						super.visit(n, arg);
						String namePackage = n.getNameAsString();
						if (!namePackage.matches("^com\\.companyname\\..+"))
							System.out.println("Invalid name package: " + namePackage);
						else
							System.out.println("Name package: " + namePackage);
					}

					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						// TODO Auto-generated method stub
						super.visit(n, arg);
						String nameClass = n.getNameAsString();
						char firstChar = nameClass.charAt(0);
						if (firstChar > 'a' && firstChar < 'z') {
							System.out.println("First Name must be Upcase : " + nameClass + " , Vi tri: " + n.getBegin() + " - " + n.getEnd());
						} 
						Optional<Comment> oc = n.getComment();
						if (oc.isEmpty()) {
							System.out.println("no comment");
						} else {
							Comment comment = oc.get();
							String content = comment.getContent();
							if (!content.contains("@author:")) {
								System.out.println("Comment without author at class " + nameClass);
							} else if (!content.contains("@created-date:")) {
								System.out.println("Comment without date at class " + nameClass);
							}
						}
					}
					
//					@Override
//					public void visit(FieldDeclaration n, Object arg) {
//						super.visit(n, arg);
//						System.out.println(" [L " + n.getBegin() + "] " + n);
//					}
//
//					@Override
//					public void visit(MethodDeclaration n, Object arg) {
//						super.visit(n, arg);
//						System.out.println(" [L " + n.getBegin() + "] " + n.getDeclarationAsString());
//					}
				}.visit(StaticJavaParser.parse(file), null);
			} catch (Exception e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);
	}

	public static void main(String[] args) {
		File projectDir = new File("T:\\dangquang\\src\\main\\java");
		listMethodCalls(projectDir);
	}
}

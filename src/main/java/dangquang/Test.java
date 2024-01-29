package dangquang;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		File projectDir = new File("T:\\spring_boot_api_jwt_ad-refresh_token_branch");
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
		}).explore(projectDir);
	}
}

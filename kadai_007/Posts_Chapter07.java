package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		

		Connection con = null;
		Statement statement = null;
		
		try {
			//データベースに接続
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/challenge_java",	
				"root",
				"@553242erI"				
			);
			
			System.out.println("データベース接続成功" + con);
			
			//SQLクエリを準備
			statement = con.createStatement();
			
			//データを追加
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES " +
		            "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13), " +
		            "(1002, '2023-02-08', 'お疲れ様です！', 12), " +
		            "(1003, '2023-02-09', '今日も頑張ります！', 18), " +
		            "(1001, '2023-02-09', '無理は禁物ですよ！', 17), " +
		            "(1002, '2023-02-10', '明日から連休ですね！', 20);";
						
			//データを検索して取得
			String sql2 =  "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
			
			//データ追加のSQLクエリを実行
			System.out.println("レコード追加を実行します");
			int rowCnt = statement.executeUpdate(sql);
			System.out.println( rowCnt + "件のレコードが追加されました");
			
			//データ検索のSQLクエリを実行
			ResultSet result = statement.executeQuery(sql2);
			
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			
			//検索の実行結果を抽出
			while(result.next()) {
				Date postedAt = result.getDate("posted_at");
				String postContent = result.getString("post_content");
				int postLike = result.getInt("likes");
				System.out.println(result.getRow() +  "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + postLike );
			}			
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			//使用したオブジェクトを解放
			if ( statement != null ) {
				try { statement.close(); } catch(SQLException ignore) {}
			}
			if ( con != null ) {
				try { con.close(); } catch(SQLException ignore) {}
			}
		}
	}
}
	

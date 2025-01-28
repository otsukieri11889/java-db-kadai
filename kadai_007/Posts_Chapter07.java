package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement statement = null;
        Statement stmt = null;  // ここでstmtを宣言

        // ユーザーリスト
        String[][] userList = {
            { "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
            { "1002", "2023-02-08", "お疲れ様です！", "12" },
            { "1003", "2023-02-09", "今日も頑張ります！", "18" },
            { "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
            { "1002", "2023-02-10", "明日から連休ですね！", "20" }
        };

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "@553242erI"
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
            statement = con.prepareStatement(insertSql);

            // リストの1行目から順番に読み込む
            for (int i = 0; i < userList.length; i++) {
                // すべてのパラメータを設定
                statement.setString(1, userList[i][0]); // user_id
                statement.setDate(2, Date.valueOf(userList[i][1])); // posted_at
                statement.setString(3, userList[i][2]); // 投稿内容
                statement.setInt(4, Integer.parseInt(userList[i][3])); // いいね数

                // SQLクエリを実行（DBMSに送信）
                int rowCnt = statement.executeUpdate();
            }
            System.out.println(userList.length + "件のレコードが追加されました");

            // 投稿データを検索する
            stmt = con.createStatement();
            String selectSql = "SELECT * FROM posts WHERE user_id = 1002;";
            System.out.println("ユーザーIDが" + 1002 + "のレコードを検索しました");

            // SQLクエリを実行（DBMSに送信）
            ResultSet result = stmt.executeQuery(selectSql);

            // SQLクエリの実行結果を抽出
            while (result.next()) {
                Date postedAt = result.getDate("posted_at");
                String post_content = result.getString("post_content");
                int likes = result.getInt("likes");
                System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "／投稿内容=" + post_content + "／いいね数=" + likes);
            }
        } catch (SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if (statement != null) {
                try { statement.close(); } catch (SQLException ignore) {}
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException ignore) {}
            }
            if (con != null) {
                try { con.close(); } catch (SQLException ignore) {}
            }
        }
    }
}
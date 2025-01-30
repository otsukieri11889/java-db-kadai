package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

    public static void main(String[] args) {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "@553242erI"
            );

           

            // 更新クエリの実行
            statement = con.createStatement();
            System.out.println("データベース接続成功"+ statement.toString());
            String updateSql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";
            int rowCnt = statement.executeUpdate(updateSql);
            System.out.println("レコード更新を実行します"  );
          
            System.out.println( rowCnt + "件のレコードが更新されました");

            // クエリの実行
            String selectSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC";
            resultSet = statement.executeQuery(selectSql);
            System.out.println( "数学・英語の点数が高い順に並べ替えました");

            // 結果セットの処理
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int scoreMath = resultSet.getInt("score_math");
                int scoreEnglish = resultSet.getInt("score_english");
                System.out.println(resultSet.getRow() + "件目=：生徒ID=" + id + "／氏名=" + name + "／数学=" + scoreMath + "／英語=" + scoreEnglish);
            }

        } catch (SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // リソースの解放
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignore) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {}
            }
        }
    }
}		
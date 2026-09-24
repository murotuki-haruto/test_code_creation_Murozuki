package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//ログイン画面遷移
		goTo("http://localhost:8080/lms");

		WebElement login = webDriver.findElement(By.tagName("h2"));
		assertEquals("ログイン", login.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ID
		WebElement id = webDriver.findElement(By.id("loginId"));
		id.clear();
		id.sendKeys("StudentAA02");

		// pass
		WebElement pass = webDriver.findElement(By.id("password"));
		pass.clear();
		pass.sendKeys("StudentAA022");

		// ログインボタン
		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();

		// コース詳細画面の表示確認
		WebElement courseDetail = webDriver.findElement(By.className("active"));
		assertEquals("コース詳細", courseDetail.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		//ヘルプリンクを押下
		WebElement function = webDriver.findElement(By.linkText("機能"));
		function.click();
		WebElement help = webDriver.findElement(By.linkText("ヘルプ"));
		help.click();

		//ヘルプ画面表示確認
		WebElement helpScreen = webDriver.findElement(By.tagName("h2"));
		assertEquals("ヘルプ", helpScreen.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面に遷移")
	void test04() {

		// よくある質問リンク押下
		WebElement question = webDriver.findElement(By.linkText("よくある質問"));
		question.click();

		// よくある質問画面が別タブで開くため、表示された画面に切り替える必要があるので以下の処理を追加
		for (String window : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(window);
		}

		// よくある質問画面表示確認
		WebElement questionScreen = webDriver.findElement(By.tagName("h2"));
		assertEquals("よくある質問", questionScreen.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {

		// キーワード入力
		WebElement keyword = webDriver.findElement(By.id("form"));
		keyword.clear();
		keyword.sendKeys("あ");

		// 検索ボタン押下
		WebElement search = webDriver.findElement(By.className("btn-primary"));
		search.click();

		// 検索結果表示確認
		WebElement question = webDriver.findElement(By.tagName("dt"));
		assertEquals("Q.事業所が変わった場合、何かしら手続きをする必要がありますか？", question.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// クリアボタン押下
		WebElement clear = webDriver.findElement(By.className("input[value='クリア']"));
		clear.click();

		// キーワード欄表示確認
		WebElement keyword = webDriver.findElement(By.id("form"));
		assertEquals("", keyword.getAttribute("value"));

		getEvidence(new Object() {
		});
	}

}

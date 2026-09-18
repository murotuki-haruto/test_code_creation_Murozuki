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
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//機能メニュー押下
		WebElement function = webDriver.findElement(By.linkText("機能"));
		function.click();

		//機能メニューからヘルプ表示されるかチェック
		WebElement help = webDriver.findElement(By.linkText("ヘルプ"));
		assertEquals("ヘルプ", help.getText());

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//ヘルプ押下
		WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));
		helpLink.click();

		//ヘルプ画面の表示確認
		WebElement heipscreen = webDriver.findElement(By.tagName("h2"));
		assertEquals("ヘルプ", heipscreen.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「よくある質問」リンクを押下")
	void test05() {

		// よくある質問リンクを押下
		WebElement question = webDriver.findElement(By.linkText("よくある質問"));
		question.click();

		// 別タブが開いたことを確認
		assertEquals(2, webDriver.getWindowHandles().size());

		getEvidence(new Object() {
		});
	}

}

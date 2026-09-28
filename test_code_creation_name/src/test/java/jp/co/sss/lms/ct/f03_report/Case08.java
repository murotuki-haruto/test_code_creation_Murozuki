package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
		id.sendKeys("StudentAA04");

		// pass
		WebElement pass = webDriver.findElement(By.id("password"));
		pass.clear();
		pass.sendKeys("StudentAA044");

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 「詳細」ボタンを押下
		WebElement detail = webDriver.findElements(By.cssSelector("input[value='詳細']")).get(0);
		detail.click();

		// セクション詳細画面の表示確認
		WebElement sectionDetail = webDriver.findElement(By.className("active"));
		assertEquals("セクション詳細", sectionDetail.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出済み日報【デモ】を確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// レポートの「提出済み日報【デモ】を確認する」ボタンを押下
		WebElement reportButton = webDriver.findElement(By.cssSelector("input[value='提出済み日報【デモ】を確認する']"));
		reportButton.click();

		// レポート登録画面の表示確認
		WebElement reportTitle = webDriver.findElement(By.tagName("h2"));
		assertTrue(reportTitle.getText().contains("日報【デモ】"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 報告内容入力
		WebElement report = webDriver.findElement(By.tagName("textarea"));
		report.clear();
		report.sendKeys("修正しました");

		// 「提出する」ボタン押下
		WebElement submit = webDriver.findElement(By.className("btn-primary"));
		submit.click();

		// 提出済みレポートが表示されていることを確認
		WebElement submittedReport = webDriver.findElement(By.cssSelector("input[value='提出済み日報【デモ】を確認する']"));
		assertEquals("提出済み日報【デモ】を確認する", submittedReport.getAttribute("value"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		WebElement user = webDriver.findElement(By.cssSelector("a[href='/lms/user/detail']"));

		user.click();

		WebElement userdetail = webDriver.findElement(By.tagName("h2"));
		assertEquals("ユーザー詳細", userdetail.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		WebElement detail = webDriver.findElements(By.cssSelector("input[value='詳細']")).get(0);
		detail.click();

		WebElement reportConfirmation = webDriver.findElements(By.tagName("td")).get(1);
		assertEquals("修正しました", reportConfirmation.getText());

		getEvidence(new Object() {
		});
	}

}

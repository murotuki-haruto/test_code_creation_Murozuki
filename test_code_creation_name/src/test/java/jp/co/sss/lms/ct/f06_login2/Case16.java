package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース16
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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

		goTo("http://localhost:8080/lms");

		WebElement login = webDriver.findElement(By.tagName("h2"));
		assertEquals("ログイン", login.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {

		WebElement id = webDriver.findElement(By.id("loginId"));
		id.clear();
		id.sendKeys("StudentAA09");

		WebElement pass = webDriver.findElement(By.id("password"));
		pass.clear();
		pass.sendKeys("StudentAA09");

		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();

		WebElement useRule = webDriver.findElement(By.tagName("h2"));
		assertEquals("利用規約", useRule.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」にチェックを入れ「次へ」ボタン押下")
	void test03() {

		WebElement check = webDriver.findElement(By.cssSelector("input[value='1']"));
		check.click();

		WebElement nextButton = webDriver.findElement(By.className("btn-primary"));
		nextButton.click();

		// パスワード変更画面が表示されるまで待機
		visibilityTimeout(By.id("currentPassword"), 5);

		// パスワード変更画面表示確認
		WebElement passChange = webDriver.findElement(By.tagName("h2"));
		assertEquals("パスワード変更", passChange.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {

		// 現在のパスワード未入力
		WebElement nowPass = webDriver.findElement(By.id("currentPassword"));
		nowPass.clear();

		WebElement newPass = webDriver.findElement(By.id("password"));
		newPass.clear();
		newPass.sendKeys("StudentAA099");

		WebElement confPass = webDriver.findElement(By.id("passwordConfirm"));
		confPass.clear();
		confPass.sendKeys("StudentAA099");

		// 画面の「変更」ボタン押下
		WebElement change = webDriver.findElement(By.cssSelector("button[type='submit']"));
		change.click();

		// モーダルの「変更」ボタン押下
		visibilityTimeout(By.id("upd-btn"), 5);
		WebElement modalChange = webDriver.findElement(By.id("upd-btn"));
		modalChange.click();

		// 現在のパスワードのエラーメッセージ確認
		WebElement nowErrorMessage = webDriver.findElement(By.cssSelector("#currentPassword + ul .error"));
		assertEquals("現在のパスワードは必須です。", nowErrorMessage.getText());

		getEvidence(new Object() {
		});

		// 新しいパスワード未入力
		WebElement nowPass1 = webDriver.findElement(By.id("currentPassword"));
		nowPass1.clear();
		nowPass1.sendKeys("StudentAA09");

		WebElement newPass1 = webDriver.findElement(By.id("password"));
		newPass1.clear();

		WebElement confPass1 = webDriver.findElement(By.id("passwordConfirm"));
		confPass1.clear();
		confPass1.sendKeys("StudentAA099");

		// 画面の「変更」ボタン押下
		WebElement change1 = webDriver.findElement(By.cssSelector("button[type='submit']"));
		change1.click();

		// モーダルの「変更」ボタン押下
		visibilityTimeout(By.id("upd-btn"), 5);

		WebElement modalChange1 = webDriver.findElement(By.id("upd-btn"));
		modalChange1.click();

		// 新しいパスワードエラーメッセージ確認
		WebElement newErrorMessage = webDriver.findElement(By.cssSelector("#password ~ ul .error"));
		assertTrue(newErrorMessage.getText().contains("パスワードは必須です。"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {

		WebElement nowPass = webDriver.findElement(By.id("currentPassword"));
		nowPass.clear();
		nowPass.sendKeys("StudentAA09");

		// 21文字のパスワード
		WebElement newPass = webDriver.findElement(By.id("password"));
		newPass.clear();
		newPass.sendKeys("Abcdefghij12345678901");

		WebElement confPass = webDriver.findElement(By.id("passwordConfirm"));
		confPass.clear();
		confPass.sendKeys("Abcdefghij12345678901");

		// 画面の「変更」ボタン押下
		WebElement change = webDriver.findElement(By.cssSelector("button[type='submit']"));
		change.click();

		// モーダルの「変更」ボタン押下
		visibilityTimeout(By.id("upd-btn"), 5);
		WebElement modalChange = webDriver.findElement(By.id("upd-btn"));
		modalChange.click();

		// 文字数エラーの確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("#password ~ ul .error"));

		assertEquals("パスワードの長さが最大値(20)を超えています。", errorMessage.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {

		WebElement nowPass = webDriver.findElement(By.id("currentPassword"));
		nowPass.clear();
		nowPass.sendKeys("StudentAA08");

		// 数字のみのためパスワードポリシー違反
		WebElement newPass = webDriver.findElement(By.id("password"));
		newPass.clear();
		newPass.sendKeys("12345678");

		WebElement confPass = webDriver.findElement(By.id("passwordConfirm"));
		confPass.clear();
		confPass.sendKeys("12345678");

		// 画面の「変更」ボタン押下
		WebElement change = webDriver.findElement(By.cssSelector("button[type='submit']"));
		change.click();

		// モーダルの「変更」ボタン押下
		visibilityTimeout(By.id("upd-btn"), 5);
		WebElement modalChange = webDriver.findElement(By.id("upd-btn"));
		modalChange.click();

		// パスワードポリシーのエラー確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("#password ~ ul .error"));
		assertEquals("「パスワード」は半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。", errorMessage.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {

		WebElement nowPass = webDriver.findElement(By.id("currentPassword"));
		nowPass.clear();
		nowPass.sendKeys("StudentAA09");

		WebElement newPass = webDriver.findElement(By.id("password"));
		newPass.clear();
		newPass.sendKeys("StudentAA099");

		// 新しいパスワードとは異なる値入力
		WebElement confPass = webDriver.findElement(By.id("passwordConfirm"));
		confPass.clear();
		confPass.sendKeys("StudentAA010");

		// 画面の「変更」ボタン押下
		WebElement change = webDriver.findElement(By.cssSelector("button[type='submit']"));
		change.click();

		// モーダルの「変更」ボタン押下
		visibilityTimeout(By.id("upd-btn"), 5);

		WebElement modalChange = webDriver.findElement(By.id("upd-btn"));
		modalChange.click();

		//待機
		visibilityTimeout(By.id("currentPassword"), 5);
		// 確認パスワード不一致のエラーメッセージ確認
		WebElement body = webDriver.findElement(By.tagName("body"));
		assertTrue(body.getText().contains("パスワードと確認パスワードが一致しません。"));

		getEvidence(new Object() {
		});
	}
}
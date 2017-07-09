package com.epam.library.guicontroller;

import org.testng.annotations.Test;
import com.epam.library.bean.AccessLevel;
import com.epam.library.bean.User;
import com.epam.library.command.exception.CommandException;
import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.fail;
import org.testng.annotations.AfterMethod;

public class ControllerTest {
	private Controller controller;
	private final static int userStatusUser = 2;

	@BeforeMethod(groups = { "smoke", "methods", "exceptions", "positive", "negative" })
	public void beforeMethod() {
		controller = new Controller();
	}

	@AfterMethod(groups = { "smoke", "methods", "exceptions", "positive", "negative" })
	public void afterMethod() {
		controller = null;
	}

	@Test(groups = { "smoke", "methods",
			"positive" }, dataProvider = "positiveLogin", dataProviderClass = DataProviderControllerTest.class)
	public void tst_method_execute_login(String expected, String request) {
		User testUser;
		try {
			testUser = (User) controller.executeTask(request);
			Assert.assertEquals(expected, testUser.getLogin());
		} catch (CommandException e) {
			fail();
		}
	}

	@Test(groups = { "smoke", "exceptions",
			"negative" }, dataProvider = "negativeLogin", dataProviderClass = DataProviderControllerTest.class, expectedExceptions = CommandException.class)
	public void tst_exception_execute_login(String expected, String request) throws CommandException {
		controller.executeTask(request);
	}

	@Test(groups = { "smoke", "methods",
			"positive" }, dataProvider = "positiveRegistration", dataProviderClass = DataProviderControllerTest.class)
	public void tst_method_execute_registration(String expected, String request) {
		User testUser;
		try {
			testUser = (User) controller.executeTask(request);
			Assert.assertEquals(expected, testUser.getLogin());
		} catch (CommandException e) {
			fail();
		}
	}

	@Test(groups = { "smoke", "exceptions",
			"negative" }, dataProvider = "negativeRegistration", dataProviderClass = DataProviderControllerTest.class, expectedExceptions = CommandException.class)
	public void tst_method_exception_registration(String expected, String request) throws CommandException {
		controller.executeTask(request);
	}

	@Test(groups = { "smoke", "methods",
			"positive" }, dataProvider = "positiveUpdateUserInfo", dataProviderClass = DataProviderControllerTest.class)
	public void tst_method_execute_updateUserInfo(String expected, String request) {
		User testUser;
		try {
			AccessLevel testAccessLevel = new AccessLevel();
			testAccessLevel.setAccessLevelId(userStatusUser);
			Controller.sessionUser = new User();
			Controller.sessionUser.setAccessLevel(testAccessLevel);
			testUser = (User) controller.executeTask(request);
			Assert.assertEquals(expected, testUser.getLogin());
		} catch (CommandException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
}

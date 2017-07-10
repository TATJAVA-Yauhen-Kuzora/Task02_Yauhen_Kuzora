package com.epam.library.guicontroller;

import org.testng.annotations.DataProvider;

public class DataProviderControllerTest {
	@DataProvider
	public Object[][] positiveLogin() { //
		return new Object[][] { //
				new Object[] { "super", "Login|super|12345" }, //
		};
	}

	@DataProvider
	public Object[][] negativeLogin() { //
		return new Object[][] { //
				new Object[] { "super", "Login|super|123" }, //
				new Object[] { "super", "Logn|super|12345" }, //
				new Object[] { "super", "Logn|super" }, //
		};
	}

	@DataProvider
	public Object[][] positiveRegistration() { //
		return new Object[][] { //
				new Object[] { "testLogin", "Registration|name|secondName|testLogin|12345" }, //
		};
	}

	@DataProvider
	public Object[][] negativeRegistration() { //
		return new Object[][] { //
				new Object[] { "testLogin11", "Registion|name|secondName|testLogin11|12345" }, //
				new Object[] { "testLogin111", "Registration|name|secondName|testLogin111" }, //
				// count
				// of
				// args
				new Object[] { "super", "Registration|Eugene|Kuzora|super|12345" }, };
	}

	@DataProvider
	public Object[][] positiveUpdateUserInfo() { //
		return new Object[][] { //
				new Object[] { "testLogin2", "Update_user_info|name|secondName|testLogin2|12" }, //
		};
	}

	@DataProvider
	public Object[][] negativeUpdateUserInfo() { //
		return new Object[][] { //
				new Object[] { "testLogin3", "Update_user5inf4|name|secondName|testLogin4|12" }, //
				new Object[] { "testLogin3", "Update_user_info|name|secondName|12" }, //
				new Object[] { "testLogin3", "Update_user_info|name|secondName|testLogin4|12.54" }, //
		};
	}
}

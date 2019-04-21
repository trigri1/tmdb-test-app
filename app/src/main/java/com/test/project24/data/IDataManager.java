package com.test.project24.data;

import com.test.project24.data.database.IDatabaseHelper;
import com.test.project24.data.network.IAppApiHelper;
import com.test.project24.data.sharedprefs.IAppPrefsHelper;

/**
 * Created by Gohar Ali on 30/10/2017.
 * <p>
 *
 * @author Gohar Ali
 * Interface that combines all the data part of the application
 * @see IAppPrefsHelper for SharedPreference
 * @see IAppApiHelper for Network Calls
 */
public interface IDataManager extends IDatabaseHelper, IAppApiHelper, IAppPrefsHelper {
}

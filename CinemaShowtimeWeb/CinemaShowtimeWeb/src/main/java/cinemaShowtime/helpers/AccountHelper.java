package cinemaShowtime.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cinemaShowtime.database.dao.AccountPreferenceItemDAO;
import cinemaShowtime.database.model.AccountPreference;
import cinemaShowtime.database.model.AccountPreferenceItem;
import cinemaShowtime.utils.Application;

public class AccountHelper {

	public static AccountPreference prepareAccountPreference() {
		AccountPreferenceItemDAO accountPreferenceItemDAO = new AccountPreferenceItemDAO();
		HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
		queryParamMap.put("accountId", Application.getInstance().getAccount().getId());
		List<AccountPreferenceItem> itemList = accountPreferenceItemDAO.findList(queryParamMap);
		if (itemList != null && itemList.size() > 0) {
			AccountPreference accPreference = new AccountPreference();
			ArrayList<Long> cinemaIds = new ArrayList<Long>();
			ArrayList<Long> genreIds = new ArrayList<Long>();

			for (AccountPreferenceItem item : itemList) {
				accPreference.setAccountId(item.getAccountId());
				if (item.getCityId() != null) {
					accPreference.setCityId(item.getCityId());
				}
				if (item.getGenreId() != null) {
					genreIds.add(item.getGenreId());
				}
				if (item.getCinemaId() != null) {
					cinemaIds.add(item.getCinemaId());
				}
			}
			Long[] longArray;
			longArray = Arrays.copyOf(genreIds.toArray(), genreIds.toArray().length, Long[].class);
			accPreference.setGenreIds(longArray);
			longArray = Arrays.copyOf(cinemaIds.toArray(), genreIds.toArray().length, Long[].class);
			accPreference.setCinemaIds(longArray);
			Application.getInstance().setAccountPreference(accPreference);
			return accPreference;
		} else {
			return null;
		}
	}

	
}

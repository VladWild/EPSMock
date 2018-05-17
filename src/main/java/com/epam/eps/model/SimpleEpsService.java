package com.epam.eps.model;

import com.epam.eps.framework.core.Group;
import com.epam.eps.model.searcher.InjectRiskGroups;

public class SimpleEpsService implements EpsService {
	@InjectRiskGroups(viewFieldId = "eps.bean.id.reviewSector")
	private Group[] groups = {};

	@Override
	public Group[] getRiskGroups() {
		return groups;
	}
}




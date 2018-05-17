package com.epam.eps.framework.core;

import com.epam.eps.framework.support.risk.Max;
import com.epam.eps.framework.support.risk.Min;
import com.epam.eps.framework.support.risk.RiskZone;

import java.util.ArrayList;
import java.util.List;

@RiskZone(id = "eps.bean.id.risk.target")
public class TargetRiskMock {
	@Min(10)
	private int min;

	@Max(Integer.MAX_VALUE)
	private int max;

	private List<Group> groups;

	public TargetRiskMock() {
		this.groups = new ArrayList<>();
	}

	@AddAnotherOneGroup
	public void addAnotherOneGroup(Group group) {
		groups.add(group);
	}

	public List<Group> getGroups() {
		return groups;
	}
}

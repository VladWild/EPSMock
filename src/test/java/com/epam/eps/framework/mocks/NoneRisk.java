package com.epam.eps.framework.mocks;

import com.epam.eps.framework.core.AddAnotherOneGroup;
import com.epam.eps.framework.core.Group;
import com.epam.eps.framework.support.risk.Max;
import com.epam.eps.framework.support.risk.Min;
import com.epam.eps.framework.support.risk.RiskZone;

import java.util.ArrayList;
import java.util.List;

@RiskZone(id = "eps.bean.id.risk.none")
public class NoneRisk {
	@Min(1)
	private int min;
	@Max(2)
	private int max;

	private List<Group> groups;

	public NoneRisk() {
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

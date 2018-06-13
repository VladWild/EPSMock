package com.epam.eps.model.risk;

import com.epam.eps.framework.core.AddAnotherOneGroup;
import com.epam.eps.framework.core.Group;
import com.epam.eps.framework.support.risk.Max;
import com.epam.eps.framework.support.risk.Min;
import com.epam.eps.framework.support.risk.RiskZone;

import java.util.ArrayList;
import java.util.List;

@RiskZone(id = "eps.bean.id.risk.critical")
public class CriticalRisk implements Risk {
	@Min(value = 10)
	private int min;
	@Max(value = Integer.MAX_VALUE)
	private int max;
	private List<Group> groups = new ArrayList<>();

	@Override
	public List<Group> getGroups() {
		return groups;
	}

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public int getMax() {
		return max;
	}

	@AddAnotherOneGroup
	public void addAnotherOneGroup(Group group){
		groups.add(group);
	}

	@Override
	public String toString() {
		return "CriticalRisk{" + "min=" + min + ", max=" + max + ", groups=" + groups + '}';
	}
}


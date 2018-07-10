package com.infopush.PushServer.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientGroup {
    private Integer id;

    private String groupName;

    private Integer parentTgId;

    private Date creattime;

    private String description;

    private List<ClientGroup> childlist=new ArrayList<ClientGroup>();
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getParentTgId() {
        return parentTgId;
    }

    public void setParentTgId(Integer parentTgId) {
        this.parentTgId = parentTgId;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public List<ClientGroup> getChildlist() {
		return childlist;
	}

	public void setChildlist(List<ClientGroup> childlist) {
		this.childlist = childlist;
	}
}
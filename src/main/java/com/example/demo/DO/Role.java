package com.example.demo.DO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zonzie on 2017/12/20.
 */
public class Role {
    private Integer rid;
    private String name;
    private Set<User> users = new HashSet<>();
    private Set<Module> modules = new HashSet<>();

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Role role = (Role) o;

        if (rid != null ? !rid.equals(role.rid) : role.rid != null)
            return false;
        if (name != null ? !name.equals(role.name) : role.name != null)
            return false;
        if (users != null ? !users.equals(role.users) : role.users != null)
            return false;
        return modules != null ? modules.equals(role.modules) : role.modules == null;
    }

    @Override
    public int hashCode() {
        int result = rid != null ? rid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (modules != null ? modules.hashCode() : 0);
        return result;
    }
}

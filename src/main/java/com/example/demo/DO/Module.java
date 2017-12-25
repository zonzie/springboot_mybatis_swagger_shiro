package com.example.demo.DO;

import java.util.Set;

/**
 * Created by zonzie on 2017/12/20.
 */
public class Module {
    private Integer mid;
    private String mname;
    private Set<Role> roles;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Module module = (Module) o;

        if (mid != null ? !mid.equals(module.mid) : module.mid != null)
            return false;
        if (mname != null ? !mname.equals(module.mname) : module.mname != null)
            return false;
        return roles != null ? roles.equals(module.roles) : module.roles == null;
    }

    @Override
    public int hashCode() {
        int result = mid != null ? mid.hashCode() : 0;
        result = 31 * result + (mname != null ? mname.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
}

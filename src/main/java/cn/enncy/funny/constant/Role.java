package cn.enncy.funny.constant;

/**
 * 权限枚举
 * @author: enncy
 */
public enum Role {
    // 权限
    VISITOR("visitor","游客",0),
    USER("user","用戶",1),
    ROOT("admin","管理员",2);


    public String value;
    public String description;
    public int level;

    Role(String value,String description,int level){
        this.value = value;
        this.description = description;
        this.level = level;

    }

    @Override
    public String toString() {
        return value;
    }

    public static Role parseToRole(String value){
        for (Role role : values()) {
            if(role.value.equals(value)){
                return role;
            }
        }
        return Role.VISITOR;
    }

    public static boolean verifyRole(Role[] required, Role role){
        for (Role r : required) {
            if(role.level >= r.level){
                return true;
            }
        }
        return false;
    }
}

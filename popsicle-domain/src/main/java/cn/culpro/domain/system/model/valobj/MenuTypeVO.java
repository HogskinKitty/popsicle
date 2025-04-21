package cn.culpro.domain.system.model.valobj;

import lombok.Getter;

/**
 * 菜单类型值对象
 */
@Getter
public enum MenuTypeVO {
    /**
     * 目录
     */
    DIRECTORY(1, "目录"),
    
    /**
     * 菜单
     */
    MENU(2, "菜单"),
    
    /**
     * 按钮
     */
    BUTTON(3, "按钮");
    
    /**
     * 类型码
     */
    private final Integer code;
    
    /**
     * 类型描述
     */
    private final String description;
    
    MenuTypeVO(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据类型码获取菜单类型
     *
     * @param code 类型码
     * @return 菜单类型值对象
     */
    public static MenuTypeVO fromCode(Integer code) {
        for (MenuTypeVO type : MenuTypeVO.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return MENU; // 默认返回菜单类型
    }
} 
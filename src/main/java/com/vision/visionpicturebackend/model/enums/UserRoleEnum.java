package com.vision.visionpicturebackend.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

/**
 * TODO:用户角色枚举
 * @author vision
 * 创建时间: 2025/3/30 14:32
 */
@Getter
public enum UserRoleEnum {

     USER("用户","user"),
    ADMIN("管理员","admin");


    private final  String text;
    private final  String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static UserRoleEnum getEnumByValue(String value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

}
/*


 */

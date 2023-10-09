package com.zhu.ticketgrabbing.entity;

import java.util.Objects;

/**
 * 存放不同方法对应的cookie对象
 */
public enum MethonEnum {

    QUERYAPI(1, "uab_collina=169614418965282241020371; JSESSIONID=077A18D2B44C260EBBA55ADCA50E7E51; route=6f50b51faa11b987e576cdb301e545c4; BIGipServerotn=2162688266.50210.0000; guidesStatus=off; highContrastMode=defaltMode; cursorStatus=off; _jc_save_fromStation=%u5317%u4EAC%u5317%2CVAP; _jc_save_toStation=%u4E0A%u6D77%2CSHH; _jc_save_fromDate=2023-10-03; _jc_save_toDate=2023-10-03; _jc_save_wfdc_flag=wf"),
    CHECKLOGIN(2, "_passport_session=70b0c0de649a4aa5888e504e0397062d7321; guidesStatus=off; highContrastMode=defaltMode; cursorStatus=off; _jc_save_wfdc_flag=dc; BIGipServerotn=1591279882.24610.0000; BIGipServerpassport=904397066.50215.0000; route=6f50b51faa11b987e576cdb301e545c4; _jc_save_fromStation=%u4E0A%u6D77%2CSHH; _jc_save_toStation=%u5317%u4EAC%2CBJP; _jc_save_fromDate=2023-10-14; _jc_save_toDate=2023-10-04; BIGipServerpool_passport=48497162.50215.0000; BIGipServerportal=3151233290.17183.0000; fo=4ujcz71cb1w3n8miT-yr7b-28W6c5KUsNnqL3enQnKdkNPFtBYTlHkaUelLJu-7_0hHv9Rc-inbxedXsQWnVJZ91YX-Sj_aqgHejiOMEzV4ghAx-b-KvXv-OSZ6NhFn9UW24DcaXK_--fyRhQJhL1mrFxU59bcXO2J2QI4Z-SPVgoFeVb6ZzMQKaSKY19PPhSYRT7MEgJ62St8fK"),
    MESSAGECODE(3, "_passport_session=70b0c0de649a4aa5888e504e0397062d7321; guidesStatus=off; highContrastMode=defaltMode; cursorStatus=off; _jc_save_wfdc_flag=dc; BIGipServerotn=1591279882.24610.0000; BIGipServerpassport=904397066.50215.0000; route=6f50b51faa11b987e576cdb301e545c4; _jc_save_fromStation=%u4E0A%u6D77%2CSHH; _jc_save_toStation=%u5317%u4EAC%2CBJP; _jc_save_fromDate=2023-10-14; _jc_save_toDate=2023-10-04; BIGipServerpool_passport=48497162.50215.0000; BIGipServerportal=3151233290.17183.0000; fo=4ujcz71cb1w3n8miT-yr7b-28W6c5KUsNnqL3enQnKdkNPFtBYTlHkaUelLJu-7_0hHv9Rc-inbxedXsQWnVJZ91YX-Sj_aqgHejiOMEzV4ghAx-b-KvXv-OSZ6NhFn9UW24DcaXK_--fyRhQJhL1mrFxU59bcXO2J2QI4Z-SPVgoFeVb6ZzMQKaSKY19PPhSYRT7MEgJ62St8fK"),
    LOGIN(4, ""),
    OTHER(5, "");

    private final Integer index;
    private final String name;

    MethonEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    /**
     * 获取枚举对象
     *
     * @param index 枚举下标
     * @return 枚举对象
     */
    public static MethonEnum getType(Integer index) {
        for (MethonEnum value : MethonEnum.values()) {
            if (Objects.equals(value.index, index)) {
                return value;
            }
        }
        return OTHER;
    }

    /**
     * 根据下标获取内容
     *
     * @param index 下标
     */
    public static String getName(Integer index) {
        for (MethonEnum value : MethonEnum.values()) {
            if (Objects.equals(value.index, index)) {
                return value.name;
            }
        }
        return OTHER.name;
    }

    public Integer getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
